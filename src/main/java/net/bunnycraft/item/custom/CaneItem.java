package net.bunnycraft.item.custom;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import javax.swing.*;
import java.util.Optional;

public class CaneItem extends ToolItem {
    private static final Identifier MOVEMENT_SPEED_ID = Identifier.ofVanilla("generic_movement_speed");
    private static final Identifier STEP_HEIGHT_ID = Identifier.ofVanilla("generic_step_height");
    private static final Identifier REACH_ID = Identifier.ofVanilla("player_entity_interaction_range");
    private ComponentType<AttributeModifiersComponent> attributeModifiers;

    public CaneItem(ToolMaterials material, Settings settings) {
        super(material, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float movement_speed,float step_height, float attack_damage, float attack_speed, float reach) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(MOVEMENT_SPEED_ID, movement_speed, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        AttributeModifierSlot.HAND
                )
                .add(
                        EntityAttributes.GENERIC_STEP_HEIGHT,
                        new EntityAttributeModifier(STEP_HEIGHT_ID, step_height, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.HAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, attack_damage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attack_speed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(REACH_ID, reach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
