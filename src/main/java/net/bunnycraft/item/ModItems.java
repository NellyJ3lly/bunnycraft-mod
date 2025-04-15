package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

public class ModItems {
    public static  final Item ROSEGOLD_INGOT = registerItem("rosegold_ingot", new Item(new Item.Settings()));
    public static  final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new Item.Settings()));
    public static  final Item PANCAKE_RABBIT = registerItem("pancake_rabbit",SmithingTemplateItem.of(Identifier.of(Bunnycraft.MOD_ID,"rabbit_trim"), FeatureFlags.VANILLA));
    public static  final Item BUNDLE = registerItem("bundle", new BundleItem(new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModItems() {
        Bunnycraft.LOGGER.info("Registering Mod Items for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ROSEGOLD_INGOT);
            entries.add(STEEL_INGOT);
            entries.add(PANCAKE_RABBIT);
            entries.add(BUNDLE);
        });
    }
}
