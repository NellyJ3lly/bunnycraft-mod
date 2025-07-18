package net.bunnycraft.item.custom;

import com.mojang.datafixers.types.templates.Check;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClimbingClawItem extends Item {
    public ClimbingClawItem(Settings settings) {
        super(settings);
    }

    public boolean CanClimb(HitResult hit,LivingEntity livingentity) {
        boolean isClimbable = livingentity.getWorld().getBlockState(BlockPos.ofFloored(hit.getPos())).isIn(BlockTags.CLIMBABLE);
        boolean hitIsBlockNotClimb = hit.getType() == HitResult.Type.BLOCK && !isClimbable;

        BlockPos blockposatfeet = livingentity.getBlockPos();
        BlockPos lookingatblockpos = BlockPos.ofFloored(hit.getPos().add(0,0,0));

        BlockPos checkthisblock = new BlockPos(lookingatblockpos.getX(),blockposatfeet.getY(),lookingatblockpos.getZ());
        BlockPos blockabove = checkthisblock.add(0,1,0);

        boolean blockNotAir = !(livingentity.getWorld().getBlockState(checkthisblock).isAir());
        boolean aboveBlockAir = (livingentity.getWorld().getBlockState(blockabove).isAir());

        return (hitIsBlockNotClimb || (blockNotAir && aboveBlockAir) || !aboveBlockAir);
    }

    BlockPos startPos;

    public boolean CheckItemStack(ItemStack stack, LivingEntity livingentity,Hand hand) {
        return stack.isOf(ModTools.CLIMBING_CLAW) && livingentity.getStackInHand(hand) == stack;
    }

    public void DamageStack(LivingEntity livingentity, Hand hand,EquipmentSlot equipmentSlot) {
        ItemStack stack = livingentity.getStackInHand(hand);
        if (CheckItemStack(stack,livingentity,hand)) {
            stack.damage(1,livingentity,equipmentSlot);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingentity) {
            if (!CheckItemStack(stack,livingentity,Hand.MAIN_HAND) && !CheckItemStack(stack,livingentity,Hand.OFF_HAND)) {return;}

            if (startPos == null) {startPos = BlockPos.ofFloored(entity.getPos());}

            HitResult hit = livingentity.raycast(1,0,false);

            stack.set(ModComponents.CAN_CLIMB_ON_BLOCK,CanClimb(hit,livingentity));
            boolean OnClimbableBlock = livingentity.getWorld().getBlockState(livingentity.getBlockPos()).isIn(BlockTags.CLIMBABLE);
            if (!livingentity.isClimbing() || OnClimbableBlock) {return;}

            BlockPos currentPos = livingentity.getBlockPos();
            double YDiff = Math.abs(currentPos.getY() - startPos.getY());

            if ((YDiff < 2)) {return;}

            startPos = currentPos;

            DamageStack(livingentity,Hand.MAIN_HAND,EquipmentSlot.MAINHAND);
            DamageStack(livingentity,Hand.OFF_HAND,EquipmentSlot.OFFHAND);
        }
    }
}
