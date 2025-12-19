package net.bunnycraft.mixin.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(net.minecraft.screen.EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {

    @Unique
    EnchantmentScreenHandler screenHandler = (EnchantmentScreenHandler) (Object) this;

    @Shadow @Final private Inventory inventory;

    @Shadow @Final public ScreenHandlerContext context;

    @Shadow @Final public Random random;

    @Shadow @Final public int[] enchantmentPower;

    @Unique
    private static boolean hasHelmet = false;
    @Unique
    private static boolean hasChestplate = false;
    @Unique
    double AmountOfPowerReduction = 1;

    protected EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

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

    @WrapOperation(
            method = "method_17410(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V")
    )
    public void Bunnycraft$RemoveCosts(PlayerEntity instance, ItemStack enchantedItem, int experienceLevels, Operation<Void> original) {
        boolean doNotCostForEnchant = false;

        screenHandler.context.run(((world, pos) -> {
            if (!world.getBlockState(pos.add(-1,0,0)).isOf(Blocks.DIAMOND_BLOCK)) {
                original.call(instance, enchantedItem, experienceLevels);
                instance.enchantmentTableSeed = instance.getRandom().nextInt();
            }
        }));
    }

    @WrapOperation(
            method = "method_17410(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/item/ItemStack;II)Ljava/util/List;")
    )
    public List<EnchantmentLevelEntry> Bunnycraft$usePreviousLevelCost(EnchantmentScreenHandler instance, DynamicRegistryManager registryManager, ItemStack stack, int slot, int level, Operation<List<EnchantmentLevelEntry>> original) {
        if (AmountOfPowerReduction < 1) {
            // when the level reduction happens it also affects this and gives the wrong enchantment
            // so I have to go and reverse it
            int previousLevelBeforeReduction = (int) (level/AmountOfPowerReduction);
            return original.call(instance, registryManager, stack, slot, previousLevelBeforeReduction);
        }
        return original.call(instance, registryManager, stack, slot, level);
    }

    @Inject(
            method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",
            at = @At("HEAD")
    )
    private static void Bunnycraft$getAmethystArmor(int syncId, PlayerInventory playerInventory, CallbackInfo ci) {
        hasHelmet = playerInventory.player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModArmors.AMETHYST_HELMET);
        hasChestplate = playerInventory.player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModArmors.AMETHYST_CHESTPLATE);
    }

    @WrapOperation(
            method = "method_17411",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/registry/DynamicRegistryManager;Lnet/minecraft/item/ItemStack;II)Ljava/util/List;")
    )
    public List<EnchantmentLevelEntry> Bunnycraft$ModifyEnchantmentCost(EnchantmentScreenHandler instance, DynamicRegistryManager registryManager, ItemStack stack, int slot, int level, Operation<List<EnchantmentLevelEntry>> original) {
        if (hasHelmet) {
            AmountOfPowerReduction = 0.8;
        }

        if (hasChestplate) {
            AmountOfPowerReduction = 0.7;
        }

        if (hasHelmet && hasChestplate) {
            AmountOfPowerReduction = 0.5;
        }

        this.enchantmentPower[slot] = (int) (this.enchantmentPower[slot] * AmountOfPowerReduction);

        return original.call(instance, registryManager, stack, slot, level);
    }
}
