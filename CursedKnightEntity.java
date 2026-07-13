package com.dragonkingdoms.entity;

import com.dragonkingdoms.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * Cavaleiro Amaldiçoado (Cursed Knight)
 * ------------------------------------
 * Um antigo cavaleiro preso em uma armadura amaldiçoada, condenado a vagar
 * por ruínas e castelos abandonados atacando qualquer criatura viva.
 * Estende Zombie para reaproveitar o modelo/renderer/animações humanóides
 * padrão do jogo (mantendo o mod leve) - por isso também pega fogo ao sol,
 * o que reforça bem a temática de morto-vivo amaldiçoado.
 *
 * Vida: 30 (15 corações) | Dano: 7 (dificuldade normal)
 * IA: patrulha, ataque corpo a corpo com espada
 * Drops: ver loot_table/entities/cursed_knight.json
 */
public class CursedKnightEntity extends Zombie {

    public CursedKnightEntity(EntityType<? extends CursedKnightEntity> type, Level level) {
        super(type, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4D);
    }

    @Override
    protected void registerGoals() {
        // Sobrescrevemos totalmente os goals padrão do Zombie (que incluem
        // arrombar portas etc.) para um comportamento mais simples e previsível.
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.05D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.CURSED_KNIGHT_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return ModSounds.CURSED_KNIGHT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CURSED_KNIGHT_DEATH.get();
    }

    /** Ao nascer, o cavaleiro já aparece com sua espada antiga equipada. */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.15F); // 15% de chance de dropar a espada
        return data;
    }
}
