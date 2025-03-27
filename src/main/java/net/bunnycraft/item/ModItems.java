package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


import java.util.List;

public class ModItems {
    public static  final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static  final Item ROSEGOLD_INGOT = registerItem("rosegold_ingot", new Item(new Item.Settings()));
    public static  final Item PANCAKE_RABBIT = registerItem("pancake_rabbit", new Item(new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModItems() {
        Bunnycraft.LOGGER.info("Registering Mod Items for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(STEEL_INGOT);
            entries.add(ROSEGOLD_INGOT);
            entries.add(PANCAKE_RABBIT);
        });
    }
}
