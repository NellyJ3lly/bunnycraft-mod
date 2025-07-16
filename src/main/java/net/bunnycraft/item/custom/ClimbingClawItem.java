package net.bunnycraft.item.custom;

import com.mojang.datafixers.types.templates.Check;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

public class ClimbingClawItem extends Item {
    public ClimbingClawItem(Settings settings) {
        super(settings);
    }

    public boolean IsAirOrClimbable(World world, BlockPos blockpos) {
      return world.getBlockState(blockpos).isAir() || world.getBlockState(blockpos).isIn(BlockTags.CLIMBABLE);
    };

    public double BlockYChanged(Double OldY,Double NewY) {
        return OldY - NewY;
    }

    public boolean CanClimb(HitResult hit, LivingEntity livingentity) {
        return hit.getType() == HitResult.Type.BLOCK || livingentity.horizontalCollision;
    }

    public ItemStack GetClimbClawInHand(LivingEntity entity, Hand hand) {
        if (!entity.getStackInHand(hand).isOf(ModTools.CLIMBING_CLAW)) {return null;}
        return entity.getStackInHand(hand);
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

            if (startPos == null) {startPos = entity.getBlockPos();}

            HitResult hit = livingentity.raycast(1,0,false);

            stack.set(ModComponents.CAN_CLIMB_ON_BLOCK,CanClimb(hit,livingentity));

            if (Boolean.FALSE.equals(stack.get(ModComponents.CAN_CLIMB_ON_BLOCK)) || !livingentity.isClimbing() || livingentity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return;}

            BlockPos currentPos = livingentity.getBlockPos();

            double YDiff = Math.abs(currentPos.getY() - startPos.getY());

            if (YDiff < 3) {return;}

            DamageStack(livingentity,Hand.MAIN_HAND,EquipmentSlot.MAINHAND);
            DamageStack(livingentity,Hand.OFF_HAND,EquipmentSlot.OFFHAND);


            startPos = null;
        }
    }
}
