package net.bunnycraft.mixin.item.attribute;


import net.bunnycraft.Bunnycraft;
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
public abstract class ArmorAttributesMixin {

    public EntityAttributeModifier Attribute(Item item,String id, Float Value, EntityAttributeModifier.Operation Operation) {
        EntityAttributeModifier Modifier = new EntityAttributeModifier(
                Identifier.of(Bunnycraft.MOD_ID, item.getTranslationKey() + id), //ensures a unique identifier for each piece
                Value,
                Operation
        );

        return Modifier;
    };

    @Shadow
    public abstract Item getItem();

    @Inject(
            method = "applyAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
            at = @At("TAIL")
    )
    private void addCustomAttributes(EquipmentSlot slot,
            BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> consumer,
            CallbackInfo ci) {

        Item item = this.getItem();

        //makes steel armor slow you down
        if (item instanceof ArmorItem armor && slot.isArmorSlot()) {

            //gets the material name of the armor piece
            String material = armor.getMaterial().getIdAsString();

            if(material.contains("steel")) {
                RegistryEntry<EntityAttribute> movementAttribute = EntityAttributes.GENERIC_MOVEMENT_SPEED;

                EntityAttributeModifier SteelModifer =
                        Attribute(armor,"movement",-0.07f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

                consumer.accept(movementAttribute, SteelModifer);
            }

            // TURTLE
            if(material.contains("turtle")) {
                RegistryEntry<EntityAttribute> TurtlemovementAttribute = EntityAttributes.GENERIC_MOVEMENT_SPEED;
                EntityAttributeModifier TurtleMovement =
                        Attribute(armor,"movement",-0.05f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

                if (slot == EquipmentSlot.HEAD) {
                    RegistryEntry<EntityAttribute> OxygenAttribute = EntityAttributes.GENERIC_OXYGEN_BONUS;

                    EntityAttributeModifier OxygenModifier =
                            Attribute(armor,"oxygen_bonus",1f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(OxygenAttribute, OxygenModifier);
                }

                if (slot == EquipmentSlot.CHEST) {
                    RegistryEntry<EntityAttribute> waterMiningAttribute = EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED;

                    EntityAttributeModifier waterMiningModifier =
                            Attribute(armor,"water_mining_id",2f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(waterMiningAttribute, waterMiningModifier);
                }

                if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
                    RegistryEntry<EntityAttribute> watereffiencyAttribute = EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY;

                    EntityAttributeModifier watereffiencyModifier =
                            Attribute(armor,"water_speed_id",0.5f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(watereffiencyAttribute, watereffiencyModifier);
                }

                consumer.accept(TurtlemovementAttribute, TurtleMovement);
            }

            if(material.contains("guardian")) {
                RegistryEntry<EntityAttribute> GuardianmovementAttribute = EntityAttributes.GENERIC_MOVEMENT_SPEED;
                EntityAttributeModifier GuardianMovement =
                        Attribute(armor,"movement",-0.1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

                if (slot == EquipmentSlot.HEAD) {
                    RegistryEntry<EntityAttribute> OxygenAttribute = EntityAttributes.GENERIC_OXYGEN_BONUS;

                    EntityAttributeModifier OxygenModifier =
                            Attribute(armor,"oxygen_bonus",1f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(OxygenAttribute, OxygenModifier);

                    RegistryEntry<EntityAttribute> AttackAttribute = EntityAttributes.GENERIC_ATTACK_DAMAGE;

                    EntityAttributeModifier AttackModifer =
                            Attribute(armor,"attack_id",0.5f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(AttackAttribute, AttackModifer);
                }

                if (slot == EquipmentSlot.CHEST) {
                    RegistryEntry<EntityAttribute> waterMiningAttribute = EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED;
                    RegistryEntry<EntityAttribute> AttackAttribute = EntityAttributes.GENERIC_ATTACK_DAMAGE;

                    EntityAttributeModifier waterMiningModifier =
                            Attribute(armor,"water_effiency_id",1f, EntityAttributeModifier.Operation.ADD_VALUE);

                    EntityAttributeModifier AttackModifer =
                            Attribute(armor,"attack_id",2f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(waterMiningAttribute, waterMiningModifier);
                    consumer.accept(AttackAttribute, AttackModifer);
                }

                if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
                    RegistryEntry<EntityAttribute> watereffiencyAttribute = EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY;
                    EntityAttributeModifier watereffiencyModifier =
                            Attribute(armor,"water_speed_id",0.25f, EntityAttributeModifier.Operation.ADD_VALUE);

                    RegistryEntry<EntityAttribute> AttackAttribute = EntityAttributes.GENERIC_ATTACK_DAMAGE;

                    EntityAttributeModifier AttackModifer =
                            Attribute(armor,"attack_id",0.25f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(AttackAttribute, AttackModifer);

                    consumer.accept(watereffiencyAttribute, watereffiencyModifier);
                }

                consumer.accept(GuardianmovementAttribute, GuardianMovement);
            }
        }
    }
}