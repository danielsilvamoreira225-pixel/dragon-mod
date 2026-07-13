package com.dragonkingdoms.client;

import com.dragonkingdoms.DragonKingdoms;
import com.dragonkingdoms.entity.model.AncientDragonModel;
import com.dragonkingdoms.entity.renderer.AncientDragonRenderer;
import com.dragonkingdoms.registry.ModEntities;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Registro do lado cliente: liga cada EntityType a um renderer.
 * <p>
 * Os 3 monstros terrestres (Cavaleiro Amaldiçoado, Goblin das Cavernas e
 * Guardião Sombrio) estendem Zombie e por isso reaproveitam o
 * ZombieRenderer/modelo humanóide padrão do jogo - mantendo o mod leve.
 * A diferenciação visual vem das texturas próprias em textures/entity/.
 * Se quiser modelos 100% customizados no futuro, troque por um
 * HumanoidModel próprio, seguindo o mesmo padrão usado no AncientDragonModel.
 */
@EventBusSubscriber(modid = DragonKingdoms.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRenderers {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AncientDragonModel.LAYER_LOCATION, AncientDragonModel::buildFullMesh);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.ANCIENT_DRAGON.get(), AncientDragonRenderer::new);
        event.registerEntityRenderer(ModEntities.CURSED_KNIGHT.get(), ZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.CAVE_GOBLIN.get(), ZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_GUARDIAN.get(), ZombieRenderer::new);
    }
}
