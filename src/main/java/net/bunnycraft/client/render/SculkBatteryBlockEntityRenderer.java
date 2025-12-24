package net.bunnycraft.client.render;

import net.bunnycraft.BunnycraftClient;
import net.bunnycraft.block.entity.SculkBatteryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;


/*
 * This is your custom Block Entity Renderer for CauldronAlloyerEntity.
 */
public class SculkBatteryBlockEntityRenderer implements BlockEntityRenderer<SculkBatteryBlockEntity> {

    //---------------------------------------------------------------------------------------------------------- Variables for the heating Appearance

    private final BlockRenderManager blockRenderManager;
    private final BlockModelRenderer blockModelRenderer;

    // The context provides access to various rendering components.
    public SculkBatteryBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.blockModelRenderer = MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer();
        this.blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
    }

    /*
     * This is the main rendering method.
     *
     * @param blockEntity The CauldronAlloyerEntity instance to be rendered.
     * @param tickDelta The partial tick time, used for smooth animations.
     * @param matrices The MatrixStack for transformations (translation, rotation, scaling).
     * @param vertexConsumers The buffer for drawing vertices.
     * @param light The packed light value for the block.
     * @param overlay The packed overlay value (e.g., hurt effect).
     */
    @Override
    public void render(SculkBatteryBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float heldLevels = (float) Math.clamp((float) blockEntity.getClientTotalExperience()/blockEntity.getMaxExperience(),0,0.99);

        matrices.push();

        MatrixStack.Entry matrixEntry = matrices.peek();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        Matrix4f positionMatrix = matrixEntry.getPositionMatrix();
        matrices.translate(0.5,0, 0.99);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0));

        createSide(vertexConsumer,positionMatrix,light,0.5F,heldLevels,0,0F);

        matrices.pop();

        matrices.push();

        matrices.translate(.5, heldLevels, .5);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        MatrixStack.Entry matrixEntry2 = matrices.peek();
        VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        Matrix4f positionMatrix2 = matrixEntry2.getPositionMatrix();

        createSide(vertexConsumer2,positionMatrix2,light);

        matrices.pop();

        matrices.push();

        matrices.translate(.99, 0, .5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        MatrixStack.Entry matrixEntry3 = matrices.peek();
        VertexConsumer vertexConsumer3 = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        Matrix4f positionMatrix3 = matrixEntry3.getPositionMatrix();

        createSide(vertexConsumer3,positionMatrix3,light,0.5F,heldLevels,0,0);

        matrices.pop();

        matrices.push();

        matrices.translate(.01, 0, .5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        MatrixStack.Entry matrixEntry4 = matrices.peek();
        VertexConsumer vertexConsumer4 = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        Matrix4f positionMatrix4 = matrixEntry4.getPositionMatrix();

        createSide(vertexConsumer4,positionMatrix4,light,0.5F,heldLevels,0,0);

        matrices.pop();

        matrices.push();

        matrices.translate(.5, 0, .01);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0));
        MatrixStack.Entry matrixEntry5 = matrices.peek();
        VertexConsumer vertexConsumer5 = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        Matrix4f positionMatrix5 = matrixEntry5.getPositionMatrix();

        createSide(vertexConsumer5,positionMatrix5,light,0.5F,heldLevels,0,0);

        matrices.pop();
    }

    public void createSide(VertexConsumer vertexConsumer, Matrix4f positionMatrix, int light) {
        createSide(vertexConsumer,positionMatrix,light,0.5F,0.5F,0F,0.5F);
    }

    public void createSide(VertexConsumer vertexConsumer, Matrix4f positionMatrix, int light,float x, float y, float z, float lowery) {
        int hexColor = 0x4dff58;

        int r = (hexColor >> 16) & 0xFF;
        int g = (hexColor >> 8) & 0xFF;
        int b = hexColor & 0xFF;
        int a = 255;

        SpriteAtlasTexture blockAtlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        Sprite sprite = blockAtlas.getSprite(BunnycraftClient.GRAYSCALE_LAVA_ID);
        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();

        // Top-Left vertex
        addVertex(vertexConsumer, positionMatrix, -x,  y, z, r, g, b, a, minU, minV, light); // Use sprite's minU, minV
        // Bottom-Left vertex
        addVertex(vertexConsumer, positionMatrix, -x, -lowery, z, r, g, b, a, minU, maxV, light); // Use sprite's minU, maxV
        // Bottom-Right vertex
        addVertex(vertexConsumer, positionMatrix,  x, -lowery, z, r, g, b, a, maxU, maxV, light); // Use sprite's maxU, maxV
        // Top-Right vertex
        addVertex(vertexConsumer, positionMatrix,  x,  y, z, r, g, b, a, maxU, minV, light); // Use sprite's maxU, minV
    }

    private void addVertex(VertexConsumer consumer, Matrix4f positionMatrix,
                           float x, float y, float z,
                           int r, int g, int b, int a,
                           float u, float v, int light) {
        consumer.vertex(positionMatrix, x, y, z)       // Position
                .color(r, g, b, a)                     // Color (can tint texture)
                .texture(u, v)                         // UV Texture coordinates
                .overlay(OverlayTexture.DEFAULT_UV)    // Overlay (for damage flash, etc. DEFAULT_UV = no overlay)
                .light(light)                          // Lightmap (for scene lighting)
                .normal( 0f, 0f, 1f);  // Normal vector (for lighting direction, (0,0,1) is facing positive Z in local space)
    }


}
