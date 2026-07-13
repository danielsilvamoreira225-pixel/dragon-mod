package com.dragonkingdoms.registry;

import com.dragonkingdoms.DragonKingdoms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Sons customizados usados pelas criaturas do mod.
 * Os arquivos .ogg correspondentes devem existir em:
 * src/main/resources/assets/dragonkingdoms/sounds/
 * e serem listados em assets/dragonkingdoms/sounds.json
 */
public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, DragonKingdoms.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CURSED_KNIGHT_AMBIENT =
            register("cursed_knight_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CURSED_KNIGHT_HURT =
            register("cursed_knight_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CURSED_KNIGHT_DEATH =
            register("cursed_knight_death");

    public static final DeferredHolder<SoundEvent, SoundEvent> CAVE_GOBLIN_AMBIENT =
            register("cave_goblin_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CAVE_GOBLIN_HURT =
            register("cave_goblin_hurt");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHADOW_GUARDIAN_AMBIENT =
            register("shadow_guardian_ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_ROAR =
            register("ancient_dragon_roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_WING_FLAP =
            register("ancient_dragon_wing_flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_DEATH =
            register("ancient_dragon_death");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () ->
                SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DragonKingdoms.MOD_ID, name)));
    }
}
