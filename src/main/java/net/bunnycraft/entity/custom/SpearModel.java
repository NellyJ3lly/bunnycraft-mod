package net.bunnycraft.entity.custom;

import net.bunnycraft.Bunnycraft;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpearModel extends EntityModel<SpearEntity> {
    // Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

        public static final EntityModelLayer SPEAR = new EntityModelLayer(Identifier.of(Bunnycraft.MOD_ID, "spear_entity"), "main");
        private final ModelPart spear;

        public SpearModel(ModelPart root) {
            this.spear = root.getChild("spear");
        }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData spear = modelPartData.addChild("spear", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -26.0F, -0.5F, 1.0F, 27.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 8).cuboid(-0.5F, -30.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 0).cuboid(-1.0F, -29.0F, -0.5F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 4).cuboid(-1.5F, -25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 6).cuboid(0.5F, -25.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
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
