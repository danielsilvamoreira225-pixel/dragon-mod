package com.dragonkingdoms.registry;

import com.dragonkingdoms.DragonKingdoms;
import com.dragonkingdoms.entity.AncientDragonEntity;
import com.dragonkingdoms.entity.CaveGoblinEntity;
import com.dragonkingdoms.entity.CursedKnightEntity;
import com.dragonkingdoms.entity.ShadowGuardianEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Registro de todas as entidades (monstros e o dragão) do Dragon Kingdoms. */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, DragonKingdoms.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<CursedKnightEntity>> CURSED_KNIGHT =
            ENTITY_TYPES.register("cursed_knight", () -> EntityType.Builder.of(CursedKnightEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .build("cursed_knight"));

    public static final DeferredHolder<EntityType<?>, EntityType<CaveGoblinEntity>> CAVE_GOBLIN =
            ENTITY_TYPES.register("cave_goblin", () -> EntityType.Builder.of(CaveGoblinEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.0F)
                    .eyeHeight(0.9F)
                    .build("cave_goblin"));

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowGuardianEntity>> SHADOW_GUARDIAN =
            ENTITY_TYPES.register("shadow_guardian", () -> EntityType.Builder.of(ShadowGuardianEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 2.2F)
                    .eyeHeight(2.0F)
                    .build("shadow_guardian"));

    public static final DeferredHolder<EntityType<?>, EntityType<AncientDragonEntity>> ANCIENT_DRAGON =
            ENTITY_TYPES.register("ancient_dragon", () -> EntityType.Builder.of(AncientDragonEntity::new, MobCategory.MONSTER)
                    .sized(3.5F, 2.5F) // consideravelmente maior que os demais mobs
                    .eyeHeight(2.0F)
                    .fireImmune() // criatura de fogo não pega fogo com o próprio ataque
                    .build("ancient_dragon"));

    /** Liga os atributos (vida/dano/etc.) definidos em cada classe de entidade ao registro do jogo. */
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CURSED_KNIGHT.get(), CursedKnightEntity.createAttributes().build());
        event.put(CAVE_GOBLIN.get(), CaveGoblinEntity.createAttributes().build());
        event.put(SHADOW_GUARDIAN.get(), ShadowGuardianEntity.createAttributes().build());
        event.put(ANCIENT_DRAGON.get(), AncientDragonEntity.createAttributes().build());
    }
}
