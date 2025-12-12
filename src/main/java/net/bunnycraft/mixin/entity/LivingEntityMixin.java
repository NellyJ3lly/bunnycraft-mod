package net.bunnycraft.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public Hand preferredHand;
    @Shadow @Final private DefaultedList<ItemStack> syncedArmorStacks;
    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

    @Unique
    public int getArmorAmountofMaterial(String material) {
        int ArmorAmount = 0;

        for (ItemStack stack : entity.getAllArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (!armorItem.getMaterial().getIdAsString().contains(material)) {continue;}
                ArmorAmount++;
            }
        }

        return ArmorAmount;
    };

    @Unique
    public float getSteelDamageReduction() {
        float DamageReduction = 0.0f;

        for (ItemStack stack : entity.getAllArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (!armorItem.getMaterial().getIdAsString().contains("steel")) {continue;}
                String SlotType = armorItem.getSlotType().asString();
                if (SlotType.contains("chest")) {
                    DamageReduction += 0.2f;
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
    }

    @Unique
    public float getArmadilloArmorDamageReduction() {
        float DamageReduction = 0.0f;

        for (ItemStack stack : entity.getAllArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (!armorItem.getMaterial().getIdAsString().contains("armadillo")) {continue;}
                String SlotType = armorItem.getSlotType().asString();
                if (SlotType.contains("chest")) {
                    DamageReduction += 0.4f;
                }
                if (SlotType.contains("legs")) {
                    DamageReduction += 0.3f;
                }
                if (SlotType.contains("feet") || SlotType.contains("head")) {
                    DamageReduction += 0.1f;
                }
            }
        }

        return DamageReduction;
    }

    @Unique
    public int getClimbingClaws() {
        int NumOfClaws = 0;

        if (entity.getMainHandStack().isOf(ModTools.CLIMBING_CLAW)) {
            NumOfClaws++;
        }

        if (entity.getOffHandStack().isOf(ModTools.CLIMBING_CLAW)) {
            NumOfClaws++;
        }

        return NumOfClaws;
    };

    @Unique
    public boolean isStackClimbingClawThatClimbs(Hand hand) {
        ItemStack stack = entity.getStackInHand(hand);

        boolean hasClimbingClaw = stack.isOf(ModTools.CLIMBING_CLAW);
        boolean isColliding = (Boolean.TRUE.equals(stack.get(ModComponents.HORIZONTAL_COLLISION))
                || Boolean.TRUE.equals(stack.get(ModComponents.CAN_CLIMB_ON_BLOCK)));

        return hasClimbingClaw && isColliding;
    }

    @Unique
    public boolean CanClimb() {
        if (this.getClimbingClaws() == 0) {return false;}

        return isStackClimbingClawThatClimbs(Hand.MAIN_HAND) || isStackClimbingClawThatClimbs(Hand.OFF_HAND);
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/LivingEntity;isClimbing()Z",
            at = @At("TAIL"))
    public boolean Bunnycraft$LetClimbClawClimb(boolean original) {
        if (entity.getBlockStateAtPos().isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS) && entity.getMovement().y <= 0) {
            return false;
        }

        if (!this.CanClimb() || entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return original;}

        BlockPos blockPos = entity.getBlockPos();
        entity.climbingPos = Optional.of(blockPos);
        return true;
    }

    @Unique
    public double setClimbSpeedConditions() {
        if(this.getClimbingClaws() == 1 && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return 1.5;}

        if(this.getClimbingClaws() == 2) {return 1.5;}

        if(this.getClimbingClaws() == 2 && entity.getBlockStateAtPos().isIn(BlockTags.CLIMBABLE)) {return 2;}

        return 1;
    }

    @ModifyReturnValue(
            method = "applyClimbingSpeed(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("TAIL"))
    public Vec3d Bunnycraft$ApplyClimbingClawSpeed(Vec3d originalMotion) {
        ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);

        if (entity.isClimbing() && stack.getComponents().contains(ModComponents.HORIZONTAL_COLLISION)) {
            double climbSpeed = setClimbSpeedConditions();

            if (entity.isSneaking() && originalMotion.y < 0.0) {
                climbSpeed = 0;
            }

            return new Vec3d(originalMotion.x, originalMotion.y * climbSpeed, originalMotion.z);
        }

        if (entity.getBlockStateAtPos().isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS)
                || entity.getWorld().getBlockState(
                        entity.getBlockPos().add(0,-1,0)).isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS)) {
            return entity.getMovement();
        }

        return originalMotion;
    }

    @ModifyReturnValue(
            method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
            at = @At("TAIL"))
    public float BlockDamageWithArmor(float original) {
        if (getArmorAmountofMaterial("armadillo") > 0 && entity.isSneaking() && entity.isOnGround()) {
//            if (getArmorAmountofMaterial("armadillo") == 4) {return 0.0f;}
            return original - (original * getArmadilloArmorDamageReduction());
        }
        if (getArmorAmountofMaterial("steel") > 0) {
            return original - (original * getSteelDamageReduction());
        }
        return original;
    }

    @Inject(
            method = "applyFluidMovingSpeed(DZLnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At("RETURN"),
            cancellable = true
            )
    public void Bunnycraft$DivingSuitWaterMovement(double gravity, boolean falling, Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        double d = motion.y;

        if (gravity != 0.0) {
            if (getArmorAmountofMaterial("diving") >= 1) {
                double SprintBonus = 0.0;

                if (entity.isSprinting()) {
                    SprintBonus = 0.02;
                }

                cir.cancel();

                if (falling && Math.abs(motion.y) < 0) {
                    // the max number should go up to 0.2
                    d = -(0.0 + ((double) (getArmorAmountofMaterial("diving")/4)/4));
                } else {
                    // 16 should go down to 1 with an entire set
                    d = (motion.y-gravity/((double) 16 /getArmorAmountofMaterial("diving")));
                }

                d += SprintBonus;
                cir.setReturnValue(new Vec3d(motion.x,d,motion.z));
            }
            if (getArmorAmountofMaterial("guardian") == 4) {
                cir.setReturnValue(new Vec3d(motion.x,d,motion.z));
            }
        }
    }
}
