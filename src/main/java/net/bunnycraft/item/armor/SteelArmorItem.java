package net.bunnycraft.item.armor;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Equipment;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class SteelArmorItem extends ModArmorItem implements Equipment {
    public SteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(EquipmentSlot slot, float movement_speed) {
        final Identifier MOVEMENT_SPEED_ID = Identifier.of(slot.asString() + "_steel_movement_speed");
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(MOVEMENT_SPEED_ID, movement_speed, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.forEquipmentSlot(slot)
                )
                .build();
    }
}
