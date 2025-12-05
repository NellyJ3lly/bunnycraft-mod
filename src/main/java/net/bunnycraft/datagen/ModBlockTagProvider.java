package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
                .add(ModBlocks.STEEL_BLOCK)
                .add(ModBlocks.BUDDING_ECHO)
                .add(ModBlocks.ECHO_CLUSTER);

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
                .add(ModBlocks.STEEL_BLOCK)
                .add(ModBlocks.BUDDING_ECHO)
                .add(ModBlocks.ECHO_CLUSTER);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_COPPER_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_ROSE_GOLD_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(BlockTags.LOGS)
                .add(ModBlocks.SCULK_WOOD_LOG)
                .add(ModBlocks.SCULK_WOOD_WOOD)
                .add(ModBlocks.STRIPPED_SCULK_WOOD_LOG)
                .add(ModBlocks.STRIPPED_SCULK_WOOD_WOOD);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(ModBlocks.SCULK_WOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.SCULK_WOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.SCULK_WOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.BUTTONS)
                .add(ModBlocks.SCULK_WOOD_FENCE);

        getOrCreateTagBuilder(BlockTags.STAIRS)
                .add(ModBlocks.ECHO_BRICK_STAIRS)
                .add(ModBlocks.SCULK_WOOD_STAIRS);
        getOrCreateTagBuilder(BlockTags.SLABS)
                .add(ModBlocks.ECHO_BRICK_SLAB)
                .add(ModBlocks.SCULK_WOOD_SLAB);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.ECHO_BRICK_WALL);

        getOrCreateTagBuilder(ModTags.Blocks.SCULK_REPLACEABLE_NON_FULL_BLOCK)
                .add(Blocks.OAK_SAPLING)
                .add(Blocks.AMETHYST_CLUSTER)
                .add(Blocks.SMALL_AMETHYST_BUD)
                .add(Blocks.MEDIUM_AMETHYST_BUD)
                .add(Blocks.LARGE_AMETHYST_BUD);

        getOrCreateTagBuilder(BlockTags.CLIMBABLE)
                .add(Blocks.SCULK);

        getOrCreateTagBuilder(ModTags.Blocks.BLOCKS_CAN_ECHOLOCATE)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)
                .add(Blocks.NETHER_QUARTZ_ORE)
                .add(Blocks.ANCIENT_DEBRIS);
    }
}