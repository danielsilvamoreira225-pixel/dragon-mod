package com.dragonkingdoms.entity.renderer;

import com.dragonkingdoms.DragonKingdoms;
import com.dragonkingdoms.entity.AncientDragonEntity;
import com.dragonkingdoms.entity.model.AncientDragonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Liga o modelo (AncientDragonModel) e a textura ao Dragão Ancião.
 * A escala (scale) deixa a criatura visivelmente maior que um jogador,
 * reforçando a sensação de "grande predador antigo".
 */
public class AncientDragonRenderer extends LivingEntityRenderer<AncientDragonEntity, AncientDragonModel.State, AncientDragonModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(DragonKingdoms.MOD_ID, "textures/entity/ancient_dragon.png");

    public AncientDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new AncientDragonModel(context.bakeLayer(AncientDragonModel.LAYER_LOCATION)), 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(AncientDragonModel.State state) {
        return TEXTURE;
    }

    @Override
    public AncientDragonModel.State createRenderState() {
        return new AncientDragonModel.State();
    }

    @Override
    public void extractRenderState(AncientDragonEntity entity, AncientDragonModel.State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        AncientDragonModel.State.extract(entity, state, partialTick);
    }
}
