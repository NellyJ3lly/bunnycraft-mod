package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.custom.SpearItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModTools  {


    //base tool damages, same as vanilla
    public static final float pickaxeDmg = 1;
    public static final int swordDmg = 3; // idk why this has to be an int, but if needed it should be changed to a float with a mixin
    public static final float spearDmg = 3;
    public static final float axeDmg = 6;
    public static final float shovelDmg = 1.5f;
    public static final float hoeDmg = 0;

    //always declare a new tool with these values, tweak the stats in the toolMaterial


    // WOODEN TOOLS
    public static final Item WOODEN_SPEAR = registerTool("wooden_spear", new SpearItem(ToolMaterials.WOOD, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.WOOD, spearDmg, -2)), 1.2f, 2.5f));

    // STONE TOOLS
    public static final Item STONE_SPEAR = registerTool("stone_spear", new SpearItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.STONE, spearDmg, -2.2f)), 1.7f, 2f));

    // COPPER TOOLS
    public static  final Item COPPER_PICKAXE = registerTool("copper_pickaxe", new PickaxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.COPPER, pickaxeDmg, -2.2f))));

    public static  final Item COPPER_SWORD = registerTool("copper_sword", new SwordItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.COPPER, swordDmg, -2.2f))));

    public static final Item COPPER_SPEAR =registerTool("copper_spear", new SpearItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ModToolMaterials.COPPER, spearDmg, -2.2f)), 1.2f, 2.5f));

    public static  final Item COPPER_AXE = registerTool("copper_axe", new AxeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.COPPER, axeDmg, -2.6f))));

    public static  final Item COPPER_SHOVEL = registerTool("copper_shovel", new ShovelItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.COPPER, shovelDmg, -2.4f))));

    public static  final Item COPPER_HOE = registerTool("copper_hoe", new HoeItem(ModToolMaterials.COPPER, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.COPPER, hoeDmg, -2.0f))));

    // IRON TOOLS
    public static final Item IRON_SPEAR = registerTool("iron_spear", new SpearItem(ToolMaterials.IRON, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.IRON, spearDmg, -2.4f)), 1f, 2.5f));

    // GOLDEN TOOLS
    public static final Item GOLDEN_SPEAR = registerTool("golden_spear", new SpearItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.GOLD, spearDmg, -2.6f)), .5f, 4.5f));

    // DIAMOND TOOLS
    public static final Item DIAMOND_SPEAR = registerTool("diamond_spear", new SpearItem(ToolMaterials.DIAMOND, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.DIAMOND, spearDmg, -2.8f)), .5f, 3.5f));

    // NETHERITE TOOLS
    public static final Item NETHERITE_SPEAR = registerTool("netherite_spear", new SpearItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof().attributeModifiers(
            SpearItem.createAttributeModifiers(ToolMaterials.NETHERITE, spearDmg, -3)), .2f, 4.5f));

    // STEEL TOOLS
    public static  final Item STEEL_PICKAXE = registerTool("steel_pickaxe", new PickaxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, pickaxeDmg, -3f))));

    public static  final Item STEEL_SWORD = registerTool("steel_sword", new SwordItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.STEEL, swordDmg, -3f))));

    public static final Item STEEL_SPEAR = registerTool("steel_spear", new SpearItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ModToolMaterials.STEEL, spearDmg, -2.8f)), .2f, 3.5f));

    public static  final Item STEEL_AXE = registerTool("steel_axe", new AxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.STEEL, axeDmg, -3.2f))));

    public static  final Item STEEL_SHOVEL = registerTool("steel_shovel", new ShovelItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.STEEL, shovelDmg, -3.2f))));

    public static  final Item STEEL_HOE = registerTool("steel_hoe", new HoeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.STEEL, hoeDmg, -2.8f))));

    // later make it so they always give 3 maybe a chance for 4 wool while shearingy
    public static  final Item STEEL_SHEARS = registerItem("steel_shears", new ShearsItem(new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.STEEL, 0, -1f)).maxDamage(714).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())));


    // ROSE GOLD TOOLS
    public static  final Item ROSE_GOLD_PICKAXE = registerTool("rose_gold_pickaxe", new PickaxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            PickaxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, pickaxeDmg, -1f))));

    public static  final Item ROSE_GOLD_SWORD = registerTool("rose_gold_sword", new SwordItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            SwordItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, swordDmg, -1f))));

    public static final Item ROSE_GOLD_SPEAR = registerTool("rose_gold_spear", new SpearItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            SpearItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, spearDmg, -1.2f)), .5f, 5f));

    public static  final Item ROSE_GOLD_AXE = registerTool("rose_gold_axe", new AxeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            AxeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, axeDmg, -1.4f))));

    public static  final Item ROSE_GOLD_SHOVEL = registerTool("rose_gold_shovel", new ShovelItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            ShovelItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, shovelDmg, -1.4f))));

    public static  final Item ROSE_GOLD_HOE = registerTool("rose_gold_hoe", new HoeItem(ModToolMaterials.ROSE_GOLD, new Item.Settings().attributeModifiers(
            HoeItem.createAttributeModifiers(ModToolMaterials.ROSE_GOLD, hoeDmg, -0.8f))));


    public static Map<Integer, Item> pickaxeList;
    public static Map<Integer, Item> swordList;
    public static Map<Integer, Item> spearList;
    public static Map<Integer, Item> axeList;
    public static Map<Integer, Item> shovelList;
    public static Map<Integer, Item> hoeList;
    private static boolean listsRegistered = false;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    private static Item registerTool(String name, Item item) {

        if (!listsRegistered) {

            pickaxeList = new HashMap<>();
            swordList = new HashMap<>();
            spearList = new HashMap<>();
            axeList = new HashMap<>();
            shovelList = new HashMap<>();
            hoeList = new HashMap<>();

            listsRegistered = true;
        }

        //careful when adding new tools because if the toolname ends with an existing tool like pickaxe ending in axe it causes conflicts if not handled
        if(name.endsWith("pickaxe")) {pickaxeList.put(pickaxeList.size(), item);}
        if(name.endsWith("sword")) {swordList.put(swordList.size(), item);}
        if(name.endsWith("spear")) {spearList.put(spearList.size(), item);}
        if(name.endsWith("axe") && !name.endsWith("pickaxe")) {axeList.put(axeList.size(), item);}
        if(name.endsWith("shovel")) {shovelList.put(shovelList.size(), item);}
        if(name.endsWith("hoe")) {hoeList.put(hoeList.size(), item);}

        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModTools() {

        Bunnycraft.LOGGER.info("Registering Mod Tools for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(STEEL_SHEARS);

            //add pickaxes
            for(int i = 0; ModTools.pickaxeList.get(i) != null; i++) {
                entries.add(ModTools.pickaxeList.get(i));
            }

            //add swords
            for(int i = 0; ModTools.swordList.get(i) != null; i++) {
                entries.add(ModTools.swordList.get(i));
            }

            //add spears
            for(int i = 0; ModTools.spearList.get(i) != null; i++) {
                entries.add(ModTools.spearList.get(i));
            }

            //add axes
            for(int i = 0; ModTools.axeList.get(i) != null; i++) {
                entries.add(ModTools.axeList.get(i));
            }

            //add shovels
            for(int i = 0; ModTools.shovelList.get(i) != null; i++) {
                entries.add(ModTools.shovelList.get(i));
            }

            //add hoes
            for(int i = 0; ModTools.hoeList.get(i) != null; i++) {
                entries.add(ModTools.hoeList.get(i));
            }
        });
    }

}

//how to make a spear with a custom model

//create a model and renderer class and register the new thing as a new entity pointing to the model and renderer classes
//within spear entity make sure the super call passes in the right thing by checking the name (see how its done already there)