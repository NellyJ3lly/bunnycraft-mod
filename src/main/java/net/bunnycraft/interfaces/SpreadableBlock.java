package net.bunnycraft.interfaces;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.Properties;

public interface SpreadableBlock {
    @Unique
    ArrayList<BlockPos> positionsToCheck = new ArrayList<>();

    // I may have to redo this code later
    // this was the easiest way I found that'd let me check for non full blocks around and convert them to sculk blocks

    @Unique
    default void SetupList() {
        positionsToCheck.add(new BlockPos(0,0,1));
        positionsToCheck.add(new BlockPos(0,0,-1));
        positionsToCheck.add(new BlockPos(1,0,0));
        positionsToCheck.add(new BlockPos(-1,0,0));
    }

    default void CheckForNonFullBlocks(WorldAccess world, BlockPos basePos) {
        for (BlockPos pos : positionsToCheck) {
            BlockPos addedPos = basePos.add(pos);
            BlockState blockState = world.getBlockState(addedPos);

            if(blockState.isIn(ModTags.Blocks.SCULK_REPLACEABLE_NON_FULL_BLOCK)) {
                BlockState belowBlock = world.getBlockState(addedPos.add(0,-1,0));

                if (belowBlock.isOf(Blocks.BUDDING_AMETHYST)) {
                    world.setBlockState(addedPos.add(0,-1,0), ModBlocks.BUDDING_ECHO.getDefaultState(),3);
                    break;
                } else if (belowBlock.isIn(BlockTags.SCULK_REPLACEABLE)) {
                    world.setBlockState(addedPos.add(0,-1,0),Blocks.SCULK.getDefaultState(),3);
                    break;
                }
            }
        }
    }

    default void PlaceSculkVeins(WorldAccess world, BlockPos basePos) {
        BlockState SculkVein = Blocks.SCULK_VEIN.getDefaultState().with(MultifaceGrowthBlock.getProperty(Direction.DOWN),true);

        for (BlockPos pos : positionsToCheck) {
            BlockPos addedPos = basePos.add(pos);
            BlockState blockState = world.getBlockState(addedPos);

            boolean spreadsSculk = (world.getBlockState(addedPos.add(0,-1,0)) instanceof SculkSpreadable);

            if(blockState.isAir() && !spreadsSculk) {
                world.setBlockState(addedPos,SculkVein,3);
            }
        }
    }
}
