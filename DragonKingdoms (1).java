package com.dragonkingdoms;

import com.dragonkingdoms.registry.ModEntities;
import com.dragonkingdoms.registry.ModItems;
import com.dragonkingdoms.registry.ModSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principal do Dragon Kingdoms.
 * <p>
 * Aqui só acontece o "cadastro" (registro) dos DeferredRegister de cada
 * categoria de conteúdo. A lógica de cada coisa fica nas classes específicas
 * dentro dos pacotes entity/, item/, event/, etc.
 */
@Mod(DragonKingdoms.MOD_ID)
public class DragonKingdoms {

    public static final String MOD_ID = "dragonkingdoms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public DragonKingdoms(IEventBus modEventBus) {
        // Registra cada categoria de conteúdo no barramento de eventos do mod.
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModItems.ARMOR_MATERIALS.register(modEventBus);
        ModItems.CREATIVE_TABS.register(modEventBus);

        // Atributos das entidades (vida, dano, velocidade) precisam ser
        // registrados explicitamente antes do mundo carregar.
        modEventBus.addListener(ModEntities::registerAttributes);

        LOGGER.info("Dragon Kingdoms carregado - que os reinos antigos despertem!");
    }
}
