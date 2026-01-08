package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.CauldronAlloyerEntity;
import net.bunnycraft.block.entity.SculkBatteryBlockEntity;
import net.bunnycraft.client.render.CauldronAlloyerEntityRenderer;
import net.bunnycraft.client.render.EnchantingStandEntityRenderer;
import net.bunnycraft.client.render.SculkBatteryBlockEntityRenderer;
import net.bunnycraft.client.screen.EnchantingStandScreen;
import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.entity.custom.RoseGoldSpearModel;
import net.bunnycraft.entity.custom.RoseGoldSpearRenderer;
import net.bunnycraft.entity.custom.SpearModel;
import net.bunnycraft.entity.custom.SpearRenderer;
import net.bunnycraft.networking.CauldronAlloyerS2CPayload;
import net.bunnycraft.networking.SculkBatteryS2CPayload;
import net.bunnycraft.screen.custom.BunnyBankScreen;
import net.bunnycraft.util.ModModelPredicates;
import net.bunnycraft.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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

import java.util.List;

import static net.minecraft.component.type.DyedColorComponent.getColor;

public class BunnycraftClient implements ClientModInitializer {

    public static final Identifier GRAYSCALE_LAVA_ID = Identifier.of(Bunnycraft.MOD_ID, "block/alloy_liquid");

    public static final int DIVING_SUIT_DEFAULT = 0xFF825234;

    public static final List<Item> DIVING_SUIT_PIECES = List.of(
            ModArmors.DIVING_BOOTS,
            ModArmors.DIVING_CHESTPLATE,
            ModArmors.DIVING_LEGGINGS
    );

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
        BlockEntityRendererFactories.register(ModBlockEntities.SCULK_BATTERY_BLOCK_ENTITY, SculkBatteryBlockEntityRenderer:: new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENCHANTING_STAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MS_PIPIS_BLOCK, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ECHO_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_ECHO_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_ECHO_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_ECHO_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_WOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SCULK_BATTERY, RenderLayer.getCutout());
        //registers the screen for enchanting stand
        HandledScreens.register(ModScreenHandlers.ENCHANTING_STAND_SCREEN_HANDLER_TYPE, EnchantingStandScreen::new);
        HandledScreens.register(ModScreenHandlers.BUNNY_BANK_SCREEN_HANDLER, BunnyBankScreen::new);

        DIVING_SUIT_PIECES.forEach(item -> {
            ColorProviderRegistry.ITEM.register(
                    (stack, tintIndex) -> tintIndex > 0 ? -1 : getColor(stack, DIVING_SUIT_DEFAULT), item);
        });



        //recives the packet which tells which cauldron has what items and processes the data
        ClientPlayNetworking.registerGlobalReceiver(SculkBatteryS2CPayload.ID, (payload, context) -> {

            ClientWorld world = context.client().world;

            if (world == null) {
                return;
            }

            int experienceLevel = payload.experienceLevel();
            int totalExperience = payload.totalExperience();
            BlockPos pos = payload.pos();
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof SculkBatteryBlockEntity sculkBatteryBlockEntity){

                sculkBatteryBlockEntity.setClientExperienceLevels(experienceLevel);
                sculkBatteryBlockEntity.setClientTotalExperience(totalExperience);
            }
        });

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
