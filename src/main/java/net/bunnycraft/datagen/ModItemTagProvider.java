package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import javax.swing.text.html.HTML;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    protected void createLists(Map<Integer, Item> list, TagKey<Item> Tag) {
        for(int i = 0; list.get(i) != null; i++) {
            getOrCreateTagBuilder(Tag).add(list.get(i));
        }
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        createLists(ModTools.pickaxeList,ItemTags.PICKAXES);
        createLists(ModTools.swordList,ItemTags.SWORDS);
        createLists(ModTools.spearList,ModTags.Items.SPEARS);
        createLists(ModTools.shovelList,ItemTags.SHOVELS);
        createLists(ModTools.axeList,ItemTags.AXES);
        createLists(ModTools.hoeList,ItemTags.HOES);

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.SCULK_PLANKS.asItem());

        getOrCreateTagBuilder(ItemTags.DYEABLE)
                .add(ModArmors.DIVING_BOOTS)
                .add(ModArmors.DIVING_LEGGINGS)
                .add(ModArmors.DIVING_CHESTPLATE);

        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModArmors.TURTLE_BOOTS)
                .add(ModArmors.COPPER_BOOTS)
                .add(ModArmors.ROSE_GOLD_BOOTS)
                .add(ModArmors.GUARDIAN_BOOTS)
                .add(ModArmors.DIVING_BOOTS)
                .add(ModArmors.STEEL_BOOTS);

        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModArmors.TURTLE_LEGGINGS)
                .add(ModArmors.COPPER_LEGGINGS)
                .add(ModArmors.ROSE_GOLD_LEGGINGS)
                .add(ModArmors.GUARDIAN_LEGGINGS)
                .add(ModArmors.DIVING_LEGGINGS)
                .add(ModArmors.STEEL_LEGGINGS);

        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModArmors.TURTLE_CHESTPLATE)
                .add(ModArmors.COPPER_CHESTPLATE)
                .add(ModArmors.ROSE_GOLD_CHESTPLATE)
                .add(ModArmors.GUARDIAN_CHESTPLATE)
                .add(ModArmors.DIVING_CHESTPLATE)
                .add(ModArmors.STEEL_CHESTPLATE);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModArmors.DEALMAKER)
                .add(ModArmors.COPPER_HELMET)
                .add(ModArmors.ROSE_GOLD_HELMET)
                .add(ModArmors.GUARDIAN_HELMET)
                .add(ModArmors.DIVING_HELMET)
                .add(ModArmors.STEEL_HELMET);

        //trim tags

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ROSE_GOLD_INGOT)
                .add(ModItems.STEEL_INGOT);

        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.PANCAKE_RABBIT);

        getOrCreateTagBuilder(ModTags.Items.SPEAR_ENCHANTABLE)
                .addTag(ModTags.Items.SPEARS);

        getOrCreateTagBuilder(ModTags.Items.HOE_ENCHANTABLE)
                .addTag(ItemTags.HOES);

        getOrCreateTagBuilder(ModTags.Items.PICKAXE_ENCHANTABLE)
                .addTag(ItemTags.PICKAXES);

        getOrCreateTagBuilder(ModTags.Items.AXE_ENCHANTABLE)
                .addTag(ItemTags.AXES);

        getOrCreateTagBuilder(ModTags.Items.SHOVEL_ENCHANTABLE)
                .addTag(ItemTags.SHOVELS);

        getOrCreateTagBuilder(ModTags.Items.SHEAR_ENCHANTABLE)
                .add(Items.SHEARS)
                .add(ModTools.STEEL_SHEARS);


        //makes loyalty go to tridents and spears by making loyalty.json target the loyalty compat tag
        //currently targeting this tag is loyalty.json
        getOrCreateTagBuilder(ModTags.Items.ACCEPTS_LOYALTY)
                .addOptionalTag(ItemTags.TRIDENT_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);

        //currently targeting this tag is bane_of_arthropods.json, fire_aspect.json, knockback.json, looting,json, sharpness.json, smite.json

        getOrCreateTagBuilder(ModTags.Items.ACCEPTS_GENERIC_COMBAT_ENCHANTS)  // for an item to accept this it must also be added to sharp weapon enchantable
                .addOptionalTag(ItemTags.SWORD_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.HOE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.PICKAXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.AXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHOVEL_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHEAR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.HOE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.PICKAXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.AXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHOVEL_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHEAR_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.ACCEPTS_MACE_ENCHANTS)
                .addOptionalTag(ItemTags.MACE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.PICKAXE_ENCHANTABLE);

        getOrCreateTagBuilder(ModTags.Items.ACCEPTS_SWEEPING_EDGE)
                .addOptionalTag(ItemTags.SWORD_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.HOE_ENCHANTABLE);

        //vanilla tag that allows getting stuff like unbreaking
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.HOE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.PICKAXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.AXE_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHOVEL_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SHEAR_ENCHANTABLE);

    }
}
