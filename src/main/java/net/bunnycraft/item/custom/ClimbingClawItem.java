package net.bunnycraft.item.custom;

import com.mojang.datafixers.types.templates.Check;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ClimbingClawItem extends Item {
    public ClimbingClawItem(Settings settings) {
        super(settings);
    }

    private BlockPos startPos;
    private final float RayDistance = 0.5f;

    public boolean BlockAlreadyClimbable(LivingEntity livingentity) {
        return livingentity.getWorld().getBlockState(livingentity.getBlockPos()).isIn(BlockTags.CLIMBABLE);
    };

    public boolean CanClimbifBlockExistsBackwards(LivingEntity livingentity) {
        HitResult hit = livingentity.raycast(-RayDistance,0,false);

        return hit.getType() == HitResult.Type.BLOCK && !BlockAlreadyClimbable(livingentity) || CheckBlocksIfCanClimb(livingentity,hit);
    };

    public boolean CheckBlocksIfCanClimb(LivingEntity livingentity, HitResult hit) {
        BlockPos blockposatfeet = livingentity.getBlockPos();
        BlockPos lookingatblockpos = BlockPos.ofFloored(hit.getPos().add(0,0,0));

        BlockPos checkthisblock = new BlockPos(lookingatblockpos.getX(),blockposatfeet.getY(),lookingatblockpos.getZ());
        BlockPos blockabove = checkthisblock.add(0,1,0);

        boolean blockNotAir = !(livingentity.getWorld().getBlockState(checkthisblock).isAir());
        boolean aboveBlockAir = (livingentity.getWorld().getBlockState(blockabove).isAir());

        return blockNotAir || !aboveBlockAir;
    }

    public boolean CanClimb(LivingEntity livingentity) {
        HitResult hit = livingentity.raycast(RayDistance,0,false);
        boolean hitIsBlockNotClimb = hit.getType() == HitResult.Type.BLOCK && !BlockAlreadyClimbable(livingentity);

        return hitIsBlockNotClimb || CheckBlocksIfCanClimb(livingentity,hit) || CanClimbifBlockExistsBackwards(livingentity);
    }

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

            stack.set(ModComponents.CAN_CLIMB_ON_BLOCK,CanClimb(livingentity));
            if (!livingentity.isClimbing() || BlockAlreadyClimbable(livingentity)) {return;}

            BlockPos currentPos = livingentity.getBlockPos();
            double YDiff = Math.abs(currentPos.getY() - startPos.getY());

            if ((YDiff < 2)) {return;}

            startPos = currentPos;

            DamageStack(livingentity,Hand.MAIN_HAND,EquipmentSlot.MAINHAND);
            DamageStack(livingentity,Hand.OFF_HAND,EquipmentSlot.OFFHAND);
        }
    }
}
