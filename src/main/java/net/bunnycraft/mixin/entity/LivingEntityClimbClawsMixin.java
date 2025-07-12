package net.bunnycraft.mixin.entity;

import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityClimbClawsMixin {
    @Shadow public abstract Iterable<ItemStack> getHandItems();

    @Shadow public abstract boolean isClimbing();

    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Unique
    public boolean hasClimbingClaw() {
        return entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAWS) || entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAWS);
    }

    @Unique
    public boolean hasBothClimbingClaws() {
        return entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAWS) && entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAWS);
    }

    @Unique
    public boolean hasOneOrBothClaws() {
        return this.hasClimbingClaw() || this.hasBothClimbingClaws();
    }

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"),
            cancellable = true)
    public void ClimbClawFunctionalityBunnycraft(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = entity.getBlockPos();

        if ((this.hasOneOrBothClaws()) && entity.horizontalCollision && !entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {
            entity.climbingPos = Optional.of(blockPos);
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;applyClimbingSpeed(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"),
            cancellable = true)
    public void ClimbClawSpeed(CallbackInfoReturnable<Vec3d> cir) {
        Vec3d motion = cir.getReturnValue();
        double climbspeed = 1;

        if (entity.isClimbing()) {
            if((this.hasBothClimbingClaws() && !entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) || (this.hasClimbingClaw() && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE))) {
                climbspeed = 1.5;
            }
            if (this.hasBothClimbingClaws() && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {
                climbspeed = 1.75;
            }
        }

        motion = new Vec3d(motion.x, motion.y * climbspeed, motion.z);

        cir.setReturnValue(motion);
    }

//    public void swimUpward(TagKey<Fluid> fluid) {
//        entity.setVelocity(entity.getVelocity().add(0.0, 1F, 0.0));
//    }
}
