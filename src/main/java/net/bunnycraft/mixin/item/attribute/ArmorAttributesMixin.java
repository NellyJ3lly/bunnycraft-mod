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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;


@Mixin(ItemStack.class)
public abstract class ArmorAttributesMixin {

    @Unique
    public EntityAttributeModifier Attribute(Item item,String id, Float Value, EntityAttributeModifier.Operation Operation) {
        EntityAttributeModifier Modifier = new EntityAttributeModifier(
                Identifier.of(Bunnycraft.MOD_ID, item.getTranslationKey() + id), //ensures a unique identifier for each piece
                Value,
                Operation
        );

        return Modifier;
    }

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

//            // diving suit
//            if(material.contains("diving")) {
//                RegistryEntry<EntityAttribute> movementAttribute = EntityAttributes.GENERIC_MOVEMENT_SPEED;
//
//                EntityAttributeModifier movementModifier =
//                        Attribute(armor,"movement",-0.1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
//
//                consumer.accept(movementAttribute, movementModifier);
//
//                RegistryEntry<EntityAttribute> JumpAttribute = EntityAttributes.GENERIC_JUMP_STRENGTH;
//
//                EntityAttributeModifier JumpModifier =
//                        Attribute(armor,"jump",0.05f, EntityAttributeModifier.Operation.ADD_VALUE);
//
//                consumer.accept(JumpAttribute, JumpModifier);
//
//                RegistryEntry<EntityAttribute> WaterMiningAttribute = EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED;
//                EntityAttributeModifier WaterMiningModifier =
//                        Attribute(armor,"water_mining",0.1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
//
//                consumer.accept(WaterMiningAttribute, WaterMiningModifier);
//
//                RegistryEntry<EntityAttribute> GravityAttribute = EntityAttributes.GENERIC_GRAVITY;
//                EntityAttributeModifier GravityModifer =
//                        Attribute(armor,"gravity",0.03f, EntityAttributeModifier.Operation.ADD_VALUE);
//
//                consumer.accept(GravityAttribute, GravityModifer);
//
//                RegistryEntry<EntityAttribute> WaterMovementAttribute = EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY;
//                EntityAttributeModifier WaterMovementModifier =
//                        Attribute(armor,"water_efficiency",0.3f, EntityAttributeModifier.Operation.ADD_VALUE);
//
//                consumer.accept(WaterMovementAttribute, WaterMovementModifier);
//
//                RegistryEntry<EntityAttribute> OxygenBonusAttribute = EntityAttributes.GENERIC_OXYGEN_BONUS;
//                EntityAttributeModifier OxygenBonusModifier =
//                        Attribute(armor,"oxygen_bonus",2f, EntityAttributeModifier.Operation.ADD_VALUE);
//
//                consumer.accept(OxygenBonusAttribute, OxygenBonusModifier);
//            }

            // TURTLE
            if(material.contains("turtle")) {
                if (slot == EquipmentSlot.HEAD) {
                    RegistryEntry<EntityAttribute> OxygenAttribute = EntityAttributes.GENERIC_OXYGEN_BONUS;

                    EntityAttributeModifier OxygenModifier =
                            Attribute(armor,"oxygen_bonus",1f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(OxygenAttribute, OxygenModifier);
                }

                if (slot == EquipmentSlot.CHEST) {
                    RegistryEntry<EntityAttribute> waterMiningAttribute = EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED;

                    EntityAttributeModifier waterMiningModifier =
                            Attribute(armor,"water_mining_id",0.5f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

                    consumer.accept(waterMiningAttribute, waterMiningModifier);
                }

                if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
                    RegistryEntry<EntityAttribute> watereffiencyAttribute = EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY;

                    EntityAttributeModifier watereffiencyModifier =
                            Attribute(armor,"water_speed_id",0.5f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(watereffiencyAttribute, watereffiencyModifier);
                }
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
                            Attribute(armor,"water_speed_id",0.3f, EntityAttributeModifier.Operation.ADD_VALUE);

                    RegistryEntry<EntityAttribute> AttackAttribute = EntityAttributes.GENERIC_ATTACK_DAMAGE;

                    EntityAttributeModifier AttackModifer =
                            Attribute(armor,"attack_id",0.25f, EntityAttributeModifier.Operation.ADD_VALUE);

                    consumer.accept(AttackAttribute, AttackModifer);

                    consumer.accept(watereffiencyAttribute, watereffiencyModifier);
                }

                consumer.accept(GuardianmovementAttribute, GuardianMovement);
            }

            if(material.contains("rose_gold")) {
                RegistryEntry<EntityAttribute> RosegoldHealthAttribute = EntityAttributes.GENERIC_MAX_HEALTH;
                EntityAttributeModifier HealthBoostModifier =
                        Attribute(armor,"health",2f, EntityAttributeModifier.Operation.ADD_VALUE);

                consumer.accept(RosegoldHealthAttribute, HealthBoostModifier);
            }
        }
    }
}