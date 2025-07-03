package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_COPPER_TOOL = createTag("needs_copper_tool");
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = createTag("incorrect_for_copper_tool");

        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");

        public static final TagKey<Block> NEEDS_ROSE_GOLD_TOOL = createTag("needs_rose_gold_tool");
        public static final TagKey<Block> INCORRECT_FOR_ROSE_GOLD_TOOL = createTag("incorrect_for_rose_gold_tool");

        public static final TagKey<Block> NEEDS_PRISMARINE_TOOL = createTag("needs_prismarine_tool");
        public static final TagKey<Block> INCORRECT_FOR_PRISMARINE_TOOL = createTag("incorrect_for_prismarine_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Bunnycraft.MOD_ID,name));
        }
    }
    public static class Items {

        public static final TagKey<Item> SPEARS = createTag("spear");

        public static final TagKey<Item> SPEAR_ENCHANTABLE = createTag("enchantable/spear");

        public static final TagKey<Item> HOE_ENCHANTABLE = createTag("enchantable/hoe");

        public static final TagKey<Item> PICKAXE_ENCHANTABLE = createTag("enchantable/pickaxe");

        public static final TagKey<Item> AXE_ENCHANTABLE = createTag("enchantable/axe");

        public static final TagKey<Item> SHOVEL_ENCHANTABLE = createTag("enchantable/shovel");

        public static final TagKey<Item> SHEAR_ENCHANTABLE = createTag("enchantable/shear");

        public static final TagKey<Item> ACCEPTS_LOYALTY = createTag("enchantable/accepts_loyalty");

        public static final TagKey<Item> ACCEPTS_SWEEPING_EDGE = createTag("enchantable/accepts_sweeping_edge");

        public static final TagKey<Item> ACCEPTS_GENERIC_COMBAT_ENCHANTS = createTag("enchantable/accepts_generic_combat_enchants");

        public static final TagKey<Item> ACCEPTS_MACE_ENCHANTS = createTag(("enchantable/accepts_mace_enchants"));



        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Bunnycraft.MOD_ID,name));
        }
    }
}
