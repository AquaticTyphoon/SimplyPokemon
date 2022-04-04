package com.aquatictyphoon.pokemonmod.client.models;

import com.aquatictyphoon.pokemonmod.PokemonMod;
import com.aquatictyphoon.pokemonmod.setup.entities.passive.BidoofEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

//Instead of "public class BidoofModel <T extends BidoofEntity> extends EntityModel<T>" like in 1.16, it is now
// "public class BidoofModel extends EntityModel<BidoofEntity>"
//We also need to register our entity's model layer to the client
// using the EntityRenderersEvent.RegisterLayerDefinitions in our client event bus
public class BidoofModel extends EntityModel<BidoofEntity> {
    public static final String BODY = "body";
    // entity models now have a LAYER_LOCATION that holds a string-reference to the model's body like above
    // and we bake it into the renderer class using EntityRendererProvider.context
    // and pass it to the constructor in the entity's renderer class
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(PokemonMod.MOD_ID, "bidoof"), BODY);
    private float jumpRotation;
    //1.16's ModelRenderer is now ModelPart
    private final ModelPart body;
    private final ModelPart rearLegLeft;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart frontLegRight;
    private final ModelPart frontLegLeft;
    private final ModelPart haunchRight;
    private final ModelPart haunchLeft;
    private final ModelPart rearLegRight;

    //In 1.18, we now specific ModelPart as the root, the other parts such as tail or head are treated as child-objects
    //of the ModelPart so we will need to add them ourselves if so
    public BidoofModel(ModelPart root) {
        this.body = root.getChild("body");
        this.rearLegLeft = root.getChild("rearLegLeft");
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.frontLegRight = root.getChild("frontLegRight");
        this.frontLegLeft = root.getChild("frontLegLeft");
        this.haunchRight = root.getChild("haunchRight");
        this.haunchLeft = root.getChild("haunchLeft");
        this.rearLegRight = root.getChild("rearLegRight");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, -8.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition rearLegLeft = partdefinition.addOrReplaceChild("rearLegLeft", CubeListBuilder.create(), PartPose.offset(3.0F, 17.5F, 3.7F));

        PartDefinition rearLegLeft_r1 = rearLegLeft.addOrReplaceChild("rearLegLeft_r1", CubeListBuilder.create().texOffs(8, 16).mirror().addBox(2.0F, -2.0F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 6.5F, -3.7F, 0.1745F, 0.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(19, 20).mirror().addBox(-1.5F, -4.5F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 22).mirror().addBox(-2.5F, -3.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 21).mirror().addBox(1.5F, -3.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 21).mirror().addBox(-0.5F, -5.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 20.0F, 7.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-2.5F, -1.0F, -5.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(41, 0).mirror().addBox(-3.5F, 0.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(41, 0).mirror().addBox(2.5F, 0.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 0).mirror().addBox(-0.5F, 1.5F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 16.0F, -1.0F));

        PartDefinition earRight = head.addOrReplaceChild("earRight", CubeListBuilder.create().texOffs(58, 0).mirror().addBox(-3.5F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition earLeft = head.addOrReplaceChild("earLeft", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(2.5F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(32, 9).mirror().addBox(-1.0F, 0.5F, -5.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontLegRight = partdefinition.addOrReplaceChild("frontLegRight", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 17.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition frontLegLeft = partdefinition.addOrReplaceChild("frontLegLeft", CubeListBuilder.create().texOffs(8, 16).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 17.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition haunchRight = partdefinition.addOrReplaceChild("haunchRight", CubeListBuilder.create().texOffs(32, 17).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 17.5F, 3.7F, -0.3491F, 0.0F, 0.0F));

        PartDefinition haunchLeft = partdefinition.addOrReplaceChild("haunchLeft", CubeListBuilder.create().texOffs(32, 17).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 17.5F, 3.7F, -0.3491F, 0.0F, 0.0F));

        PartDefinition rearLegRight = partdefinition.addOrReplaceChild("rearLegRight", CubeListBuilder.create(), PartPose.offset(-3.0F, 17.5F, 3.7F));

        PartDefinition rearLegRight_r1 = rearLegRight.addOrReplaceChild("rearLegRight_r1", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-4.0F, -2.0F, 3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 6.5F, -3.7F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    //This is the only part that Blockbench doesn't autogenerate and is empty if you use Blockbench
    //You will need to write your own if you want fully custom math-based movement animation
    //Otherwise, you can check out the setupAnim in vanilla's mob-model classes, I used rabbit for this
    @Override
    public void setupAnim(BidoofEntity p_103548_, float p_103549_, float p_103550_, float p_103551_, float p_103552_, float p_103553_) {
        float f = p_103551_ - (float)p_103548_.tickCount;
        this.head.xRot = p_103553_ * ((float)Math.PI / 180F);
        this.head.yRot = p_103552_ * ((float)Math.PI / 180F);
        this.jumpRotation = Mth.sin(p_103548_.getJumpCompletion(f) * (float)Math.PI);
        this.haunchLeft.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.haunchRight.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rearLegLeft.xRot = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
        this.rearLegRight.xRot = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
        this.frontLegLeft.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
        this.frontLegRight.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
        rearLegLeft.render(poseStack, buffer, packedLight, packedOverlay);
        tail.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        frontLegRight.render(poseStack, buffer, packedLight, packedOverlay);
        frontLegLeft.render(poseStack, buffer, packedLight, packedOverlay);
        haunchRight.render(poseStack, buffer, packedLight, packedOverlay);
        haunchLeft.render(poseStack, buffer, packedLight, packedOverlay);
        rearLegRight.render(poseStack, buffer, packedLight, packedOverlay);
    }
}