package net.bunnycraft.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sun.jna.platform.win32.COM.TypeInfoUtil;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModArmors;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Unique
    AnvilScreenHandler screenHandler = (AnvilScreenHandler) (Object) this;

    @Unique
    private static boolean hasHelmet = false;
    @Unique
    private static boolean hasChestplate = false;
    @Unique
    private static double AmountOfPowerReduction = 1;

    @Inject(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",
            at = @At("HEAD")
    )
    private static void Bunnycraft$getAmethystArmor(int syncId, PlayerInventory playerInventory, CallbackInfo ci) {
        hasHelmet = playerInventory.player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModArmors.AMETHYST_HELMET);
        hasChestplate = playerInventory.player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModArmors.AMETHYST_CHESTPLATE);
    }

    @WrapOperation(
            method = "updateResult()V",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/screen/Property;set(I)V")
    )
    private static void changeLevelCost(Property instance, int i, Operation<Void> original) {
        if (!hasHelmet && !hasChestplate) {
            AmountOfPowerReduction = 1;
        }

        if (hasHelmet && !hasChestplate) {
            AmountOfPowerReduction = 0.8;
        }

        if (hasChestplate && !hasHelmet) {
            AmountOfPowerReduction = 0.7;
        }

        if (hasHelmet && hasChestplate) {
            AmountOfPowerReduction = 0.5;
        }

        int discount = (int) (i * AmountOfPowerReduction);

        original.call(instance, discount);
    }

    @WrapOperation(
            method = "Lnet/minecraft/screen/AnvilScreenHandler;onTakeOutput(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V")
    )
    public void SculkBatteryCost(PlayerEntity instance, int levels, Operation<Void> original) {
        screenHandler.context.run(((world, pos) -> {
            if (!world.getBlockState(pos.add(-1,0,0)).isOf(Blocks.DIAMOND_BLOCK)) {
                original.call();
            }
        }));
    }
}
