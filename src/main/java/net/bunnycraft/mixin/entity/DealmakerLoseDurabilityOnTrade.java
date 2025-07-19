package net.bunnycraft.mixin.entity;


import net.bunnycraft.item.ModArmors;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.TradeOutputSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOutputSlot.class)
public class DealmakerLoseDurabilityOnTrade extends Slot {
    public DealmakerLoseDurabilityOnTrade(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(
            method = "Lnet/minecraft/screen/slot/TradeOutputSlot;onTakeItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD")
    )
    public void DealmakerLoseDurability(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if(player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModArmors.DEALMAKER)) {
            ItemStack Dealmaker = player.getEquippedStack(EquipmentSlot.HEAD);
            Dealmaker.damage(1,player,EquipmentSlot.HEAD);
        }
    }
}
