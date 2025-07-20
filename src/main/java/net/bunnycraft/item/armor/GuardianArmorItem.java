package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Equipment;
import net.minecraft.registry.entry.RegistryEntry;

public class GuardianArmorItem extends ModArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public GuardianArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float water_movement_speed,float oxygen_bonus, float attack_damage, float movement_speed) {
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
            this.AddAttributeModifierToBuilder(
                    "oxygen_bonus",
                    oxygen_bonus,
                    EntityAttributes.GENERIC_OXYGEN_BONUS,
                    EntityAttributeModifier.Operation.ADD_VALUE,
                    builder
            );
            this.AddAttributeModifierToBuilder(
                    "attack_damage",
                    attack_damage,
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    builder
            );
            this.AddAttributeModifierToBuilder(
                    "movement_speed",
                    movement_speed,
                    EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    builder
            );
            return builder.build();
        });
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
