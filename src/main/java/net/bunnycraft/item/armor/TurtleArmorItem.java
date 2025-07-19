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

public class TurtleArmorItem extends ModArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public TurtleArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float water_movement_speed) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() ->
        {
            AttributeModifiersComponent.Builder builder = this.BaseAttributeModifiersComponent();
            this.AddAttributeModifierToBuilder(
                    "water_movement_speed",
                    water_movement_speed,
                    EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY,
                    EntityAttributeModifier.Operation.ADD_VALUE,
                    builder
            );
            return builder.build();
        });
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
