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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final Identifier BASE_PLAYER_ENTITY_INTERACTION_RANGE_ID = Identifier.of(Bunnycraft.MOD_ID, "base_entity_interaction_range");

    //registering as an ingot adds it to a public list currently used only by cauldron alloyer
    public static  final Item ROSE_GOLD_INGOT = registerIngot("rose_gold_ingot", new Item(new Item.Settings()));
    public static  final Item STEEL_INGOT = registerIngot("steel_ingot", new Item(new Item.Settings()));
    public static  final Item BREEZE_BAR = registerIngot("breeze_bar", new Item(new Item.Settings()));
    public static  final Item PIPIS = registerIngot("pipis", new Item(new Item.Settings()));
    public static  final Item VAULT_REWINDER = registerIngot("vault_rewinder", new Item(new Item.Settings()));

    public static  final Item STEEL_UPGRADE_TEMPLATE = registerIngot("steel_upgrade_template", new Item(new Item.Settings()));

    public static  final Item MOLTEN_ROSE_GOLD = registerItem("molten_rose_gold", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static  final Item MOLTEN_STEEL = registerItem("molten_steel", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));
    public static  final Item MOLTEN_NETHERITE = registerItem("molten_netherite", new Item(new Item.Settings().maxCount(1).recipeRemainder(Items.BUCKET)));

    public static  final Item PANCAKE_RABBIT = registerItem("pancake_rabbit",SmithingTemplateItem.of(Identifier.of(Bunnycraft.MOD_ID,"rabbit_trim"), FeatureFlags.VANILLA));

    public static  final Item COPPER_BUNNYCOIN = registerItem("copper_bunnycoin", new Item(new Item.Settings()));
    public static  final Item GOLD_BUNNYCOIN = registerItem("gold_bunnycoin", new Item(new Item.Settings()));
    public static  final Item DIAMOND_BUNNYCOIN = registerItem("diamond_bunnycoin", new Item(new Item.Settings()));

    public static  final Item MOLD = registerItem("mold", new Item( new Item.Settings()));

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

    public static Map<Integer, Item> ingotList;
    private static boolean listsRegistered = false;

    private static Item registerIngot(String name, Item item) {

        if (!listsRegistered) {

            ingotList = new HashMap<>();

            listsRegistered = true;

            ingotList.put(0, Items.IRON_INGOT);
            ingotList.put(1, Items.COPPER_INGOT);
            ingotList.put(2, Items.GOLD_INGOT);
            ingotList.put(3, Items.DIAMOND);
            ingotList.put(4, Items.NETHERITE_SCRAP);
            ingotList.put(5, Items.DIRT); // used for a conversion recipe
            //add any other items that should be able to go into the cauldron NOT MOD ITEMS, those should register using this method
        }

        assert ingotList != null;
        ingotList.put(ingotList.size(), item);

        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModItems() {
        Bunnycraft.LOGGER.info("Registering Mod Items for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ROSE_GOLD_INGOT);
            entries.add(STEEL_INGOT);
            entries.add(PANCAKE_RABBIT);
            entries.add(COPPER_BUNNYCOIN);
            entries.add(GOLD_BUNNYCOIN);
            entries.add(DIAMOND_BUNNYCOIN);
        });
    }
}
