package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Equipment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModArmorItem extends ArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;
    public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() ->
                this.BaseAttributeModifiersComponent().build());
    }

    public void AddAttributeModifierToBuilder(String id,float value,RegistryEntry<EntityAttribute> attribute, EntityAttributeModifier.Operation operation,AttributeModifiersComponent.Builder builder) {
        if (value == 0.0) {return;}
        AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
        Identifier identifier = Identifier.of("bunnycraft.",attributeModifierSlot.asString() + "." + id);
        builder.add(attribute, new EntityAttributeModifier(identifier,value,operation), attributeModifierSlot);
    }

    public AttributeModifiersComponent.Builder BaseAttributeModifiersComponent() {
        int armorProtection = material.value().getProtection(type);
        float armorToughness = material.value().toughness();
        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
        AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
        Identifier identifier = Identifier.ofVanilla("armor." + type.getName());
        builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, armorProtection, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        builder.add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(identifier, armorToughness, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        float knockbackResistance = material.value().knockbackResistance();
        if (knockbackResistance > 0.0F) {
            builder.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(identifier, knockbackResistance, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
        }

        return builder;
    };

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
