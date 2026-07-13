package com.dragonkingdoms.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Material customizado (Tier) da Espada do Reino Antigo: mais forte que
 * diamante e mais fraco que netherite, representando uma arma lendária dos
 * reinos esquecidos, forjada a partir de Escamas de Dragão e Cristal Mágico.
 */
public record AncientKingdomTier() implements net.minecraft.world.item.Tier {

    @Override
    public int getUses() {
        return 1900; // diamante = 1561, netherite = 2031
    }

    @Override
    public float getSpeed() {
        return 9.0F; // diamante = 8.0, netherite = 9.0
    }

    @Override
    public float getAttackDamageBonus() {
        return 4.0F; // diamante = 3.0, netherite = 4.0
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return 18; // diamante = 10, netherite = 15
    }

    @Override
    public Ingredient getRepairIngredient() {
        // Reparado com Escamas de Dragão - ver ModItems.DRAGON_SCALE
        return Ingredient.of(com.dragonkingdoms.registry.ModItems.DRAGON_SCALE.get());
    }
}
