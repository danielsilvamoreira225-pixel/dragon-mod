package com.dragonkingdoms.entity;

import com.dragonkingdoms.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoveControl;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

/**
 * Dragão Ancião (Ancient Dragon)
 * -------------------------------
 * O grande predador voador do mod. Aparece raramente pelo mundo (ver
 * DragonSpawnEvents), voa livremente, ataca com sopro de fogo e possui
 * grande resistência. Ao morrer, seu loot table entrega uma recompensa
 * especial (Escamas de Dragão, Cristal Mágico e chance de item lendário).
 *
 * Vida: 200 (100 corações)
 * Dano corpo a corpo: 10 | Dano do sopro de fogo: 6 + queimadura
 * Resistência: armadura 10, dificil de nocautear
 */
public class AncientDragonEntity extends Monster {

    private int fireBreathAnimTicks;

    public AncientDragonEntity(EntityType<? extends AncientDragonEntity> type, Level level) {
        super(type, level);
        this.moveControl = new DragonMoveControl(this);
        this.xpReward = 100; // recompensa de experiência de "chefe"
        this.setPersistenceRequired(); // não desaparece por despawn natural
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DragonFireAttackGoal(this));
        this.goalSelector.addGoal(2, new DragonWanderGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    /** O dragão voa - não usa pathfinding terrestre normal. */
    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean canCutCorner(PathType type) {
        return type != PathType.DANGER_FIRE && type != PathType.DANGER_OTHER;
    }

    /** Sem gravidade enquanto voa: o dragão paira e plana livremente. */
    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);

        if (this.fireBreathAnimTicks > 0) {
            this.fireBreathAnimTicks--;
        }

        // Batida de asa periódica com som, para reforçar a sensação de voo.
        if (!this.level().isClientSide() && this.tickCount % 20 == 0) {
            this.playSound(ModSounds.DRAGON_WING_FLAP.get(), 1.2F, 0.9F);
        }
    }

    /** Chamado pelo goal de ataque para avisar o modelo/animação (usado no cliente). */
    public void triggerFireBreathAnimation() {
        this.fireBreathAnimTicks = 20;
    }

    /** Usado pelo modelo/renderer para saber se deve tocar a animação de sopro. */
    public boolean isBreathingFire() {
        return this.fireBreathAnimTicks > 0;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.DRAGON_ROAR.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DRAGON_DEATH.get();
    }

    @Override
    public boolean isFlapping() {
        return this.tickCount % 40 < 20;
    }

    /**
     * Controle de movimento customizado para voo suave (inclina-se em direção
     * ao destino ao invés de andar como uma criatura terrestre).
     */
    private static class DragonMoveControl extends MoveControl {
        private final AncientDragonEntity dragon;

        DragonMoveControl(AncientDragonEntity dragon) {
            super(dragon);
            this.dragon = dragon;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 delta = new Vec3(this.wantedX - dragon.getX(), this.wantedY - dragon.getY(), this.wantedZ - dragon.getZ());
                double distance = delta.length();
                if (distance >= 1.0E-5) {
                    Vec3 normalized = delta.scale(0.05D * this.speedModifier);
                    dragon.setDeltaMovement(dragon.getDeltaMovement().add(normalized));

                    double yRot = Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90.0D;
                    dragon.setYRot(rotlerp(dragon.getYRot(), (float) yRot, 4.0F));
                }
            }
        }

        private static float rotlerp(float current, float target, float maxChange) {
            float diff = net.minecraft.util.Mth.wrapDegrees(target - current);
            if (diff > maxChange) diff = maxChange;
            if (diff < -maxChange) diff = -maxChange;
            return current + diff;
        }
    }
}
