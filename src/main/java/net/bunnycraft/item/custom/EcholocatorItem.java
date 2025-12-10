package net.bunnycraft.item.custom;

import net.bunnycraft.component.ModComponents;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.PositionSource;

import java.util.List;
import java.util.Optional;

public class EcholocatorItem extends ToolItem {
    public EcholocatorItem(Settings settings) {
        super(ToolMaterials.NETHERITE,settings);
    }

    int HorizontalRange = 4;
    int VerticalRange = 4;

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(ModComponents.ECHO_FUEL)) {
            tooltip.add(Text.literal("Echo Fuel: " + Math.round(getEchoFuel(stack))));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public float getMaxEchoFuel() {
        return 16;
    }

    public Float getEchoFuel(ItemStack stack) {
        return stack.get(ModComponents.ECHO_FUEL);
    }

    public void addEchoFuel(ItemStack stack,float value) {
        stack.set(ModComponents.ECHO_FUEL,Math.clamp(getEchoFuel(stack)+value,0,this.getMaxEchoFuel()));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return this.getEchoFuel(stack) < getMaxEchoFuel();
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return MathHelper.clamp(13-Math.round(13.0F - this.getEchoFuel(stack) * 13.0F / this.getMaxEchoFuel()), 0, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(0,0,153);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (this.getEchoFuel(stack) >= 16) {
            return false;
        }


        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            if (otherStack.isOf(Items.ECHO_SHARD)) {
                otherStack.decrement(1);

                player.playSound(SoundEvents.BLOCK_SCULK_PLACE, 1F,1F);

                this.addEchoFuel(stack,1);
                return true;
            }
        }
        return false;
    }

    public boolean FindBlock(ServerWorld world, PlayerEntity entity, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(ModTags.Blocks.BLOCKS_CAN_ECHOLOCATE)) {
            PositionSource positionSource = new EntityPositionSource(entity,1);

            world.playSound(
                    null,
                    blockPos,
                    SoundEvents.BLOCK_SCULK_SENSOR_CLICKING,
                    SoundCategory.BLOCKS,
                    0.5f,
                    1f
            );

            world.spawnParticles(
                    new VibrationParticleEffect(positionSource,10),
                    blockPos.toCenterPos().x,
                    blockPos.toCenterPos().y,
                    blockPos.toCenterPos().z,
                    3,0,0,0,1.5
            );

            return true;
        }

        return false;
    }

    public int IterateBlocks(World world,PlayerEntity user) {
        Iterable<BlockPos> blocks = BlockPos.iterateOutwards(user.getBlockPos(), HorizontalRange, VerticalRange, HorizontalRange);

        int blocksDetected = 0;

        for (BlockPos blockPos : blocks) {
            if(FindBlock((ServerWorld) world,user,blockPos)) {
                blocksDetected++;
            }
        }

        return blocksDetected;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (this.getEchoFuel(stack) == 0) {
                return TypedActionResult.pass(stack);
            }
            int blocksDetected = IterateBlocks(world,user);
            int ticksAdded = 0;

            if (blocksDetected > 0) {
                ticksAdded = (blocksDetected/4)*10;

                world.playSound(
                        null, user.getBlockPos(),
                        SoundEvents.BLOCK_SCULK_CHARGE,
                        SoundCategory.PLAYERS, 1f,1f
                );
            } else {
                world.playSound(
                        null, user.getBlockPos(),
                        SoundEvents.BLOCK_SCULK_SHRIEKER_BREAK,
                        SoundCategory.PLAYERS, 1f,1f
                );
            }

            if (!user.isInCreativeMode()) {
                this.addEchoFuel(stack,-1);

                if (this.getEchoFuel(stack) == 0) {
                    user.playSound(SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1f,1f);
                }
            }

            user.getItemCooldownManager().set(this,20 + ticksAdded);
            return TypedActionResult.success(stack,true);
        }

        return TypedActionResult.pass(stack);
    }
}

