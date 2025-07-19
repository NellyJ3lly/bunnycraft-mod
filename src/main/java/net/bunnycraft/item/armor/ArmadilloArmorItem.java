package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ArmadilloArmorItem extends ModArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public ArmadilloArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float movement_speed) {
        super(material, type, settings);

        this.attributeModifiers = Suppliers.memoize(() ->
        {
            AttributeModifiersComponent.Builder builder = this.BaseAttributeModifiersComponent();
            this.AddAttributeModifierToBuilder(
                    "movement_speed",
                    movement_speed,
                    EntityAttributes.GENERIC_MOVEMENT_SPEED ,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                    builder
            );
            return builder.build();
        });
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        System.out.println(stack);
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }


}
