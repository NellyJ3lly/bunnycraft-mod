package net.bunnycraft.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SculkSaplingBlock extends SaplingBlock {
    private final Block blockToPlaceOn;


    public SculkSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        this.blockToPlaceOn = Blocks.SCULK;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor,BlockView world, BlockPos pos) {
        return floor.isOf(this.blockToPlaceOn);
    }
}
