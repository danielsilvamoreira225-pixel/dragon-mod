package com.dragonkingdoms.item;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.EnumMap;
import java.util.Map;

/**
 * Definição da Armadura de Cavaleiro Real: forjada em ferro e reforçada com
 * Escamas de Dragão, oferecendo proteção acima do ferro comum.
 *
 * NOTA DE COMPATIBILIDADE: a API de ArmorMaterial passou por uma
 * reformulação nas versões recentes do jogo (registro por dados via
 * ResourceKey, incluindo um "equipment asset" que aponta para
 * assets/dragonkingdoms/equipment/royal_knight.json). Se algum
 * nome/assinatura mudar em um snapshot futuro de 26.x, consulte o
 * "porting primer" do NeoForge (neoforged.net) - a lógica de valores
 * (defesa, toughness etc.) abaixo continua válida.
 */
public final class RoyalKnightArmorMaterial {

    private RoyalKnightArmorMaterial() {}

    public static final ResourceKey<ArmorMaterial> ROYAL_KNIGHT =
            ResourceKey.create(Registries.ARMOR_MATERIAL,
                    ResourceLocation.fromNamespaceAndPath(com.dragonkingdoms.DragonKingdoms.MOD_ID, "royal_knight"));

    /** Aponta para assets/dragonkingdoms/equipment/royal_knight.json (texturas da armadura no corpo). */
    public static final ResourceKey<EquipmentAsset> ASSET_ID =
            ResourceKey.create(Registries.EQUIPMENT_ASSET,
                    ResourceLocation.fromNamespaceAndPath(com.dragonkingdoms.DragonKingdoms.MOD_ID, "royal_knight"));

    /** Tag de itens usados para reparar a armadura na bigorna. */
    public static final TagKey<Item> REPAIR_TAG =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(com.dragonkingdoms.DragonKingdoms.MOD_ID, "repairs_royal_knight_armor"));

    /** Pontos de defesa por peça: capacete, peitoral, calça, botas. */
    public static Map<ArmorType, Integer> defensePoints() {
        Map<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
        map.put(ArmorType.BODY, 8);
        return map;
    }

    public static ArmorMaterial create() {
        return new ArmorMaterial(
                20,                 // durabilidade base (multiplicada por peça)
                defensePoints(),
                15,                 // encantabilidade (ferro = 9, diamante = 10)
                Holder.direct(SoundEvents.ARMOR_EQUIP_DIAMOND.value()), // som de equipar
                2.0F,               // toughness (ferro = 0, diamante = 2)
                0.1F,               // knockback resistance
                REPAIR_TAG,         // itens que reparam a armadura (ver data/.../tags/item/)
                ASSET_ID            // textura/asset da armadura no corpo do jogador
        );
    }
}

