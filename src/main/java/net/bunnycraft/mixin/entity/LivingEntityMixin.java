package net.bunnycraft.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract Iterable<ItemStack> getHandItems();

    @Shadow public abstract boolean isClimbing();

    @Shadow public abstract boolean isSleeping();

    @Shadow public abstract boolean isHoldingOntoLadder();

    @Shadow public float forwardSpeed;
    @Shadow protected boolean jumping;

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition);

    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Unique
    public boolean onClimbableBlock() {
        return entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE);
    }

    @Unique
    public boolean hasClimbingClaw() {
        return (entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAW)
                || entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAW))
                && !this.hasBothClimbingClaws();
    }

    @Unique
    public boolean hasBothClimbingClaws() {
        return entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAW) && entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAW);
    }

    @Unique
    public boolean hasOneOrBothClaws() {
        return this.hasClimbingClaw() || this.hasBothClimbingClaws();
    }

    @Unique
    public boolean isStackClimbingClawThatClimbs(Hand hand) {
        ItemStack stack = entity.getStackInHand(hand);
        return stack.isOf(ModTools.CLIMBING_CLAW) && Boolean.TRUE.equals(stack.get(ModComponents.CAN_CLIMB_ON_BLOCK));
    }

    @Unique
    public boolean CanClimb() {
        if (!this.hasOneOrBothClaws()) {return false;}

        return isStackClimbingClawThatClimbs(Hand.MAIN_HAND) || isStackClimbingClawThatClimbs(Hand.OFF_HAND);
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"))
    public boolean ClimbClawFunctionalityBunnycraft(boolean original) {
        if (!this.CanClimb()) {return original;}

        BlockPos blockPos = entity.getBlockPos();
        entity.climbingPos = Optional.of(blockPos);
        return true;
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;applyClimbingSpeed(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"))
    public Vec3d ClimbClawSpeed(Vec3d original) {
        if (entity.isClimbing() && entity.horizontalCollision) {
            double climbspeed = 1;
            if(this.hasClimbingClaw()) {climbspeed = 1.3;}
            if(this.hasBothClimbingClaws()) {climbspeed = 1.5;}

            return new Vec3d(original.x, original.y * climbspeed, original.z);
        } else if (original.y < 0.0 && entity.isSneaking() && this.CanClimb()) {
            return new Vec3d(original.x, -0.25, original.z);
        }

        return original;
    }

//    @Inject(
//            method = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
//            at = @At("RETURN"),
//            cancellable = true)
//    public void DamageChanges(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
//        amount = cir.getReturnValue();
//        cir.setReturnValue(amount);
//    }

//    public void swimUpward(TagKey<Fluid> fluid) {
//        entity.setVelocity(entity.getVelocity().add(0.0, 1F, 0.0));
//    }
}
