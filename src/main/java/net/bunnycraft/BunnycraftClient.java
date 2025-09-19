package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.custom.CauldronAlloyerEntity;
import net.bunnycraft.block.entity.custom.CauldronAlloyerEntityRenderer;
import net.bunnycraft.block.entity.custom.EnchantingStandEntityRenderer;
import net.bunnycraft.block.entity.custom.EnchantingStandScreen;
import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.entity.custom.RoseGoldSpearModel;
import net.bunnycraft.entity.custom.RoseGoldSpearRenderer;
import net.bunnycraft.entity.custom.SpearModel;
import net.bunnycraft.entity.custom.SpearRenderer;
import net.bunnycraft.networking.CauldronAlloyerS2CPayload;
import net.bunnycraft.util.ModModelPredicates;
import net.bunnycraft.util.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BunnycraftClient implements ClientModInitializer {

    public static final Identifier GRAYSCALE_LAVA_ID = Identifier.of(Bunnycraft.MOD_ID, "block/alloy_liquid");

    @Override
    public void onInitializeClient() {

        ModModelPredicates.registerModelPredicates();

        //registers the model for the spear entity
        EntityModelLayerRegistry.registerModelLayer(SpearModel.SPEAR, SpearModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SPEAR, SpearRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RoseGoldSpearModel.ROSE_GOLD_SPEAR, RoseGoldSpearModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ROSE_GOLD_SPEAR, RoseGoldSpearRenderer::new);



        BlockEntityRendererFactories.register(ModBlockEntities.ENCHANTING_STAND_ENTITY, EnchantingStandEntityRenderer :: new);
        BlockEntityRendererFactories.register(ModBlockEntities.CAULDRON_ALLOYER_ENTITY, CauldronAlloyerEntityRenderer :: new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENCHANTING_STAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MS_PIPIS_BLOCK, RenderLayer.getCutout());

        //registers the screen for enchanting stand
        HandledScreens.register(ModScreenHandlers.ENCHANTING_STAND_SCREEN_HANDLER_TYPE, EnchantingStandScreen::new);


        //recives the packet which tells which cauldron has what items and processes the data
        ClientPlayNetworking.registerGlobalReceiver(CauldronAlloyerS2CPayload.ID, (payload, context) -> {

            ClientWorld world = context.client().world;

            if (world == null) {
                return;
            }

            int[] items = payload.items();
            int color = payload.fluidColor();
            float level = payload.fluidLevel();
            BlockPos pos = payload.pos();
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity == null) {
                return;
            } else if (blockEntity instanceof CauldronAlloyerEntity cauldronEntity){

                cauldronEntity.setClientRenderItems(0, Item.byRawId(items[0]).getDefaultStack());
                cauldronEntity.setClientRenderItems(1, Item.byRawId(items[1]).getDefaultStack());

                cauldronEntity.setClientRenderColor(color);

                cauldronEntity.setClientFluidLevel(level);


            }

        });

    }
}
