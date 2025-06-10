package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.custom.EnchantingStandEntityRenderer;
import net.bunnycraft.block.entity.custom.EnchantingStandScreen;
import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.entity.custom.*;
import net.bunnycraft.util.ModModelPredicates;
import net.bunnycraft.util.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

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

        EntityRendererRegistry.register(ModEntities.ALLOY_LIQUID_ENTITY, AlloyLiquidRenderer::new);


        BlockEntityRendererFactories.register(ModBlockEntities.ENCHANTING_STAND_ENTITY, EnchantingStandEntityRenderer :: new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENCHANTING_STAND, RenderLayer.getCutout());

        //registers the screen for enchanting stand
        HandledScreens.register(ModScreenHandlers.ENCHANTING_STAND_SCREEN_HANDLER_TYPE, EnchantingStandScreen::new);

    }
}
