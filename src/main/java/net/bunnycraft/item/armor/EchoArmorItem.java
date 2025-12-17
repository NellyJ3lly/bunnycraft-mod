package net.bunnycraft.item.armor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.bunnycraft.util.ModTags;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class EchoArmorItem extends ModArmorItem implements Equipment {
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public boolean SlowdownEnabled;

    public EchoArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings, float movement_speed) {
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

        this.SlowdownEnabled = true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity && stack.getItem() instanceof ArmorItem armorItem) {
            if (livingEntity.getEquippedStack(armorItem.getSlotType()) == stack) {
                SlowdownEnabled = !world.getBlockState(entity.getBlockPos()).isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS)
                        || !world.getBlockState(entity.getBlockPos().add(0,-1,0)).isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS);
            }
        }
    }

    public AttributeModifiersComponent getAttributeModifiers() {
        if (SlowdownEnabled) {
            return this.attributeModifiers.get();
        } else {
            return BaseAttributeModifiersComponent().build();
        }
    }
}
