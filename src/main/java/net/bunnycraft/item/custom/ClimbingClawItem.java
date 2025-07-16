package net.bunnycraft.item.custom;

import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
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

    BlockPos startPos;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.isOf(ModTools.CLIMBING_CLAW) || !selected) {return;}

        if (entity instanceof LivingEntity livingentity) {
            if (startPos == null) {startPos = entity.getBlockPos();}
            HitResult hit = entity.raycast(1,0,false);
            stack.set(ModComponents.CAN_CLIMB_ON_BLOCK,CanClimb(hit,livingentity));

            if (!livingentity.isClimbing()) {return;}

            BlockPos currentPos = entity.getBlockPos();

            double YDiff = Math.abs(currentPos.getY() - startPos.getY());

            if (YDiff < 3) {return;}
            stack.damage(1,livingentity,EquipmentSlot.MAINHAND);

            startPos = null;
        }
    }
}
