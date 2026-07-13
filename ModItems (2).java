package com.dragonkingdoms.registry;

import com.dragonkingdoms.DragonKingdoms;
import com.dragonkingdoms.item.AncientKingdomTier;
import com.dragonkingdoms.item.RoyalKnightArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registro central de todos os itens novos do Dragon Kingdoms:
 *
 * - Espada do Reino Antigo: arma corpo a corpo lendária
 * - Armadura de Cavaleiro Real: conjunto completo (capacete/peito/calça/bota)
 * - Escama de Dragão: material de crafting, drop do Dragão Ancião
 * - Ovo de Dragão: item de troféu/decoração raro
 * - Cristal Mágico: material raro encontrado em tesouros
 */
public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DragonKingdoms.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DragonKingdoms.MOD_ID);

    public static final DeferredRegister<net.minecraft.world.item.ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, DragonKingdoms.MOD_ID);

    /** Material único da Armadura de Cavaleiro Real, reaproveitado pelas 4 peças abaixo. */
    public static final DeferredHolder<net.minecraft.world.item.ArmorMaterial, net.minecraft.world.item.ArmorMaterial> ROYAL_KNIGHT_MATERIAL =
            ARMOR_MATERIALS.register("royal_knight", RoyalKnightArmorMaterial::create);

    // ---------------------------------------------------------------
    // Materiais de crafting
    // ---------------------------------------------------------------

    /** Escama de Dragão - drop do Dragão Ancião e do Guardião Sombrio; usada para forjar itens lendários. */
    public static final DeferredItem<Item> DRAGON_SCALE =
            ITEMS.registerSimpleItem("dragon_scale", new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE));

    /** Cristal Mágico - material raríssimo, encontrado apenas em tesouros de estruturas. */
    public static final DeferredItem<Item> MAGIC_CRYSTAL =
            ITEMS.registerSimpleItem("magic_crystal", new Item.Properties().rarity(net.minecraft.world.item.Rarity.EPIC));

    /** Ovo de Dragão - item de troféu, prova de que o jogador derrotou um Dragão Ancião. */
    public static final DeferredItem<Item> DRAGON_EGG_TROPHY =
            ITEMS.registerSimpleItem("dragon_egg_trophy", new Item.Properties()
                    .rarity(net.minecraft.world.item.Rarity.EPIC)
                    .stacksTo(1));

    // ---------------------------------------------------------------
    // Espada do Reino Antigo
    // ---------------------------------------------------------------

    public static final DeferredItem<SwordItem> ANCIENT_KINGDOM_SWORD =
            ITEMS.register("ancient_kingdom_sword", () -> new SwordItem(
                    new AncientKingdomTier(),
                    new Item.Properties()
                            .attributes(SwordItem.createAttributes(new AncientKingdomTier(), 3, -2.4F))
                            .rarity(net.minecraft.world.item.Rarity.EPIC)
            ));

    // ---------------------------------------------------------------
    // Armadura de Cavaleiro Real (registrada via ArmorMaterial + tags)
    // ---------------------------------------------------------------

    public static final DeferredItem<ArmorItem> ROYAL_KNIGHT_HELMET =
            ITEMS.register("royal_knight_helmet", () -> new ArmorItem(
                    ROYAL_KNIGHT_MATERIAL,
                    ArmorType.HELMET,
                    new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));

    public static final DeferredItem<ArmorItem> ROYAL_KNIGHT_CHESTPLATE =
            ITEMS.register("royal_knight_chestplate", () -> new ArmorItem(
                    ROYAL_KNIGHT_MATERIAL,
                    ArmorType.CHESTPLATE,
                    new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));

    public static final DeferredItem<ArmorItem> ROYAL_KNIGHT_LEGGINGS =
            ITEMS.register("royal_knight_leggings", () -> new ArmorItem(
                    ROYAL_KNIGHT_MATERIAL,
                    ArmorType.LEGGINGS,
                    new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));

    public static final DeferredItem<ArmorItem> ROYAL_KNIGHT_BOOTS =
            ITEMS.register("royal_knight_boots", () -> new ArmorItem(
                    ROYAL_KNIGHT_MATERIAL,
                    ArmorType.BOOTS,
                    new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));

    // ---------------------------------------------------------------
    // Aba de criativo
    // ---------------------------------------------------------------

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DRAGON_KINGDOMS_TAB =
            CREATIVE_TABS.register("dragon_kingdoms_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dragonkingdoms"))
                    .icon(() -> new ItemStack(ANCIENT_KINGDOM_SWORD.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ANCIENT_KINGDOM_SWORD.get());
                        output.accept(ROYAL_KNIGHT_HELMET.get());
                        output.accept(ROYAL_KNIGHT_CHESTPLATE.get());
                        output.accept(ROYAL_KNIGHT_LEGGINGS.get());
                        output.accept(ROYAL_KNIGHT_BOOTS.get());
                        output.accept(DRAGON_SCALE.get());
                        output.accept(MAGIC_CRYSTAL.get());
                        output.accept(DRAGON_EGG_TROPHY.get());
                    })
                    .build());
}
