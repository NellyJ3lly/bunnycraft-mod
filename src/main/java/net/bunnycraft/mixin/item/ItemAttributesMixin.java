package net.bunnycraft.mixin.item;


import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.custom.SpearItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;


@Mixin(ItemStack.class)
public abstract class ItemAttributesMixin {

    @Shadow
    public abstract Item getItem();




    @Inject(
            method = "applyAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
            at = @At("TAIL")
    )
    private void addCustomAttributes(
            EquipmentSlot slot,
            BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer,
            CallbackInfo ci) {

        Item item = this.getItem();

        //changes range stats
        if (item instanceof SwordItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.swordRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof SpearItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.spearRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof TridentItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.spearRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof AxeItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.axeRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof PickaxeItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.pickaxeRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof ShovelItem toolItem && slot == EquipmentSlot.MAINHAND) {


            RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

            EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                    Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                    Bunnycraft.shovelRange,
                    EntityAttributeModifier.Operation.ADD_VALUE
            );

            consumer.accept(rangeAttribute, rangeModifier);

        }

        if (item instanceof HoeItem toolItem && slot == EquipmentSlot.MAINHAND) {


                RegistryEntry<EntityAttribute> rangeAttribute = EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE;

                EntityAttributeModifier rangeModifier = new EntityAttributeModifier(
                        Identifier.of(Bunnycraft.MOD_ID, toolItem.getTranslationKey() + "_range_id"),
                        Bunnycraft.hoeRange,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );

                consumer.accept(rangeAttribute, rangeModifier);

        }

        //makes steel armor slow you down
        if (item instanceof ArmorItem armor && slot.isArmorSlot()) {


            //gets the material name of the armor piece
            String material = armor.getMaterial().getIdAsString().substring(11);

            if(material.equals("steel")) {

                RegistryEntry<EntityAttribute> movementAttribute = EntityAttributes.GENERIC_MOVEMENT_SPEED;

                EntityAttributeModifier movementModifier = new EntityAttributeModifier(
                        Identifier.of(Bunnycraft.MOD_ID, armor.getTranslationKey() + "_movement_id"), //ensures a unique identifier for each piece
                        Bunnycraft.steelSlowdown,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );

                consumer.accept(movementAttribute, movementModifier);
            }

        }
    }
}