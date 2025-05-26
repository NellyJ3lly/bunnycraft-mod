package net.bunnycraft.entity.custom;

import net.bunnycraft.Bunnycraft;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RoseGoldSpearModel extends EntityModel<SpearEntity> {
    // Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

    public static final EntityModelLayer ROSE_GOLD_SPEAR = new EntityModelLayer(Identifier.of(Bunnycraft.MOD_ID, "rose_gold_spear_entity"), "main");
    private final ModelPart spear;

    public RoseGoldSpearModel(ModelPart root) {
        this.spear = root.getChild("spear");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData spear = modelPartData.addChild("spear", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -22.0F, -0.5F, 1.0F, 23.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 7).cuboid(-1.0F, -24.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 2).cuboid(-0.5F, -30.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 2).cuboid(-1.0F, -29.0F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 10).cuboid(-2.5F, -26.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-2.5F, -25.0F, -0.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 4).cuboid(1.5F, -26.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(SpearEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        spear.render(matrices, vertices, light, overlay, color);
    }
}
