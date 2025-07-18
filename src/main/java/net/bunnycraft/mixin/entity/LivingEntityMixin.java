package net.bunnycraft.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Unique
    public boolean hasClimbingClaw() {
        return (entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAW) || entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAW))
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

    @Unique
    public double setClimbSpeedConditions() {
        if(this.hasClimbingClaw() && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return 1.5;}

        if(this.hasBothClimbingClaws()) {return 1.5;}

        if(this.hasBothClimbingClaws() && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return 2;}

        return 1;
    }

    @Unique
    public int getArmorAmountofMaterial(String material) {
        AtomicInteger ArmorAmount = new AtomicInteger();
        entity.getAllArmorItems().forEach(stack -> {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().getIdAsString().contains(material)) {
                    ArmorAmount.addAndGet(1);
                }
            }
        });
        return ArmorAmount.get();
    };

    @Unique
    public float getSteelDamageReduction() {
        float DamageReduction = 0.0f;

        for (ItemStack stack : entity.getAllArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                String SlotType = armorItem.getSlotType().asString();
                if (SlotType.contains("chest")) {
                    DamageReduction += 0.1f;
                }
                if (SlotType.contains("legs")) {
                    DamageReduction += 0.05f;
                }
                if (SlotType.contains("feet") || SlotType.contains("head")) {
                    DamageReduction += 0.025f;
                }
            }
        }

        return DamageReduction;
    };

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"))
    public boolean ClimbClawFunctionalityBunnycraft(boolean original) {
        if (!this.CanClimb() || entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return original;}

        BlockPos blockPos = entity.getBlockPos();
        entity.climbingPos = Optional.of(blockPos);
        return true;
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;applyClimbingSpeed(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"))
    public Vec3d ClimbClawSpeed(Vec3d original) {
        if (entity.isClimbing() && entity.horizontalCollision) {
            double climbSpeed = setClimbSpeedConditions();

            return new Vec3d(original.x, original.y * climbSpeed, original.z);
        }

        return original;
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
            at = @At("TAIL"))
    public float BlockDamageWithArmor(float original) {
        if (getArmorAmountofMaterial("armadillo") == 4 && entity.isSneaking()) {
            return 0.0f;
        }
        if (getArmorAmountofMaterial("steel") > 0) {
            return original - (original * getSteelDamageReduction());
        }
        return original;
    }
//
//    public void swimUpward(TagKey<Fluid> fluid) {
//        entity.setVelocity(entity.getVelocity().add(0.0, 0.0F, 0.0));
//    }
}
