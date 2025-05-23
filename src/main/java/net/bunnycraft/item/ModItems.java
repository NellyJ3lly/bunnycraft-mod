package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {
    public static  final Item ROSE_GOLD_INGOT = registerItem("rose_gold_ingot", new Item(new Item.Settings()));
    public static  final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static  final Item PANCAKE_RABBIT = registerItem("pancake_rabbit",SmithingTemplateItem.of(Identifier.of(Bunnycraft.MOD_ID,"rabbit_trim"), FeatureFlags.VANILLA));
    public static  final Item BUNDLE = registerItem("bundle", new BundleItem(new Item.Settings()));

    //item that reserves a slot for your spear
    public static final Item EMPTY_SPEAR_SLOT = registerItem("empty_spear_slot", new Item(new Item.Settings().maxCount(1)) {

        //appends a tooltip within an anonymous class
        @Override
        public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {

            //tells what the empty slot item does
            tooltip.add(Text.translatable("tooltip.bunnycraft.empty_slot.instructions"));

            super.appendTooltip(stack, context, tooltip, type);
        }

    });


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModItems() {
        Bunnycraft.LOGGER.info("Registering Mod Items for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ROSE_GOLD_INGOT);
            entries.add(STEEL_INGOT);
            entries.add(PANCAKE_RABBIT);
            entries.add(BUNDLE);
        });
    }
}
