package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.bunnycraft.item.ModArmors;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Consumer;

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

    boolean DontPlaySneakSound = false;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        ArmorItem item =  (ArmorItem) stack.getItem().asItem();
        if (item == null) {return;}
        if (entity instanceof LivingEntity livingentity) {
            if (!livingentity.isOnGround()) {return;}

            if (livingentity.isSneaking()) {
                if (!DontPlaySneakSound) {
                    DontPlaySneakSound = true;
                    world.playSound(livingentity,livingentity.getBlockPos(),
                            SoundEvents.ENTITY_ARMADILLO_ROLL,livingentity.getSoundCategory(),1f,1f);
                };
            } else if (!livingentity.isSneaking()) {
                if (DontPlaySneakSound) {
                    DontPlaySneakSound = false;
                    world.playSound(livingentity,livingentity.getBlockPos(),
                            SoundEvents.ENTITY_ARMADILLO_UNROLL_FINISH,livingentity.getSoundCategory(),1f,1f);
                }
            }
        }
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        return this.attributeModifiers.get();
    }


}
