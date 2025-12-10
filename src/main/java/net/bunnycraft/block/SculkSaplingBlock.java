package net.bunnycraft.block;

import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SculkSaplingBlock extends SaplingBlock implements SpreadableBlock {
    private final Block blockToPlaceOn;


    public SculkSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        this.blockToPlaceOn = Blocks.SCULK;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor,BlockView world, BlockPos pos) {
        return floor.isOf(this.blockToPlaceOn);
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        super.generate(world, pos, state, random);
    }
}
