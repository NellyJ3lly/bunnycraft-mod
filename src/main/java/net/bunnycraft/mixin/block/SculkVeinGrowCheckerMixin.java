package net.bunnycraft.mixin.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(targets = "net.minecraft.block.SculkVeinBlock$SculkVeinGrowChecker")
class SculkVeinGrowCheckerMixin extends LichenGrower.LichenGrowChecker {

    public SculkVeinGrowCheckerMixin(MultifaceGrowthBlock lichen) {
        super(lichen);
    }

    @Unique
    List<BlockPos> directions = List.of(
      new BlockPos(1,0,0),
            new BlockPos(-1,0,0),
            new BlockPos(0,0,-1)
    );

//    public BlockState CheckForConversion(BlockView world,BlockPos pos) {
//        directions.forEach(blockpos ->{
//
//
//            world.getBlockState()
//        });
//    };

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean canGrow(BlockView world, BlockPos pos, BlockPos growPos, Direction direction, BlockState state) {
            BlockState blockState = world.getBlockState(growPos.offset(direction));
            if (!blockState.isOf(Blocks.SCULK) && !blockState.isOf(Blocks.SCULK_CATALYST) && !blockState.isOf(Blocks.MOVING_PISTON)) {
                if (pos.getManhattanDistance(growPos) == 2) {
                    BlockPos blockPos = pos.offset(direction.getOpposite());

                    if (world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction)) {
                        return false;
                    }
                }

                FluidState fluidState = state.getFluidState();
                if (!fluidState.isEmpty() && !fluidState.isOf(Fluids.WATER)) {
                    return false;
                } else if (state.isIn(BlockTags.FIRE)) {
                    return false;
                } else {
                    return state.isReplaceable() || super.canGrow(world, pos, growPos, direction, state);
                }
            } else {
                return false;
            }
    }
}
