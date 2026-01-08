package net.bunnycraft.block;

import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
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
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (stack.isOf(Items.BONE_MEAL)) {
//            return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
//        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        super.generate(world, pos, state, random);
    }
}
