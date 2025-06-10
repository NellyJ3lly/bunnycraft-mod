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

    @Unique
    private static final Item WOODEN_SHOVEL;
    @Unique
    private static final Item STONE_SHOVEL;
    @Unique
    private static final Item GOLDEN_SHOVEL;
    @Unique
    private static final Item IRON_SHOVEL;
    @Unique
    private static final Item DIAMOND_SHOVEL;
    @Unique
    private static final Item NETHERITE_SHOVEL;

    @Unique
    private static final Item WOODEN_HOE;
    @Unique
    private static final Item STONE_HOE;
    @Unique
    private static final Item GOLDEN_HOE;
    @Unique
    private static final Item IRON_HOE;
    @Unique
    private static final Item DIAMOND_HOE;
    @Unique
    private static final Item NETHERITE_HOE;

    static {
        SHEARS = register((String) "shears", new ShearsItem((new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 1, -0.4F)).maxDamage(238).component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())));
        // Pickaxes
        WOODEN_PICKAXE = register((String) "wooden_pickaxe", new PickaxeItem(ToolMaterials.WOOD, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.WOOD, 5.0F, -3F))));
        STONE_PICKAXE = register((String) "stone_pickaxe", new PickaxeItem(ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.STONE, 6.0F, -3F))));
        GOLDEN_PICKAXE = register((String) "golden_pickaxe", new PickaxeItem(ToolMaterials.GOLD, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.GOLD, 5.0F, -2.8F))));
        IRON_PICKAXE = register((String) "iron_pickaxe", new PickaxeItem(ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.IRON, 5.0F, -2.9F))));
        DIAMOND_PICKAXE = register((String) "diamond_pickaxe", new PickaxeItem(ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 3.0F, -2.8F))));
        NETHERITE_PICKAXE = register((String)"netherite_pickaxe", new PickaxeItem(ToolMaterials.NETHERITE, (new Item.Settings()).fireproof().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4.0F, -2.8F))));
        // Shovels
        WOODEN_SHOVEL = register((String)"wooden_shovel", new ShovelItem(ToolMaterials.WOOD, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.WOOD, 3.5F, -3.0F))));
        STONE_SHOVEL = register((String)"stone_shovel", new ShovelItem(ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.STONE, 2.5F, -3.0F))));
        GOLDEN_SHOVEL = register((String)"golden_shovel", new ShovelItem(ToolMaterials.GOLD, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.GOLD, 1.5F, -3.0F))));
        IRON_SHOVEL = register((String)"iron_shovel", new ShovelItem(ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.IRON, 3F, -2.9F))));
        DIAMOND_SHOVEL = register((String)"diamond_shovel", new ShovelItem(ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.DIAMOND, 2.5F, -2.8F))));
        NETHERITE_SHOVEL = register((String)"netherite_shovel", new ShovelItem(ToolMaterials.NETHERITE, (new Item.Settings()).fireproof().attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.NETHERITE, 2.5F, -2.8F))));
        // Hoes
        WOODEN_HOE = register((String)"wooden_hoe", new HoeItem(ToolMaterials.WOOD, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.WOOD, 2.0F, -2.0F))));
        STONE_HOE = register((String)"stone_hoe", new HoeItem(ToolMaterials.STONE, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.STONE, 2.0F, -1.0F))));
        GOLDEN_HOE = register((String)"golden_hoe", new HoeItem(ToolMaterials.GOLD, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.GOLD, 2.0F, -3.0F))));
        IRON_HOE = register((String)"iron_hoe", new HoeItem(ToolMaterials.IRON, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.IRON, -2.0F, -1.0F))));
        DIAMOND_HOE = register((String)"diamond_hoe", new HoeItem(ToolMaterials.DIAMOND, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 0.0F, 0.0F))));
        NETHERITE_HOE = register((String)"netherite_hoe", new HoeItem(ToolMaterials.NETHERITE, (new Item.Settings()).fireproof().attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 0.0F, 0.0F))));
    }
}
