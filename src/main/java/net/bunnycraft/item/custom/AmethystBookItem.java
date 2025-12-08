package net.bunnycraft.item.custom;

import net.bunnycraft.item.ModItems;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Overwrite;

public class AmethystBookItem extends BookItem {
    public AmethystBookItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 25;
    }

    public void giveItem(World world, PlayerEntity user, Hand hand,ItemStack stack) {
        user.getStackInHand(hand).decrement(1);

        if (user.getInventory().getEmptySlot() == -1) {
            user.dropItem(stack,true);
        } else {
            user.giveItemStack(stack);
        }

        user.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Hand opposite = hand.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack itemInOtherHand = user.getStackInHand(opposite);

        if (itemInOtherHand.isOf(Items.ENCHANTED_BOOK) || itemInOtherHand.isOf(ModItems.ENCHANTED_AMETHYST_BOOK)) {
            ItemStack newAmethystBook = ModItems.ENCHANTED_AMETHYST_BOOK.getDefaultStack();
            newAmethystBook.set(DataComponentTypes.STORED_ENCHANTMENTS,itemInOtherHand.get(DataComponentTypes.STORED_ENCHANTMENTS));

            giveItem(world,user,hand,newAmethystBook);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
