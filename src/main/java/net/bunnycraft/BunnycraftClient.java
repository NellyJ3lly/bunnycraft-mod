package net.bunnycraft;

import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.entity.custom.RoseGoldSpearModel;
import net.bunnycraft.entity.custom.RoseGoldSpearRenderer;
import net.bunnycraft.entity.custom.SpearModel;
import net.bunnycraft.entity.custom.SpearRenderer;
import net.bunnycraft.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BunnycraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModModelPredicates.registerModelPredicates();

        //registers the model for the spear entity
        EntityModelLayerRegistry.registerModelLayer(SpearModel.SPEAR, SpearModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SPEAR, SpearRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RoseGoldSpearModel.ROSE_GOLD_SPEAR, RoseGoldSpearModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ROSE_GOLD_SPEAR, RoseGoldSpearRenderer::new);
    }
}
