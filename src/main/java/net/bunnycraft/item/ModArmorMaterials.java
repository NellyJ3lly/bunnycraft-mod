package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
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
                map.put(ArmorItem.Type.BODY,16);
            }), 13, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ()-> Ingredient.ofItems(ModItems.STEEL_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Bunnycraft.MOD_ID,"steel"))),4.0f,0.2f));


    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(Bunnycraft.MOD_ID,name),material.get());

    }
}
