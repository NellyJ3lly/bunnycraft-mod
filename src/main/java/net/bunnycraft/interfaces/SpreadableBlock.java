package net.bunnycraft.interfaces;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

public interface SpreadableBlock {
    @Unique
    ArrayList<BlockPos> positionsToCheck = new ArrayList<>();

    @Unique
    private void SetupList() {
        positionsToCheck.add(new BlockPos(0,0,1));
        positionsToCheck.add(new BlockPos(0,0,-1));
        positionsToCheck.add(new BlockPos(1,0,0));
        positionsToCheck.add(new BlockPos(-1,0,0));
    }

    BlockPos offsetPos = new BlockPos(0,0,0);

    @Unique
    default void LoopPositions(WorldAccess world, BlockPos basePos) {
        SetupList();

        for (BlockPos pos : positionsToCheck) {
            BlockPos addedPos = basePos.add(pos);
            BlockState blockState = world.getBlockState(addedPos);

            if(blockState.isOf(Blocks.OAK_SAPLING)) {
                world.setBlockState(addedPos.add(0,-1,0),Blocks.SCULK.getDefaultState(),3);
                break;
            }
        }
    }
}
