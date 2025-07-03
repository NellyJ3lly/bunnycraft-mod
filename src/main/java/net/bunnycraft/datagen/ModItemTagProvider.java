package net.bunnycraft.datagen;

import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.armor.ModArmors;
import net.bunnycraft.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
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
