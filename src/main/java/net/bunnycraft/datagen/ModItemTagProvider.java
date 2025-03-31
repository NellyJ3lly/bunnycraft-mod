package net.bunnycraft.datagen;

import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModTools.COPPER_SWORD)
                .add(ModTools.STEEL_SWORD);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModTools.COPPER_PICKAXE)
                .add(ModTools.STEEL_PICKAXE);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModTools.COPPER_HOE)
                .add(ModTools.STEEL_HOE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModTools.COPPER_SHOVEL)
                .add(ModTools.STEEL_SHOVEL);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModTools.COPPER_AXE)
                .add(ModTools.STEEL_AXE);

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ROSEGOLD_INGOT)
                .add(ModItems.STEEL_INGOT);

        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.PANCAKE_RABBIT);
    }
}
