package net.bunnycraft.datagen;

import net.bunnycraft.item.ModArmors;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        //add pickaxes
        for(int i = 0; ModTools.pickaxeList.get(i) != null; i++) {
            getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModTools.pickaxeList.get(i));
        }

        //add swords
        for(int i = 0; ModTools.swordList.get(i) != null; i++) {
            getOrCreateTagBuilder(ItemTags.SWORDS).add(ModTools.swordList.get(i));
        }

        //add spears
        for(int i = 0; ModTools.spearList.get(i) != null; i++) {
            getOrCreateTagBuilder(ModTags.Items.SPEARS).add(ModTools.spearList.get(i));
        }

        //add axes
        for(int i = 0; ModTools.axeList.get(i) != null; i++) {
            getOrCreateTagBuilder(ItemTags.AXES).add(ModTools.axeList.get(i));
        }

        //add shovels
        for(int i = 0; ModTools.shovelList.get(i) != null; i++) {
            getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModTools.shovelList.get(i));
        }

        //add hoes
        for(int i = 0; ModTools.hoeList.get(i) != null; i++) {
            getOrCreateTagBuilder(ItemTags.HOES).add(ModTools.hoeList.get(i));
        }


        //trim tags

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ROSE_GOLD_INGOT)
                .add(ModItems.STEEL_INGOT);

        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.PANCAKE_RABBIT);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModArmors.STEEL_HELMET)
                .add(ModArmors.STEEL_CHESTPLATE)
                .add(ModArmors.STEEL_LEGGINGS)
                .add(ModArmors.STEEL_BOOTS);

        //additional spear tags

        getOrCreateTagBuilder(ModTags.Items.SPEAR_ENCHANTABLE)
                .addTag(ModTags.Items.SPEARS);

        //makes loyalty go to tridents and spears by making loyalty.json target the loyalty compat tag
        //currently targeting this tag is loyalty.json
        getOrCreateTagBuilder(ModTags.Items.LOYALTY_COMPAT_TAG)
                .addOptionalTag(ItemTags.TRIDENT_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);

        //all enchants that target sword now also target spear by ovveridding them to target the spear compat tag
        //currently targeting this tag is bane_of_arthropods.json, fire_aspect.json, knockback.json, looting,json, sharpness.json, smite.json
        getOrCreateTagBuilder(ModTags.Items.SPEAR_COMPAT_TAG)
                .addOptionalTag(ItemTags.SWORD_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);

        //vanilla tag that allows getting stuff like unbreaking
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);


        //additional tag definitons, i dont fully know why i need to define these but they are importand probably. they dont seem to effect what you can get in an enchanting table
        getOrCreateTagBuilder(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);

        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .addOptionalTag(ModTags.Items.SPEAR_ENCHANTABLE);
    }
}
