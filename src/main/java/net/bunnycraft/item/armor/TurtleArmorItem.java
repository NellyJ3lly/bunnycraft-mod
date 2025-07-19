package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Equipment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class TurtleArmorItem extends ArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public TurtleArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float water_movement_speed) {
        super(material, type, settings);

        final Identifier WATER_MOVEMENT_SPEED_ID = Identifier.of(type.asString() + "_water_movement");
        this.attributeModifiers = Suppliers.memoize(() -> {
            int i = material.value().getProtection(type);
            float f = material.value().toughness();
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
            Identifier identifier = Identifier.ofVanilla("armor." + type.getName());
            builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, (double)i, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            builder.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, (double)f, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            float g = material.value().knockbackResistance();
            if (g > 0.0F) {
                builder.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, (double)g, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            }

            builder.add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY,
                    new EntityAttributeModifier(WATER_MOVEMENT_SPEED_ID, water_movement_speed, EntityAttributeModifier.Operation.ADD_VALUE),
                    attributeModifierSlot
            );

            return builder.build();
        });
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }

}
