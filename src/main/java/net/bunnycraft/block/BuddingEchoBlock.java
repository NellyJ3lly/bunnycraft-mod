package net.bunnycraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import static net.minecraft.block.AbstractBlock.createCodec;


public class BuddingEchoBlock extends Block {
    public static final MapCodec<BuddingEchoBlock> CODEC = createCodec(BuddingEchoBlock::new);

    public static final int GROW_CHANCE = 0;
    private static final Direction[] DIRECTIONS = Direction.values();

    public MapCodec<BuddingEchoBlock> getCodec() {
        return CODEC;
    }

    public BuddingEchoBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (canGrowIn(blockState)) {
            block = ModBlocks.SMALL_ECHO_BUD;
        } else if ((blockState.isOf(ModBlocks.SMALL_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) || state.isOf(Blocks.SCULK_VEIN)) {
            block = ModBlocks.MEDIUM_ECHO_BUD;
        } else if (blockState.isOf(ModBlocks.MEDIUM_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
            block = ModBlocks.LARGE_ECHO_BUD;
        } else if (blockState.isOf(ModBlocks.LARGE_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
            block = ModBlocks.ECHO_CLUSTER;
        }

        if (block != null) {
            BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(AmethystClusterBlock.FACING, direction)).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
            world.setBlockState(blockPos, blockState2);
        }

    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}
