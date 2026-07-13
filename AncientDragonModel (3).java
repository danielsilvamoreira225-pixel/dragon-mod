package com.dragonkingdoms.entity.model;

import com.dragonkingdoms.entity.AncientDragonEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/**
 * Modelo geométrico do Dragão Ancião, feito manualmente com cubos (sem
 * dependência de bibliotecas externas de animação, para manter o mod leve).
 * <p>
 * Peças: corpo, pescoço, cabeça, mandíbula, cauda (3 segmentos), 2 asas
 * (cada uma com uma dobra) e 4 pernas.
 * <p>
 * A animação (voo/batida de asas/rugido) acontece em {@link #setupAnim}
 * usando funções senoidais simples baseadas no tempo (ageInTicks) - uma
 * abordagem clássica e leve, sem exigir um sistema de animações externo.
 */
public class AncientDragonModel extends EntityModel<AncientDragonModel.State> {

    public static final net.minecraft.client.model.geom.ModelLayerLocation LAYER_LOCATION =
            new net.minecraft.client.model.geom.ModelLayerLocation(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                            com.dragonkingdoms.DragonKingdoms.MOD_ID, "ancient_dragon"),
                    "main");

    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart wingLeft;
    private final ModelPart wingLeftTip;
    private final ModelPart wingRight;
    private final ModelPart wingRightTip;
    private final ModelPart legFrontLeft;
    private final ModelPart legFrontRight;
    private final ModelPart legBackLeft;
    private final ModelPart legBackRight;

    public AncientDragonModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.wingLeft = this.body.getChild("wing_left");
        this.wingLeftTip = this.wingLeft.getChild("wing_left_tip");
        this.wingRight = this.body.getChild("wing_right");
        this.wingRightTip = this.wingRight.getChild("wing_right_tip");
        this.legFrontLeft = this.body.getChild("leg_front_left");
        this.legFrontRight = this.body.getChild("leg_front_right");
        this.legBackLeft = this.body.getChild("leg_back_left");
        this.legBackRight = this.body.getChild("leg_back_right");
    }

    /**
     * Define a "malha" (layout de cubos e UVs) do dragão. Textura: 256x256.
     * Peças: corpo, pescoço, cabeça, mandíbula, cauda (3 segmentos),
     * 2 asas (cada uma com dobra) e 4 pernas.
     */
    public static LayerDefinition buildFullMesh() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -20.0F, 16.0F, 16.0F, 40.0F),
                PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 60).addBox(-4.0F, -4.0F, -12.0F, 8.0F, 8.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -4.0F, -18.0F, -0.35F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 90).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, -12.0F, 0.2F, 0.0F, 0.0F));

        head.addOrReplaceChild("jaw",
                CubeListBuilder.create().texOffs(0, 115).addBox(-4.0F, 0.0F, -9.0F, 8.0F, 4.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 4.0F, -2.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(0, 135).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(0, 165).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 16.0F));

        tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(0, 190).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 14.0F));

        PartDefinition wingLeft = body.addOrReplaceChild("wing_left",
                CubeListBuilder.create().texOffs(100, 0).addBox(0.0F, -1.0F, -2.0F, 34.0F, 2.0F, 20.0F),
                PartPose.offsetAndRotation(8.0F, -6.0F, -10.0F, 0.0F, 0.5F, 0.2F));

        wingLeft.addOrReplaceChild("wing_left_tip",
                CubeListBuilder.create().texOffs(100, 40).addBox(0.0F, -1.0F, -2.0F, 28.0F, 2.0F, 16.0F),
                PartPose.offsetAndRotation(34.0F, 0.0F, 0.0F, 0.0F, 0.3F, 0.0F));

        PartDefinition wingRight = body.addOrReplaceChild("wing_right",
                CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-34.0F, -1.0F, -2.0F, 34.0F, 2.0F, 20.0F),
                PartPose.offsetAndRotation(-8.0F, -6.0F, -10.0F, 0.0F, -0.5F, -0.2F));

        wingRight.addOrReplaceChild("wing_right_tip",
                CubeListBuilder.create().texOffs(100, 40).mirror().addBox(-28.0F, -1.0F, -2.0F, 28.0F, 2.0F, 16.0F),
                PartPose.offsetAndRotation(-34.0F, 0.0F, 0.0F, 0.0F, -0.3F, 0.0F));

        body.addOrReplaceChild("leg_front_left",
                CubeListBuilder.create().texOffs(180, 60).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F),
                PartPose.offset(7.0F, 6.0F, -12.0F));

        body.addOrReplaceChild("leg_front_right",
                CubeListBuilder.create().texOffs(180, 60).mirror().addBox(-2.5F, 0.0F, -2.5F, 5.0F, 14.0F, 5.0F),
                PartPose.offset(-7.0F, 6.0F, -12.0F));

        body.addOrReplaceChild("leg_back_left",
                CubeListBuilder.create().texOffs(180, 90).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F),
                PartPose.offset(7.0F, 4.0F, 12.0F));

        body.addOrReplaceChild("leg_back_right",
                CubeListBuilder.create().texOffs(180, 90).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F),
                PartPose.offset(-7.0F, 4.0F, 12.0F));

        return LayerDefinition.create(mesh, 256, 256);
    }

    /** Chamado a cada frame para animar o dragão de acordo com seu estado atual. */
    @Override
    public void setupAnim(State state) {
        float age = state.ageInTicks;
        float flap = Mth.sin(age * 0.35F) * (state.isFlapping ? 0.6F : 0.15F);

        // Batida de asas
        this.wingLeft.zRot = 0.2F + flap;
        this.wingLeftTip.zRot = 0.3F + flap * 0.8F;
        this.wingRight.zRot = -0.2F - flap;
        this.wingRightTip.zRot = -0.3F - flap * 0.8F;

        // Balanço suave da cauda, como se estivesse "nadando" no ar
        float tailSwing = Mth.sin(age * 0.15F) * 0.25F;
        this.tail1.yRot = tailSwing;
        this.tail2.yRot = tailSwing * 1.3F;
        this.tail3.yRot = tailSwing * 1.6F;

        // Cabeça olhando na direção do movimento (yRot/xRot vindos do render state)
        this.head.yRot = state.yRotHead * Mth.DEG_TO_RAD;
        this.head.xRot = state.xRotHead * Mth.DEG_TO_RAD;

        // Pernas dobradas levemente para cima durante o voo (não tocam o chão)
        float legTuck = -0.6F;
        this.legFrontLeft.xRot = legTuck;
        this.legFrontRight.xRot = legTuck;
        this.legBackLeft.xRot = legTuck * 0.8F;
        this.legBackRight.xRot = legTuck * 0.8F;

        // Abre a mandíbula durante o ataque de fogo
        this.jaw.xRot = state.isBreathingFire ? 0.6F : 0.05F;
    }

    /**
     * Estado de renderização do dragão: os dados relevantes são copiados da
     * entidade uma vez por frame no lado do cliente (padrão usado desde a
     * separação entre lógica de entidade e renderização).
     */
    public static class State extends LivingEntityRenderState {
        public float ageInTicks;
        public float yRotHead;
        public float xRotHead;
        public boolean isFlapping;
        public boolean isBreathingFire;

        public static void extract(AncientDragonEntity entity, State state, float partialTick) {
            state.ageInTicks = entity.tickCount + partialTick;
            state.yRotHead = entity.getYHeadRot();
            state.xRotHead = entity.getXRot();
            state.isFlapping = entity.isFlapping();
            state.isBreathingFire = entity.isBreathingFire();
        }
    }
}
