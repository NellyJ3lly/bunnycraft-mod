package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool pancakerabbittexturepool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PANCAKE_RABBIT_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PANCAKE_RABBIT_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROSEGOLD_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.PANCAKE_RABBIT, Models.GENERATED);

        itemModelGenerator.register(ModTools.COPPER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.COPPER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModTools.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModTools.COPPER_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModTools.STEEL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.STEEL_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.STEEL_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.STEEL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModTools.STEEL_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModTools.ROSE_GOLD_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.ROSE_GOLD_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.ROSE_GOLD_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModTools.ROSE_GOLD_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModTools.ROSE_GOLD_SHOVEL, Models.HANDHELD);
    }
}
