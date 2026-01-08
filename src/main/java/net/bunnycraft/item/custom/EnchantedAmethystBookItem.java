package net.bunnycraft.item.custom;

import net.bunnycraft.item.ModItems;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;

public class EnchantedAmethystBookItem extends Item {
    public EnchantedAmethystBookItem(Item.Settings settings) {
        super(settings);
    }

    public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
        ItemStack itemStack = new ItemStack(ModItems.ENCHANTED_AMETHYST_BOOK);
        itemStack.addEnchantment(info.enchantment, info.level);
        return itemStack;
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
