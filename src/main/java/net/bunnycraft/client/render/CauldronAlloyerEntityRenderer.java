package net.bunnycraft.client.render;

import net.bunnycraft.BunnycraftClient;
import net.bunnycraft.block.entity.CauldronAlloyerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix3f;
import org.joml.Matrix4f;


/*
 * This is your custom Block Entity Renderer for CauldronAlloyerEntity.
 */
public class CauldronAlloyerEntityRenderer implements BlockEntityRenderer<CauldronAlloyerEntity> {

    //---------------------------------------------------------------------------------------------------------- Variables for the heating Appearance

    private final BlockRenderManager blockRenderManager;
    private final BlockModelRenderer blockModelRenderer;

    // The context provides access to various rendering components.
    public CauldronAlloyerEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.blockModelRenderer = MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer();
        this.blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
    }

    public void itemRender(int Slot,Vec3d translation, int RotateX, int RotateY, CauldronAlloyerEntity blockEntity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(translation.x,translation.y,translation.z);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(RotateX));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(RotateY));

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                blockEntity.getRenderItem(Slot),
                ModelTransformationMode.GROUND,
                light,
                overlay,
                matrices,
                vertexConsumers,
                blockEntity.getWorld(),
                0
        );

        matrices.pop();
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
    public void render(CauldronAlloyerEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockState blockState = blockEntity.getCachedState();

        matrices.push();

        // Get the appropriate VertexConsumer for the block layer
        // For most blocks, the solid layer is appropriate.
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getCutout());

        //render the block
        BakedModel model = blockRenderManager.getModel(blockState);

        this.blockModelRenderer.render(
                matrices.peek(),
                consumer,
                blockState,
                model,
                1,
                1,
                1,
                light,
                overlay
        );

        matrices.pop();

        //render the liquid layer ----------------------------------------------------------------------------------------------------------

        matrices.push();

        matrices.translate(.5, blockEntity.getClientFluidLevel(), .5);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        SpriteAtlasTexture blockAtlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);

        Sprite sprite = blockAtlas.getSprite(BunnycraftClient.GRAYSCALE_LAVA_ID);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));

        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f positionMatrix = matrixEntry.getPositionMatrix();
        Matrix3f normalMatrix = matrixEntry.getNormalMatrix();


        int hexColor = blockEntity.getClientFluidColor();

        int r = (hexColor >> 16) & 0xFF;
        int g = (hexColor >> 8) & 0xFF;
        int b = hexColor & 0xFF;
        int a = 255;

        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();

        // Top-Left vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix, -.5f,  .5f, 0f, r, g, b, a, minU, minV, light); // Use sprite's minU, minV
        // Bottom-Left vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix, -.5f, -.5f, 0f, r, g, b, a, minU, maxV, light); // Use sprite's minU, maxV
        // Bottom-Right vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix,  .5f, -.5f, 0f, r, g, b, a, maxU, maxV, light); // Use sprite's maxU, maxV
        // Top-Right vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix,  .5f,  .5f, 0f, r, g, b, a, maxU, minV, light); // Use sprite's maxU, minV

        matrices.pop();

        //render the first slot ------------------------------------------------------------------------------------------------------
//
//        if (blockEntity.getItems().get(0).getCount() == 0) {
//            System.out.println("NONE");
//        }


        int rotateX = 0;
        int rotateY = 90;
        double y = 0.25;
        double x = 0.5;

        for (int i = 0; i < blockEntity.getItems().get(0).getCount(); i++) {
            rotateX += 30;
            rotateY += 1;
            y += 0.02;
            x += 0.01;

            Vec3d Zero2Position = new Vec3d(x,y,0.5);

            itemRender(0, Zero2Position,rotateX, rotateY,blockEntity,matrices,vertexConsumers,light, overlay);
        }


        //render the second slot ---------------------------------------------------------

        for (int i = 0; i < blockEntity.getItems().get(1).getCount(); i++) {
            rotateX += 30;
            rotateY += 1;
            y += 0.02;
            x += 0.01;

            Vec3d Zero2Position = new Vec3d(0.5,y,0.5);

            itemRender(1, Zero2Position,rotateX, rotateY,blockEntity,matrices,vertexConsumers,light, overlay);
        }
    }

    private void addVertex(VertexConsumer consumer, Matrix4f positionMatrix, Matrix3f normalMatrix,
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
