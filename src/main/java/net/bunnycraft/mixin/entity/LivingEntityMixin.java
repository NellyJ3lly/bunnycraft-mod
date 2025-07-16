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
    public boolean facingNorthOrSouth() {
        return entity.getFacing().equals(Direction.NORTH) || entity.getFacing().equals(Direction.SOUTH);
    }

    @Unique
    public boolean facingEastOrWest() {
        return entity.getFacing().equals(Direction.EAST) || entity.getFacing().equals(Direction.WEST);
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

        // probably messy so we may change this later
        boolean or = isStackClimbingClawThatClimbs(Hand.MAIN_HAND) || isStackClimbingClawThatClimbs(Hand.OFF_HAND);
        boolean and = isStackClimbingClawThatClimbs(Hand.MAIN_HAND) && isStackClimbingClawThatClimbs(Hand.OFF_HAND);

        return or || and;
    }

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"),
            cancellable = true)
    public void ClimbClawFunctionalityBunnycraft(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = entity.getBlockPos();

        if ((this.hasOneOrBothClaws() && entity.horizontalCollision && !this.onClimbableBlock())) {
            if (this.CanClimb()) {
               entity.climbingPos = Optional.of(blockPos);
               cir.setReturnValue(true);
           }
        }
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;applyClimbingSpeed(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"))
    public Vec3d ClimbClawSpeed(Vec3d original) {
        if (!this.hasOneOrBothClaws() || !this.CanClimb() || entity.isOnGround()) {return original;}

        Vec3d motion = original;
        double climbspeed = 1;
        double x = motion.x;
        double z = motion.z;
        double y = motion.y;

        if (motion.y < 0.0 && entity.isSneaking() && !entity.isOnGround() && !this.onClimbableBlock()) {
            climbspeed = this.hasBothClimbingClaws() ? 0.25 : 0.5;
        }

        if (entity.isClimbing()) {
            // probably a better way to do this, but it's fine
            // increases climbing speed based on whether you have both claws and/or on a block normally climbable
            if((this.hasBothClimbingClaws() && !this.onClimbableBlock())) {
                climbspeed = 1.3;
            }

            if((this.hasClimbingClaw() && this.onClimbableBlock())) {
                climbspeed = 1.3;
            }


            if((this.hasBothClimbingClaws() && this.onClimbableBlock())) {
                climbspeed = 1.5;
            }
        }

        motion = new Vec3d(x, y * climbspeed, z);

        return motion;
    }

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
            at = @At("RETURN"),
            cancellable = true)
    public void DamageChanges(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        amount = cir.getReturnValue();
        cir.setReturnValue(amount);
    }

//    public void swimUpward(TagKey<Fluid> fluid) {
//        entity.setVelocity(entity.getVelocity().add(0.0, 1F, 0.0));
//    }
}
