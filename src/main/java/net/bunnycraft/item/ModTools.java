package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.custom.SpearItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModTools  {

    // COPPER TOOLS
    public static  final Item COPPER_PICKAXE = registerItem("copper_pickaxe", new PickaxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 2, -2.2f))));

    public static  final Item COPPER_SWORD = registerItem("copper_sword", new SwordItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.COPPER, 3, -2.2f))));

    public static  final Item COPPER_AXE = registerItem("copper_axe", new AxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    AxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 5, -2.6f))));

    public static  final Item COPPER_SHOVEL = registerItem("copper_shovel", new ShovelItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    ShovelItem.createAttributeModifiers(ModToolMaterials.COPPER, 3, -2.4f))));

    public static  final Item COPPER_HOE = registerItem("copper_hoe", new HoeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    HoeItem.createAttributeModifiers(ModToolMaterials.COPPER, 2, -2.0f))));

    // STEEL TOOLS
    public static  final Item STEEL_PICKAXE = registerItem("steel_pickaxe", new PickaxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 2, -3f))));

    public static  final Item STEEL_SWORD = registerItem("steel_sword", new SwordItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.STEEL, 4, -3f))));

    public static  final Item STEEL_AXE = registerItem("steel_axe", new AxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 6, -3.2f))));

    public static  final Item STEEL_SHOVEL = registerItem("steel_shovel", new ShovelItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.STEEL, 2, -3.2f))));

    public static  final Item STEEL_HOE = registerItem("steel_hoe", new HoeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.STEEL, 1, -2.8f))));

    // ROSE GOLD TOOLS
    public static  final Item ROSE_GOLD_PICKAXE = registerItem("rose_gold_pickaxe", new PickaxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 2, -1f))));

    public static  final Item ROSE_GOLD_SWORD = registerItem("rose_gold_sword", new SwordItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 3, -1f))));

    public static  final Item ROSE_GOLD_AXE = registerItem("rose_gold_axe", new AxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 4, -1.4f))));

    public static  final Item ROSE_GOLD_SHOVEL = registerItem("rose_gold_shovel", new ShovelItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 2, -1.4f))));

    public static  final Item ROSE_GOLD_HOE = registerItem("rose_gold_hoe", new HoeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 1, -0.8f))));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModTools() {
        Bunnycraft.LOGGER.info("Registering Mod Tools for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(COPPER_PICKAXE);
            entries.add(COPPER_SWORD);
            entries.add(COPPER_AXE);
            entries.add(COPPER_SHOVEL);
            entries.add(COPPER_HOE);

            entries.add(STEEL_PICKAXE);
            entries.add(STEEL_SWORD);
            entries.add(STEEL_AXE);
            entries.add(STEEL_SHOVEL);
            entries.add(STEEL_HOE);

            entries.add(ROSE_GOLD_PICKAXE);
            entries.add(ROSE_GOLD_SWORD);
            entries.add(ROSE_GOLD_AXE);
            entries.add(ROSE_GOLD_SHOVEL);
            entries.add(ROSE_GOLD_HOE);
        });
    }
}
