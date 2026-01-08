package net.bunnycraft.mixin.entity;

import net.bunnycraft.item.ModArmors;
import net.bunnycraft.item.armor.EchoArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Shadow
    private int amount;

    @Unique
    private int getEchoArmor(PlayerEntity playerEntity) {
        int EchoArmorAmount = 0;

        for (ItemStack stack : playerEntity.getArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().getIdAsString().equals("bunnycraft:echo")) {
                    EchoArmorAmount++;
                }
            }
        }

        return EchoArmorAmount;
    }

    @Unique
    private int AmountChange(PlayerEntity playerEntity) {
        int amountToAdd = this.amount;
        double percentageIncrease = 0;

        if (playerEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModArmors.ECHO_HELMET)) {
            percentageIncrease += 0.4;
        }
        if (playerEntity.getEquippedStack(EquipmentSlot.FEET).isOf(ModArmors.ECHO_BOOTS)) {
            percentageIncrease += 0.3;
        }
        if (playerEntity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModArmors.ECHO_CHESTPLATE)) {
            percentageIncrease += 0.7;
        }
        if (playerEntity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModArmors.ECHO_LEGGINGS)) {
            percentageIncrease += 0.6;
        }

        System.out.println(amountToAdd + (amountToAdd * percentageIncrease));

        amountToAdd = Math.toIntExact(Math.round((amountToAdd + (amountToAdd * percentageIncrease))));

        return amountToAdd;
    }

    @Inject(
            method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;experiencePickUpDelay:I", ordinal = 0, opcode = Opcodes.PUTFIELD)
    )
    public void Bunnycraft$ModifyExperienceAmountForEchoArmor(PlayerEntity player, CallbackInfo ci) {
        this.amount = AmountChange(player);
    }
}
