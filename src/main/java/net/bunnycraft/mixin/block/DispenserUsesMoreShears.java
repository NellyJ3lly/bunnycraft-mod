package net.bunnycraft.mixin.block;

import net.bunnycraft.item.ModTools;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserUsesMoreShears {

    @Inject(
            method = "Lnet/minecraft/block/dispenser/DispenserBehavior;registerDefaults()V",
            at = @At(value = "HEAD")
    )
    private static void BunnycraftAddSteelShearsToDispenser(CallbackInfo ci) {
        DispenserBlock.registerBehavior(ModTools.STEEL_SHEARS, new ShearsDispenserBehavior());
    }
}
