package com.dragonkingdoms.event;

import com.dragonkingdoms.DragonKingdoms;
import com.dragonkingdoms.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.List;

/**
 * Sistema de eventos de mundo do Dragon Kingdoms:
 *
 * 1) Ataque noturno: durante a noite, há uma pequena chance a cada poucos
 *    segundos de um grupo de Cavaleiros Amaldiçoados e Goblins das Cavernas
 *    surgir perto de cada jogador, simulando uma "invasão noturna".
 * 2) Aparição rara do Dragão Ancião: uma vez por dia de jogo (a cada 24000
 *    ticks) há uma chance pequena do dragão aparecer voando perto de um
 *    jogador aleatório do mundo.
 */
@EventBusSubscriber(modid = DragonKingdoms.MOD_ID)
public class WorldEventHandler {

    private static final double NIGHT_ATTACK_CHANCE_PER_CHECK = 0.02D; // 2% a cada checagem (~a cada 30s)
    private static final double DRAGON_SPAWN_CHANCE_PER_DAY = 0.05D;   // 5% de chance por dia, por jogador
    private static final int CHECK_INTERVAL_TICKS = 600;               // a cada 30 segundos (20 ticks/s * 30)

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            handleLevelCheck(level);
        }
    }

    private static void handleLevelCheck(ServerLevel level) {
        if (level.getGameTime() % CHECK_INTERVAL_TICKS != 0) return;

        boolean isNight = level.getDayTime() % 24000 > 13000 && level.getDayTime() % 24000 < 23000;

        for (ServerPlayer player : level.players()) {
            if (isNight && level.getRandom().nextDouble() < NIGHT_ATTACK_CHANCE_PER_CHECK) {
                spawnNightAttackWave(level, player.blockPosition());
            }
        }

        // Checagem diária de aparição do dragão (uma vez por dia de jogo)
        if (level.getDayTime() % 24000 < CHECK_INTERVAL_TICKS) {
            List<ServerPlayer> players = level.players();
            if (!players.isEmpty() && level.getRandom().nextDouble() < DRAGON_SPAWN_CHANCE_PER_DAY) {
                ServerPlayer chosen = players.get(level.getRandom().nextInt(players.size()));
                spawnWanderingDragon(level, chosen.blockPosition());
            }
        }
    }

    /** Gera um pequeno grupo de monstros próximo ao jogador, simulando um ataque noturno. */
    private static void spawnNightAttackWave(ServerLevel level, BlockPos center) {
        int knights = 1 + level.getRandom().nextInt(2);
        int goblins = 1 + level.getRandom().nextInt(3);

        for (int i = 0; i < knights; i++) {
            spawnNear(level, ModEntities.CURSED_KNIGHT.get(), center);
        }
        for (int i = 0; i < goblins; i++) {
            spawnNear(level, ModEntities.CAVE_GOBLIN.get(), center);
        }
        DragonKingdoms.LOGGER.debug("Ataque noturno disparado perto de {}", center);
    }

    /** Faz o Dragão Ancião aparecer voando a uma altura razoável perto do jogador. */
    private static void spawnWanderingDragon(ServerLevel level, BlockPos center) {
        BlockPos spawnPos = center.above(20 + level.getRandom().nextInt(15))
                .offset(level.getRandom().nextInt(21) - 10, 0, level.getRandom().nextInt(21) - 10);

        Mob dragon = ModEntities.ANCIENT_DRAGON.get().create(level, EntitySpawnReason.EVENT);
        if (dragon != null) {
            dragon.moveTo(Vec3.atCenterOf(spawnPos), 0.0F, 0.0F);
            level.addFreshEntity(dragon);
            DragonKingdoms.LOGGER.info("O Dragao Ancião surgiu perto de {}", spawnPos);
        }
    }

    private static void spawnNear(ServerLevel level, EntityType<? extends Mob> type, BlockPos center) {
        for (int attempt = 0; attempt < 5; attempt++) {
            BlockPos pos = center.offset(
                    level.getRandom().nextInt(17) - 8,
                    0,
                    level.getRandom().nextInt(17) - 8);
            BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);

            if (level.getWorldBorder().isWithinBounds(ground) && level.isEmptyBlock(ground)) {
                Mob mob = type.create(level, EntitySpawnReason.EVENT);
                if (mob != null) {
                    mob.moveTo(Vec3.atBottomCenterOf(ground), 0.0F, 0.0F);
                    level.addFreshEntity(mob);
                }
                return;
            }
        }
    }
}
