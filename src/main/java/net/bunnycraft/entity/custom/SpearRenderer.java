package net.bunnycraft.entity.custom;


import net.bunnycraft.Bunnycraft;
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

    protected SpearModel model;
    public static final Identifier STEELTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_steel_spear.png");
    public static final Identifier NETHERITETEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_netherite_spear.png");
    public static final Identifier DIAMONDTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_diamond_spear.png");
    public static final Identifier GOLDENTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_golden_spear.png");
    public static final Identifier IRONTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_iron_spear.png");
    public static final Identifier COPPERTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_copper_spear.png");
    public static final Identifier STONETEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_stone_spear.png");
    public static final Identifier WOODENTEXTURE = Identifier.of(Bunnycraft.MOD_ID,"textures/entity/model_wooden_spear.png");

    public SpearRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        //gets the model, use this to use different models based on logic
        this.model = new SpearModel(ctx.getPart(SpearModel.SPEAR));
    }

    //copied from the trident
    public void render(SpearEntity spearEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, spearEntity.prevYaw, spearEntity.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, spearEntity.prevPitch, spearEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
                vertexConsumerProvider, this.model.getLayer(this.getTexture(spearEntity)), false, spearEntity.isEnchanted()
        );
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(spearEntity, f, g, matrixStack, vertexConsumerProvider, i);

    }

    //helper method, gets the texture to use
    @Override
    public Identifier getTexture(SpearEntity entity) {
        //get the tool material and return the texture based on it
        return switch (entity.getDataTracker().get(MATERIAL)) {
            case 0 -> WOODENTEXTURE;
            case 1 -> STONETEXTURE;
            case 2 -> COPPERTEXTURE;
            case 3 -> IRONTEXTURE;
            case 4 -> GOLDENTEXTURE;
            case 5 -> DIAMONDTEXTURE;
            case 6 -> NETHERITETEXTURE;
            case 7 -> STEELTEXTURE;
            default -> null;
        };

    }
}
