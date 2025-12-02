package net.bunnycraft.interfaces;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.AbstractMap;
import java.util.Map;

public interface ConvertableBlocks {
    static Map<Block,Block> BLOCK_CONVERSION() {
        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Blocks.BUDDING_AMETHYST,ModBlocks.BUDDING_ECHO),
                new AbstractMap.SimpleEntry<>(Blocks.AMETHYST_BLOCK,ModBlocks.ECHO_BLOCK),
                new AbstractMap.SimpleEntry<>(Blocks.OAK_SAPLING,ModBlocks.SCULK_WOOD_SAPLING),
                new AbstractMap.SimpleEntry<>(Blocks.SMALL_AMETHYST_BUD,ModBlocks.SMALL_ECHO_BUD),
                new AbstractMap.SimpleEntry<>(Blocks.MEDIUM_AMETHYST_BUD,ModBlocks.MEDIUM_ECHO_BUD),
                new AbstractMap.SimpleEntry<>(Blocks.LARGE_AMETHYST_BUD,ModBlocks.LARGE_ECHO_BUD),
                new AbstractMap.SimpleEntry<>(Blocks.AMETHYST_CLUSTER,ModBlocks.ECHO_CLUSTER)
        );
    }

    default void convertAboveBlock(World world, BlockPos pos) {
        Block aboveBlock = world.getBlockState(pos.add(0,1,0)).getBlock();

        assert aboveBlock != null;

        if (!world.isClient) {
            if (ConvertableBlocks.BLOCK_CONVERSION().containsKey(aboveBlock)) {
                world.setBlockState(pos.add(0,1,0),ConvertableBlocks.BLOCK_CONVERSION().get(aboveBlock).getDefaultState(),3);
            }
        }
    }
}
