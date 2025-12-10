package net.bunnycraft.mixin;

import net.bunnycraft.item.ModItems;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Unique
    private static boolean hasAmethystBook = false;

    /**
     * @author
     * @reason
     */
    @Inject(
            method = "getEnchantmentsComponentType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/component/ComponentType;",
            at = @At("HEAD"),
            cancellable = true)
    private static void getEnchantmentsComponentType(ItemStack stack, CallbackInfoReturnable<ComponentType<ItemEnchantmentsComponent>> cir) {
        if (stack.isOf(ModItems.ENCHANTED_AMETHYST_BOOK)) {
            cir.setReturnValue(DataComponentTypes.STORED_ENCHANTMENTS);
        }
    }

    // this checks if the item stack put in is an amethyst book
    @Inject(
            method = "getPossibleEntries(ILnet/minecraft/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;",
            at = @At("HEAD")
    )
    private static void checkForAmethystBook(int level, ItemStack stack, Stream<RegistryEntry<Enchantment>> possibleEnchantments, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        hasAmethystBook = stack.isOf(ModItems.AMETHYST_BOOK);
    }

    // this tells the enchanting table it's chill with amethyst books
    @ModifyVariable(method = "getPossibleEntries(ILnet/minecraft/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;",
            at = @At("STORE"),
            ordinal = 0)
    private static boolean changeValueIfAmethystBook(boolean original) {
        if (!hasAmethystBook) {
            return original;
        } else {
            return hasAmethystBook;
        }
    }
}
