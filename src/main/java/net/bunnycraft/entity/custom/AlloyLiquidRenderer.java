package net.bunnycraft.entity.custom;

import net.bunnycraft.BunnycraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class AlloyLiquidRenderer extends EntityRenderer<AlloyLiquidEntity> {



    public AlloyLiquidRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(AlloyLiquidEntity entity) {
        return BunnycraftClient.GRAYSCALE_LAVA_ID;
    }


    public void render(AlloyLiquidEntity entity, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {


        super.render(entity, entityYaw, tickDelta, matrices, vertexConsumers, light);

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        SpriteAtlasTexture blockAtlas = MinecraftClient.getInstance().getBakedModelManager().getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);

        Sprite sprite = blockAtlas.getSprite(BunnycraftClient.GRAYSCALE_LAVA_ID);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));

        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f positionMatrix = matrixEntry.getPositionMatrix();
        Matrix3f normalMatrix = matrixEntry.getNormalMatrix();

        float halfSize = 0.5f;

        int hexColor = entity.getHueColor();

        int r = (hexColor >> 16) & 0xFF;
        int g = (hexColor >> 8) & 0xFF;
        int b = hexColor & 0xFF;
        int a = 255;

        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();

        // Top-Left vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix, -halfSize,  halfSize, 0f, r, g, b, a, minU, minV, light); // Use sprite's minU, minV
        // Bottom-Left vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix, -halfSize, -halfSize, 0f, r, g, b, a, minU, maxV, light); // Use sprite's minU, maxV
        // Bottom-Right vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix,  halfSize, -halfSize, 0f, r, g, b, a, maxU, maxV, light); // Use sprite's maxU, maxV
        // Top-Right vertex
        addVertex(vertexConsumer, positionMatrix, normalMatrix,  halfSize,  halfSize, 0f, r, g, b, a, maxU, minV, light); // Use sprite's maxU, minV

        matrices.pop();

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
