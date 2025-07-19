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

public class RoseGoldArmorItem extends ArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public RoseGoldArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float health_bonus) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() -> {
            final Identifier HEALTH_BONUS_ID = Identifier.of(type.asString() + ".health_bonus");
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

            builder.add(EntityAttributes.GENERIC_MAX_HEALTH,
                    new EntityAttributeModifier(HEALTH_BONUS_ID, health_bonus, EntityAttributeModifier.Operation.ADD_VALUE),
                    attributeModifierSlot
            );

            return builder.build();
        });
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }

}
