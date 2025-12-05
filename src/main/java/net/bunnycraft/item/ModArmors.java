package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.armor.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


import java.util.List;

public class ModArmors {
    private static final Identifier MOVEMENT_SPEED_ID = Identifier.ofVanilla("generic_movement_speed");

    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            new SteelArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(42)),-0.03f));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            new SteelArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(42)), -0.12f));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            new SteelArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(42)),-0.06f));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            new SteelArmorItem(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(42)),-0.03f));

    public static final Item TURTLE_CHESTPLATE = registerItem("turtle_chestplate",
            new TurtleArmorItem(ModArmorMaterials.MOD_TURTLE_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(25)),0.5f));
    public static final Item TURTLE_LEGGINGS = registerItem("turtle_leggings",
            new TurtleArmorItem(ModArmorMaterials.MOD_TURTLE_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(25)),0.3f));
    public static final Item TURTLE_BOOTS = registerItem("turtle_boots",
            new TurtleArmorItem(ModArmorMaterials.MOD_TURTLE_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(25)),0.2f));

    public static final Item COPPER_HELMET = registerItem("copper_helmet",
            new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate",
            new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(12))));
    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings",
            new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(12))));
    public static final Item COPPER_BOOTS = registerItem("copper_boots",
            new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));

    public static final Item ARMADILLO_HELMET = registerItem("armadillo_helmet",
            new ArmadilloArmorItem(ModArmorMaterials.ARMADILLO_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20)),-0.03f));
    public static final Item ARMADILLO_CHESTPLATE = registerItem("armadillo_chestplate",
            new ArmadilloArmorItem(ModArmorMaterials.ARMADILLO_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20)),-0.08f));
    public static final Item ARMADILLO_LEGGINGS = registerItem("armadillo_leggings",
            new ArmadilloArmorItem(ModArmorMaterials.ARMADILLO_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20)),-0.06f));
    public static final Item ARMADILLO_BOOTS = registerItem("armadillo_boots",
            new ArmadilloArmorItem(ModArmorMaterials.ARMADILLO_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20)),-0.03f));

    public static final Item ROSE_GOLD_HELMET = registerItem("rose_gold_helmet",
            new RoseGoldArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(16)),2f));
    public static final Item ROSE_GOLD_CHESTPLATE = registerItem("rose_gold_chestplate",
            new RoseGoldArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16)),5f));
    public static final Item ROSE_GOLD_LEGGINGS = registerItem("rose_gold_leggings",
            new RoseGoldArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(16)),3f));
    public static final Item ROSE_GOLD_BOOTS = registerItem("rose_gold_boots",
            new RoseGoldArmorItem(ModArmorMaterials.ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(16)),2f));

    public static final Item GUARDIAN_HELMET = registerItem("guardian_helmet",
            new GuardianArmorItem(ModArmorMaterials.GUARDIAN_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(28)),
                    0,5,0,0));
    public static final Item GUARDIAN_CHESTPLATE = registerItem("guardian_chestplate",
            new GuardianArmorItem(ModArmorMaterials.GUARDIAN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(28)),
                    0,0,0.25f,-0.075f));
    public static final Item GUARDIAN_LEGGINGS = registerItem("guardian_leggings",
            new GuardianArmorItem(ModArmorMaterials.GUARDIAN_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(28)),
                    0.4f,0,0f,-0.025f));
    public static final Item GUARDIAN_BOOTS = registerItem("guardian_boots",
            new GuardianArmorItem(ModArmorMaterials.GUARDIAN_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(28)),
                    0.4f,0,0,-0.005f));

    public static final Item DIVING_HELMET = registerItem("diving_helmet",
            new DivingArmorItem(ModArmorMaterials.DIVING_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(13)),
                    0,10,0,0.0f,0f));
    public static final Item DIVING_CHESTPLATE = registerItem("diving_chestplate",
            new DivingArmorItem(ModArmorMaterials.DIVING_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(13)),
                    0.0f,0,2f,-0.1f,-0.025f));
    public static final Item DIVING_LEGGINGS = registerItem("diving_leggings",
            new DivingArmorItem(ModArmorMaterials.DIVING_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(13)),
                    0.5f,0,0f,-0.05f,-0.025f));
    public static final Item DIVING_BOOTS = registerItem("diving_boots",
            new DivingArmorItem(ModArmorMaterials.DIVING_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(13)),
                    0.5f,0,0,-0.025f,-0.01f));

    public static final Item DEALMAKER = registerItem("dealmaker",
            new ArmorItem(ModArmorMaterials.DEALMAKER_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))) {
                    @Override
                    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                        tooltip.add(Text.translatable("tooltip.bunnycraft.dealmaker"));
                        super.appendTooltip(stack,context,tooltip,type);
                    }
            });

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID,name), item);
    }

    public static void registerModArmors() {

        Bunnycraft.LOGGER.info("Registering Armor for" + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(STEEL_HELMET);
            entries.add(STEEL_CHESTPLATE);
            entries.add(STEEL_LEGGINGS);
            entries.add(STEEL_BOOTS);

            entries.add(COPPER_HELMET);
            entries.add(COPPER_CHESTPLATE);
            entries.add(COPPER_LEGGINGS);
            entries.add(COPPER_BOOTS);

            entries.add(ARMADILLO_HELMET);
            entries.add(ARMADILLO_CHESTPLATE);
            entries.add(ARMADILLO_LEGGINGS);
            entries.add(ARMADILLO_BOOTS);

            entries.add(ROSE_GOLD_HELMET);
            entries.add(ROSE_GOLD_CHESTPLATE);
            entries.add(ROSE_GOLD_LEGGINGS);
            entries.add(ROSE_GOLD_BOOTS);

            entries.add(GUARDIAN_HELMET);
            entries.add(GUARDIAN_CHESTPLATE);
            entries.add(GUARDIAN_LEGGINGS);
            entries.add(GUARDIAN_BOOTS);

            entries.add(DIVING_HELMET);
            entries.add(DIVING_CHESTPLATE);
            entries.add(DIVING_LEGGINGS);
            entries.add(DIVING_BOOTS);

            entries.add(TURTLE_CHESTPLATE);
            entries.add(TURTLE_LEGGINGS);
            entries.add(TURTLE_BOOTS);

            entries.add(DEALMAKER);
        });
    }
}
