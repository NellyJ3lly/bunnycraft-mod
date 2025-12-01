package net.bunnycraft.interfaces;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.AbstractMap;
import java.util.Map;

public interface ConvertableBlocks {
    final Map<Block,Block> BLOCK_CONVERSION =
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>(Blocks.BUDDING_AMETHYST,ModBlocks.BUDDING_ECHO),
                    new AbstractMap.SimpleEntry<>(Blocks.AMETHYST_BLOCK,ModBlocks.ECHO_BLOCK),
                    new AbstractMap.SimpleEntry<>(Blocks.OAK_SAPLING,ModBlocks.SCULK_WOOD_SAPLING)
            );

    final Map<Block,Block> PLANT_CONVERSION =
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>(Blocks.OAK_SAPLING,ModBlocks.SCULK_WOOD_SAPLING)
            );
}
