package net.bunnycraft.item.custom;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.util.ModTags;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class AmethystBookItem extends BookItem {
    public AmethystBookItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 25;
    }

    public void giveItem(PlayerEntity user, Hand hand,ItemStack stack) {
        user.getStackInHand(hand).decrement(1);

        if (user.getInventory().getEmptySlot() == -1) {
            user.dropItem(stack,true);
        } else {
            user.giveItemStack(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        // make this a translation later
        tooltip.add(Text.literal("Right click with this book in your hand and an Enchanted book in the other hand to duplicate it!"));

        super.appendTooltip(stack, context, tooltip, type);
    }

    boolean ClickedOnce = false;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Hand opposite = hand.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack itemInOtherHand = user.getStackInHand(opposite);

        if (itemInOtherHand.isOf(Items.ENCHANTED_BOOK) || itemInOtherHand.isOf(ModItems.ENCHANTED_AMETHYST_BOOK)) {

            int experienceCost = getExperienceCost(itemInOtherHand,user);
            if (experienceCost > 0) {
                user.addExperienceLevels(-experienceCost);

                ItemStack newAmethystBook = ModItems.ENCHANTED_AMETHYST_BOOK.getDefaultStack();
                newAmethystBook.set(DataComponentTypes.STORED_ENCHANTMENTS,itemInOtherHand.get(DataComponentTypes.STORED_ENCHANTMENTS));

                giveItem(user,hand,newAmethystBook);

                user.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    boolean DisplayedMessage = false;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (entity instanceof PlayerEntity playerEntity) {
            Hand hand = CheckIfHoldingBook(playerEntity);
            if (hand != null) {
                DisplayMessage(playerEntity,hand);
            } else if (DisplayedMessage) {
                DisplayedMessage = false;
                playerEntity.sendMessage(Text.literal(""),true);
            }
        }
    }

    public Hand CheckIfHoldingBook(PlayerEntity playerEntity) {
        if (playerEntity.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.AMETHYST_BOOK)
                && playerEntity.getStackInHand(Hand.OFF_HAND).isIn(ModTags.Items.ENCHANTED_BOOKS)) {
            return Hand.OFF_HAND;
        } else if (playerEntity.getStackInHand(Hand.OFF_HAND).isOf(ModItems.AMETHYST_BOOK)
                && playerEntity.getStackInHand(Hand.MAIN_HAND).isIn(ModTags.Items.ENCHANTED_BOOKS)) {
            return Hand.MAIN_HAND;
        }
        return null;
    }

    public void DisplayMessage(PlayerEntity playerEntity,Hand hand) {
        int experienceCost = getExperienceCost(playerEntity.getStackInHand(hand),playerEntity);

        if (experienceCost > 0) {
            playerEntity.sendMessage(Text.literal("This costs " + experienceCost + " levels"),true);
        } else {
            int experienceCost2 = getExperience(playerEntity.getStackInHand(hand));
            playerEntity.sendMessage(Text.literal("This is too expensive! it costs " + experienceCost2 + " levels"),true);
        }
        DisplayedMessage = true;
    }

    private int getExperienceCost(ItemStack stack, PlayerEntity playerEntity) {
        int experienceCost = getExperience(stack);
        int playerExperience = (playerEntity.experienceLevel);

        System.out.println(playerExperience + " " + experienceCost);

        if (playerExperience >= experienceCost) {
            return experienceCost;
        }
        return 0;
    }

    private int getExperience(ItemStack stack) {
        int i = 0;
        ItemEnchantmentsComponent itemEnchantmentsComponent = EnchantmentHelper.getEnchantments(stack);
        for (RegistryEntry<Enchantment> enchantmentEntry : itemEnchantmentsComponent.getEnchantments()) {
            i += itemEnchantmentsComponent.getLevel(enchantmentEntry);
        }
        return i;
    }
}
