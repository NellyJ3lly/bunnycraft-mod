package net.bunnycraft.item.armor;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> STEEL_ARMOR_MATERIAL = registerArmorMaterial("steel",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,4);
                map.put(ArmorItem.Type.LEGGINGS,7);
                map.put(ArmorItem.Type.CHESTPLATE,9);
                map.put(ArmorItem.Type.HELMET,4);
                map.put(ArmorItem.Type.BODY,13);
            }), 13, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ()-> Ingredient.ofItems(ModItems.STEEL_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"steel"))),4.0f,0.2f));

    public static final RegistryEntry<ArmorMaterial> ROSE_GOLD_ARMOR_MATERIAL = registerArmorMaterial("rose_gold",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,2);
                map.put(ArmorItem.Type.LEGGINGS,3);
                map.put(ArmorItem.Type.CHESTPLATE,5);
                map.put(ArmorItem.Type.HELMET,2);
                map.put(ArmorItem.Type.BODY,10);
            }), 30, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, ()-> Ingredient.ofItems(ModItems.ROSE_GOLD_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"rose_gold"))),0.0f,0.0f));

    public static final RegistryEntry<ArmorMaterial> GUARDIAN_ARMOR_MATERIAL = registerArmorMaterial("guardian",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,3);
                map.put(ArmorItem.Type.LEGGINGS,6);
                map.put(ArmorItem.Type.CHESTPLATE,8);
                map.put(ArmorItem.Type.HELMET,3);
                map.put(ArmorItem.Type.BODY,11);
            }), 30, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, ()-> Ingredient.ofItems(Items.PRISMARINE),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"guardian"))),1.0f,0.1f));

    public static final RegistryEntry<ArmorMaterial> COPPER_ARMOR_MATERIAL = registerArmorMaterial("copper",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 4);
            }), 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ()-> Ingredient.ofItems(Items.COPPER_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"copper"))),0.0f,0.0f));

    public static final RegistryEntry<ArmorMaterial> DIVING_MATERIAL = registerArmorMaterial("diving",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 5);
            }), 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ()-> Ingredient.ofItems(Items.COPPER_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"diving"))),0.5f,0.5f));
    public static final RegistryEntry<ArmorMaterial> MOD_TURTLE_MATERIAL = registerArmorMaterial("mod_turtle",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 5);
            }), 9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, ()-> Ingredient.ofItems(Items.TURTLE_SCUTE),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"mod_turtle"))),0f,0f));

    public static final RegistryEntry<ArmorMaterial> DEALMAKER_MATERIAL = registerArmorMaterial("dealmaker",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS,0);
                map.put(ArmorItem.Type.LEGGINGS,0);
                map.put(ArmorItem.Type.CHESTPLATE,0);
                map.put(ArmorItem.Type.HELMET,5);
                map.put(ArmorItem.Type.BODY,0);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, ()-> Ingredient.ofItems(ModItems.PIPIS),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"dealmaker"))),0.0f,0.0f));


    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(Bunnycraft.MOD_ID,name),material.get());

    }
}
