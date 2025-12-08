package net.bunnycraft.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.bunnycraft.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Stream;

@Mixin(net.minecraft.screen.EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {

    @Shadow @Final private Inventory inventory;

    @WrapOperation(
            method = "method_17410(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/item/ItemStack;II)Ljava/util/List;")
    )
    public List<EnchantmentLevelEntry> Bunnycraft$convertAmethystBookToEnchantedBook(EnchantmentScreenHandler instance, DynamicRegistryManager registryManager, ItemStack stack, int slot, int level, Operation<List<EnchantmentLevelEntry>> original) {
        if (stack.isOf(ModItems.AMETHYST_BOOK)) {
            this.inventory.setStack(0,ModItems.ENCHANTED_AMETHYST_BOOK.getDefaultStack());

            for(EnchantmentLevelEntry enchantmentLevelEntry : original.call(instance, registryManager, stack, slot, level)) {
                this.inventory.getStack(0).addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
            }
        }
        return original.call(instance, registryManager, stack, slot, level);
    }
}
