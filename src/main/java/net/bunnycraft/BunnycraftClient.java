package net.bunnycraft;

import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.entity.custom.*;
import net.bunnycraft.item.armor.ModArmors;
import net.bunnycraft.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
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


    }
}
