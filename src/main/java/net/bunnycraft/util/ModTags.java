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


        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Bunnycraft.MOD_ID,name));
        }
    }
    public static class Items {

        //the spear tag
        public static final TagKey<Item> SPEARS = createTag("spear");

        public static final TagKey<Item> SPEAR_ENCHANTABLE = createTag("enchantable/spear");

        //a special tag used only for loyalty that allows loyalty to go onto a spear and a trident
        // the loyalty json targets this tag and this tag targets spear enchantable and trident enchantable
        public static final TagKey<Item> LOYALTY_COMPAT_TAG = createTag("enchantable/loyalty_compat_tag");

        //another special tag used for al sword enchants that allows them to go on spears or swords except sweeping edge
        public static final TagKey<Item> SPEAR_COMPAT_TAG = createTag("enchantable/spear_compat_tag");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Bunnycraft.MOD_ID,name));
        }
    }
}
