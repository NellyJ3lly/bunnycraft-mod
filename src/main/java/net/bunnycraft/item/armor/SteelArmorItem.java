package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.bunnycraft.Bunnycraft;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

public class SteelArmorItem extends ModArmorItem implements Equipment {
    public SteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float movement_speed) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() ->
        {
            AttributeModifiersComponent.Builder builder = this.BaseAttributeModifiersComponent();
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

    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }
}
