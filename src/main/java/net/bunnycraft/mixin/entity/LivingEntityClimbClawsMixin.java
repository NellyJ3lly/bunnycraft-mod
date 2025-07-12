package net.bunnycraft.mixin.entity;

import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public class LivingEntityClimbClawsMixin {
    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"),
            cancellable = true)
    public void ClimbClawFunctionalityBunnycraft(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = entity.getBlockPos();

        if ((entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAWS) || entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAWS))
                && entity.horizontalCollision && !entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {
            entity.climbingPos = Optional.of(blockPos);
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "Lnet/minecraft/entity/LivingEntity;applyMovementInput(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"),
            cancellable = true)
    public void ClimbClawSpeed(CallbackInfoReturnable<Vec3d> cir) {
        Vec3d vec3d = entity.getVelocity();
        if (entity.isClimbing()
                && (entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAWS) || entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAWS))) {
            double climbspeed = 0.2;
            if (entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAWS) && entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAWS) ) {
                climbspeed *= 1.5;
            };

            if (entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {
                climbspeed *= 1.25;
            };

            vec3d = new Vec3d(vec3d.x, climbspeed, vec3d.z);
            cir.setReturnValue(vec3d);
        };
    }

//    public void swimUpward(TagKey<Fluid> fluid) {
//        entity.setVelocity(entity.getVelocity().add(0.0, 1F, 0.0));
//    }
}
