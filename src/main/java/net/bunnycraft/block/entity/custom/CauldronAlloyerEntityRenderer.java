package net.bunnycraft.block.entity.custom;

import net.bunnycraft.Bunnycraft;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;


/*
 * This is your custom Block Entity Renderer for CauldronAlloyerEntity.
 */
public class CauldronAlloyerEntityRenderer implements BlockEntityRenderer<CauldronAlloyerEntity> {

    //---------------------------------------------------------------------------------------------------------- Variables for the heating Appearance

    private ItemStack item;

    private final BlockRenderManager blockRenderManager;
    private final BlockModelRenderer blockModelRenderer;

    // The context provides access to various rendering components.
    public CauldronAlloyerEntityRenderer(BlockEntityRendererFactory.Context context) {
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
    public void render(CauldronAlloyerEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockState blockState = blockEntity.getCachedState();

        matrices.push();

        // Get the appropriate VertexConsumer for the block layer (e.g., solid, cutout)
        // For most blocks, the solid layer is appropriate.
        // You might need to adjust this based on your block's render type.
        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());

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

        //render the first item
        item = blockEntity.getItem(0);

        matrices.translate(0.5, 1, 0.5);

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                item,
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
}
