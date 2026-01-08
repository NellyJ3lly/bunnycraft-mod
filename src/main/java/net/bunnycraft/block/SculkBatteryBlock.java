package net.bunnycraft.block;

import com.mojang.serialization.MapCodec;
import net.bunnycraft.block.entity.SculkBatteryBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SculkBatteryBlock extends BlockWithEntity implements BlockEntityProvider {
    protected SculkBatteryBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<SculkBatteryBlock> CODEC = SculkBatteryBlock.createCodec(SculkBatteryBlock::new);

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SculkBatteryBlockEntity(pos,state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SculkBatteryBlockEntity); {
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof SculkBatteryBlockEntity sculkBatteryBlockEntity) {
            if (sculkBatteryBlockEntity.getExperienceLevels() >= sculkBatteryBlockEntity.getMaxLevel()) {
                return ItemActionResult.FAIL;
            }

            if (stack.isOf(Items.EXPERIENCE_BOTTLE)) {
                int i = 3 + world.random.nextInt(5) + world.random.nextInt(5);
                sculkBatteryBlockEntity.addExperience(i);
                stack.decrementUnlessCreative(1,player);

                if (!player.isInCreativeMode()) {
                    giveItem(player,Hand.MAIN_HAND,Items.EXPERIENCE_BOTTLE.getDefaultStack());
                }

                giveItem(player,Hand.MAIN_HAND,Items.GLASS_BOTTLE.getDefaultStack());

                return ItemActionResult.SUCCESS ;
            }

            if (stack.isOf(Items.GLASS_BOTTLE)) {
                int i = 3 + world.random.nextInt(5) + world.random.nextInt(5);
                sculkBatteryBlockEntity.addExperience(-i);
                stack.decrementUnlessCreative(1,player);
                giveItem(player,Hand.MAIN_HAND,Items.EXPERIENCE_BOTTLE.getDefaultStack());

                return ItemActionResult.SUCCESS ;
            }

//            if (stack.isOf(Items.ECHO_SHARD)) {
//                int i = 3 + world.random.nextInt(5) + world.random.nextInt(5);
//                sculkBatteryBlockEntity.addExperience(i);
//                stack.decrementUnlessCreative(1,player);
//                return ItemActionResult.SUCCESS ;
//            }
//            if (stack.isOf(Items.NETHER_STAR)) {
//                sculkBatteryBlockEntity.addExperience(1000);
//                stack.decrementUnlessCreative(1,player);
//                return ItemActionResult.SUCCESS ;
//            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    public void giveItem(PlayerEntity user, Hand hand,ItemStack stack) {
        user.getStackInHand(hand).decrement(1);

        if (user.getInventory().getEmptySlot() == -1) {
            user.dropItem(stack,true);
        } else {
            user.giveItemStack(stack);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof SculkBatteryBlockEntity sculkBatteryBlockEntity) {
//            if (sculkBatteryBlockEntity.getTotalExperience() > 0) {
//                if (world instanceof ServerWorld serverWorld) {
//                    dropExperience(serverWorld,pos,sculkBatteryBlockEntity.getTotalExperience());
//                }
//            }
        }

        return super.onBreak(world, pos, state, player);
    }
}
