package com.dragonkingdoms.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 * Goal de ataque à distância do Dragão Ancião: quando o alvo está dentro do
 * alcance e à vista, o dragão "sopra fogo", causando dano e ateando fogo no
 * alvo. Simplificado (sem entidade de bola de fogo) para manter o mod leve.
 */
public class DragonFireAttackGoal extends Goal {

    private final AncientDragonEntity dragon;
    private int attackCooldown;

    private static final double FIRE_RANGE = 10.0D;
    private static final int FIRE_DURATION_TICKS = 60; // 3 segundos de fogo
    private static final int COOLDOWN_TICKS = 45; // ~2.25s entre ataques

    public DragonFireAttackGoal(AncientDragonEntity dragon) {
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.dragon.getTarget();
        return target != null && target.isAlive() && this.dragon.distanceToSqr(target) <= FIRE_RANGE * FIRE_RANGE;
    }

    @Override
    public void start() {
        this.attackCooldown = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.dragon.getTarget();
        if (target == null) return;

        this.dragon.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (--this.attackCooldown <= 0 && this.dragon.hasLineOfSight(target)) {
            // Sinaliza a animação de sopro de fogo para o cliente
            this.dragon.triggerFireBreathAnimation();

            target.setRemainingFireTicks(FIRE_DURATION_TICKS);
            target.hurt(this.dragon.damageSources().mobAttack(this.dragon), 6.0F);

            this.dragon.playSound(com.dragonkingdoms.registry.ModSounds.DRAGON_ROAR.get(), 2.0F, 1.0F);
            this.attackCooldown = COOLDOWN_TICKS;
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.dragon.getTarget();
        return target != null && target.isAlive() && target instanceof Player;
    }
}
