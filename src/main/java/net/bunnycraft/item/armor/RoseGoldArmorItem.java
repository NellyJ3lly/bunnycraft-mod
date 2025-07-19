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

public class RoseGoldArmorItem extends ModArmorItem implements Equipment {
    public RoseGoldArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float health_bonus) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() ->
        {
            AttributeModifiersComponent.Builder builder = this.BaseAttributeModifiersComponent();
            this.AddAttributeModifierToBuilder(
                    "health_bonus",
                    health_bonus,
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    EntityAttributeModifier.Operation.ADD_VALUE,
                    builder
            );
            return builder.build();
        });
    }

    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
