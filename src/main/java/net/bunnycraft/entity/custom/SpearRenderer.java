package net.bunnycraft.entity.custom;


import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModTools;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import static net.bunnycraft.entity.custom.SpearEntity.MATERIAL;


public class SpearRenderer extends EntityRenderer<SpearEntity> {

    protected SpearModel spearModel;


    public SpearRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        //gets the model, use this to use different models based on logic
        this.spearModel = new SpearModel(ctx.getPart(SpearModel.SPEAR));
    }

    //copied from the trident
    public void render(SpearEntity spearEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, spearEntity.prevYaw, spearEntity.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, spearEntity.prevPitch, spearEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumerProvider, this.spearModel.getLayer(this.getTexture(spearEntity)), false, spearEntity.isEnchanted()
        );
        this.spearModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(spearEntity, f, g, matrixStack, vertexConsumerProvider, i);

    }

    //helper method, gets the texture to use
    @Override
    public Identifier getTexture(SpearEntity entity) {

        return Identifier.of(Bunnycraft.MOD_ID, "textures/entity/spear/model_" + ModTools.spearList.get((int) entity.getDataTracker().get(MATERIAL)).getTranslationKey().substring(16) + ".png");

    }
}
