package net.bunnycraft.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.bunnycraft.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {
    @Inject(
            method = "Lnet/minecraft/screen/GrindstoneScreenHandler;grind(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true)
    public void EnchantedAmethystBookToBook(ItemStack item, CallbackInfoReturnable<ItemStack> cir) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = EnchantmentHelper.apply(item, (components) -> components.remove((enchantment) -> !enchantment.isIn(EnchantmentTags.CURSE)));
        if (item.isOf(ModItems.ENCHANTED_AMETHYST_BOOK) && itemEnchantmentsComponent.isEmpty()) {
            item = item.withItem(ModItems.AMETHYST_BOOK);
            int i = 0;

            for(int j = 0; j < itemEnchantmentsComponent.getSize(); ++j) {
                i = AnvilScreenHandler.getNextCost(i);
            }

            item.set(DataComponentTypes.REPAIR_COST, i);

            cir.setReturnValue(item);
        }
    }
}
