package net.bunnycraft.datagen;

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

        //adds basic weapon tags

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModTools.COPPER_SWORD)
                .add(ModTools.STEEL_SWORD)
                .add(ModTools.ROSE_GOLD_SWORD);

        getOrCreateTagBuilder(ModTags.Items.SPEARS)
                .add(ModTools.WOODEN_SPEAR)
                .add(ModTools.STONE_SPEAR)
                .add(ModTools.COPPER_SPEAR)
                .add(ModTools.IRON_SPEAR)
                .add(ModTools.GOLDEN_SPEAR)
                .add(ModTools.DIAMOND_SPEAR)
                .add(ModTools.NETHERITE_SPEAR)
                .add(ModTools.STEEL_SPEAR);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModTools.COPPER_PICKAXE)
                .add(ModTools.STEEL_PICKAXE)
                .add(ModTools.ROSE_GOLD_PICKAXE);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModTools.COPPER_HOE)
                .add(ModTools.STEEL_HOE)
                .add(ModTools.ROSE_GOLD_HOE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModTools.COPPER_SHOVEL)
                .add(ModTools.STEEL_SHOVEL)
                .add(ModTools.ROSE_GOLD_SHOVEL);


        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModTools.COPPER_AXE)
                .add(ModTools.STEEL_AXE)
                .add(ModTools.ROSE_GOLD_AXE);

        //trim tags

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ROSE_GOLD_INGOT)
                .add(ModItems.STEEL_INGOT);

        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.PANCAKE_RABBIT);

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
