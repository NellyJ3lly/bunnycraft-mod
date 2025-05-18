package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.custom.SpearItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModTools  {

    public static final int spearDmg = 3; // the base spear damage as a variable for easy balancing DO NOT REMOVE its used in spear entity

    // WOODEN TOOLS
    public static final Item WOODEN_SPEAR = registerSpear("wooden_spear", new SpearItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.WOOD, spearDmg, -2)), 1.2f, 2.5f));

    // STONE TOOLS
    public static final Item STONE_SPEAR = registerSpear("stone_spear", new SpearItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.STONE, spearDmg, -2.2f)), 1.7f, 2f));

    // COPPER TOOLS
    public static  final Item COPPER_PICKAXE = registerItem("copper_pickaxe", new PickaxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 2, -2.2f))));

    public static  final Item COPPER_SWORD = registerItem("copper_sword", new SwordItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.COPPER, 3, -2.2f))));

    public static final Item COPPER_SPEAR = registerSpear("copper_spear", new SpearItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ModToolMaterials.COPPER, spearDmg, -2.2f)), 1.2f, 2.5f));

    public static  final Item COPPER_AXE = registerItem("copper_axe", new AxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    AxeItem.createAttributeModifiers(ModToolMaterials.COPPER, 5, -2.6f))));

    public static  final Item COPPER_SHOVEL = registerItem("copper_shovel", new ShovelItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    ShovelItem.createAttributeModifiers(ModToolMaterials.COPPER, 3, -2.4f))));

    public static  final Item COPPER_HOE = registerItem("copper_hoe", new HoeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
                    HoeItem.createAttributeModifiers(ModToolMaterials.COPPER, 2, -2.0f))));

    // IRON TOOLS
    public static final Item IRON_SPEAR = registerSpear("iron_spear", new SpearItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.IRON, spearDmg, -2.4f)), 1f, 2.5f));

    // GOLDEN TOOLS
    public static final Item GOLDEN_SPEAR = registerSpear("golden_spear", new SpearItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.GOLD, spearDmg, -2.6f)), .5f, 4.5f));

    // DIAMOND TOOLS
    public static final Item DIAMOND_SPEAR = registerSpear("diamond_spear", new SpearItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.DIAMOND, spearDmg, -2.8f)), .5f, 3.5f));

    // NETHERITE TOOLS
    public static final Item NETHERITE_SPEAR = registerSpear("netherite_spear", new SpearItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.NETHERITE, spearDmg, -3)), .2f, 4.5f));


    // STEEL TOOLS
    public static  final Item STEEL_PICKAXE = registerItem("steel_pickaxe", new PickaxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 2, -3f))));

    public static  final Item STEEL_SWORD = registerItem("steel_sword", new SwordItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.STEEL, 4, -3f))));

    public static final Item STEEL_SPEAR = registerSpear("steel_spear", new SpearItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ModToolMaterials.COPPER, spearDmg, -2.8f)), .2f, 3.5f));

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

    //add a rose gold spear not sure if the rose gold is finished yet tho

    public static  final Item ROSE_GOLD_AXE = registerItem("rose_gold_axe", new AxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 4, -1.4f))));

    public static  final Item ROSE_GOLD_SHOVEL = registerItem("rose_gold_shovel", new ShovelItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 2, -1.4f))));

    public static  final Item ROSE_GOLD_HOE = registerItem("rose_gold_hoe", new HoeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, 1, -0.8f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }



    private static int i = 0;
    static Map<Integer, Item> spearList;

    //spear adding helper method, if you dont use this method things will break because this method creates a list of all spears used by other things
    private static Item registerSpear(String name, Item item) {


        if (spearList == null) {
            spearList = new HashMap<>();
        }


        spearList.put(i, item);
        i++;

        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    //helper methods for returning spear info
    public static String getSpearName(int index) {
        //gets rid of the item.bunnycraft. part of the translationKey
        if (getSpear(index) != null) {
            return Objects.requireNonNull(getSpear(index)).getTranslationKey().substring(16);
        } else {
            return null;
        }
    }

    // use this for loop to iterate through all spears and check something about each one, useful for initializing something for every spear
    // for(int i = 0; ModTools.getSpear(i) != null; i++) {}

    public static Item getSpear(int index) {
        if (spearList.containsKey(index)) {
            return spearList.get(index);
        }
        return null;
    }



    public static void registerModTools() {

        Bunnycraft.LOGGER.info("Registering Mod Tools for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {

            entries.add(COPPER_PICKAXE);
            entries.add(COPPER_AXE);
            entries.add(COPPER_SWORD);
            entries.add(COPPER_SHOVEL);
            entries.add(COPPER_HOE);

            entries.add(STEEL_PICKAXE);
            entries.add(STEEL_AXE);
            entries.add(STEEL_SWORD);
            entries.add(STEEL_SHOVEL);
            entries.add(STEEL_HOE);

            entries.add(ROSE_GOLD_PICKAXE);
            entries.add(ROSE_GOLD_AXE);
            entries.add(ROSE_GOLD_SWORD);
            entries.add(ROSE_GOLD_SHOVEL);
            entries.add(ROSE_GOLD_HOE);

            //add spears
            for(int i = 0; ModTools.getSpear(i) != null; i++) {
                entries.add(ModTools.getSpear(i));
            }
        });
    }



}



// HOW TO ADD A NEW SPEAR

// each spear requires 2 textures, one that goes on the model and one that's the item

//first texture is 2d as any item is
//second texture is to be named model_material_spear and is the texture that goes on the 3d model

//declare the spear as normal but use spearDmg variable as the attack damage so we can balence it later if needed

//add the recipe

//TODO (for dognmyface) make all item categories this easy to add, basically makes it so you only drop in textures, define the item and done.

//list of things need changing:
// ModItemTagProvider, ModItemGroups, ModModelProvider
// maybe ModRecipeProvider, don't do yet

