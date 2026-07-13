package com.dragonkingdoms.entity;

import com.dragonkingdoms.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Guardião Sombrio (Shadow Guardian)
 * ----------------------------------
 * Monstro raro que só aparece perto de ruínas antigas, protegendo os
 * segredos deixados pelos reinos esquecidos. Muito mais forte que os
 * outros monstros do mod - funciona como um "miniboss" de estrutura.
 * Estende Zombie para reaproveitar o modelo/renderer humanóide padrão.
 *
 * Vida: 60 (30 corações) | Dano: 11
 * IA: persegue com determinação, regenera vida lentamente
 * Drops: ver loot_table/entities/shadow_guardian.json (inclui Cristal Mágico)
 */
public class ShadowGuardianEntity extends Zombie {

    public ShadowGuardianEntity(EntityType<? extends ShadowGuardianEntity> type, Level level) {
        super(type, level);
        this.xpReward = 40; // recompensa de XP bem maior - é um mob especial
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ATTACK_DAMAGE, 11.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Regeneração lenta: reforça a ideia de "guardião" vinculado às ruínas.
        if (!this.level().isClientSide() && this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.heal(1.0F);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.SHADOW_GUARDIAN_AMBIENT.get();
    }
}
