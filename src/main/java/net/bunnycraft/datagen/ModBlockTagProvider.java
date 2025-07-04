package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.bunnycraft.util.ModTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.PANCAKE_RABBIT_ORE)
                .add(ModBlocks.PANCAKE_RABBIT_BLOCK)
                .add(ModBlocks.COPPER_BUNNYCOIN_BLOCK)
                .add(ModBlocks.GOLD_BUNNYCOIN_BLOCK)
                .add(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK)
                .add(ModBlocks.ROSE_GOLD_BLOCK)
                .add(ModBlocks.STEEL_BLOCK);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.COPPER_BUNNYCOIN_BLOCK)
                .add(ModBlocks.GOLD_BUNNYCOIN_BLOCK)
                .add(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.COPPER_BUNNYCOIN_BLOCK)
                .add(ModBlocks.GOLD_BUNNYCOIN_BLOCK)
                .add(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);
        ;

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PANCAKE_RABBIT_ORE)
                .add(ModBlocks.PANCAKE_RABBIT_BLOCK)
                .add(ModBlocks.ROSE_GOLD_BLOCK)
                .add(ModBlocks.STEEL_BLOCK);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_ROSE_GOLD_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);


    }
}