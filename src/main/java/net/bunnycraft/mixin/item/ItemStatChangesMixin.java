package net.bunnycraft.mixin.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static net.minecraft.item.Items.register;

@Mixin(Items.class)
public class ItemStatChangesMixin {
    @Unique
    private static final Item SHEARS;
    @Unique
    private static final Item WOODEN_PICKAXE;
    @Unique
    private static final Item STONE_PICKAXE;
    @Unique
    private static final Item GOLDEN_PICKAXE;
    @Unique
    private static final Item IRON_PICKAXE;
    @Unique
    private static final Item DIAMOND_PICKAXE;
    @Unique
    private static final Item NETHERITE_PICKAXE;

    static {
        SHEARS = register((String) "shears", new ShearsItem((new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 1, -0.4F)).maxDamage(238).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())));
        WOODEN_PICKAXE = register((String) "wooden_pickaxe", new PickaxeItem(ToolMaterials.WOOD, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.WOOD, 5.0F, -3F))));
        STONE_PICKAXE = register((String) "stone_pickaxe", new PickaxeItem(ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.STONE, 6.0F, -3F))));
        GOLDEN_PICKAXE = register((String) "golden_pickaxe", new PickaxeItem(ToolMaterials.GOLD, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.GOLD, 5.0F, -2.8F))));
        IRON_PICKAXE = register((String) "iron_pickaxe", new PickaxeItem(ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.IRON, 5.0F, -2.9F))));
        DIAMOND_PICKAXE = register((String) "diamond_pickaxe", new PickaxeItem(ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 3.0F, -2.8F))));
        NETHERITE_PICKAXE = register((String)"netherite_pickaxe", new PickaxeItem(ToolMaterials.NETHERITE, (new Item.Settings()).fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4.0F, -2.8F))));
    }
}
