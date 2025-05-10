package net.bunnycraft.mixin;



import net.bunnycraft.item.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//this mixin is used to detect when a player drops the spear slot reserver, and deletes it

@Mixin (PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject (
            method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            at = @At( value = "HEAD"),
            cancellable = true
    )

    public void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.isOf(ModItems.EMPTY_SPEAR_SLOT)) {
            cir.setReturnValue(null);
        }
    }
}
