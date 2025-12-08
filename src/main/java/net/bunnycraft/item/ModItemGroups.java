package net.bunnycraft.item;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup BunnycraftItems = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Bunnycraft.MOD_ID,"bunnycraft"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.PANCAKE_RABBIT))
                    .displayName(Text.translatable("itemgroup.bunnycraft.group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MOLTEN_ROSE_GOLD);
                        entries.add(ModItems.ROSE_GOLD_INGOT);
                        entries.add(ModItems.MOLTEN_STEEL);
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.STEEL_UPGRADE_TEMPLATE);
                        entries.add(ModItems.ECHO_INGOT);

                        entries.add(ModItems.MOLD);
                        entries.add(ModTools.CLIMBING_CLAW);
                        entries.add(ModTools.WOODEN_CANE);
                        entries.add(ModItems.VAULT_REWINDER);
                        entries.add(ModItems.AMETHYST_BOOK);
                        entries.add(ModTools.ECHOLOCATOR);

                        entries.add(ModTools.WOODEN_SPEAR);
                        entries.add(ModTools.STONE_SPEAR);
                        entries.add(ModTools.COPPER_SPEAR);
                        entries.add(ModTools.IRON_SPEAR);
                        entries.add(ModTools.GOLDEN_SPEAR);
                        entries.add(ModTools.DIAMOND_SPEAR);
                        entries.add(ModTools.NETHERITE_SPEAR);

                        entries.add(ModTools.COPPER_PICKAXE);
                        entries.add(ModTools.COPPER_SWORD);
                        entries.add(ModTools.COPPER_AXE);
                        entries.add(ModTools.COPPER_HOE);
                        entries.add(ModTools.COPPER_SHOVEL);

                        entries.add(ModTools.ROSE_GOLD_PICKAXE);
                        entries.add(ModTools.ROSE_GOLD_SWORD);
                        entries.add(ModTools.ROSE_GOLD_AXE);
                        entries.add(ModTools.ROSE_GOLD_HOE);
                        entries.add(ModTools.ROSE_GOLD_SHOVEL);
                        entries.add(ModTools.ROSE_GOLD_SPEAR);

                        entries.add(ModTools.STEEL_PICKAXE);
                        entries.add(ModTools.STEEL_SWORD);
                        entries.add(ModTools.STEEL_AXE);
                        entries.add(ModTools.STEEL_HOE);
                        entries.add(ModTools.STEEL_SHOVEL);
                        entries.add(ModTools.STEEL_SHEARS);
                        entries.add(ModTools.STEEL_SPEAR);

                        entries.add(ModTools.PRISMARINE_PICKAXE);
                        entries.add(ModTools.PRISMARINE_SWORD);
                        entries.add(ModTools.PRISMARINE_AXE);
                        entries.add(ModTools.PRISMARINE_HOE);
                        entries.add(ModTools.PRISMARINE_SHOVEL);

                        entries.add(ModArmors.ROSE_GOLD_HELMET);
                        entries.add(ModArmors.ROSE_GOLD_CHESTPLATE);
                        entries.add(ModArmors.ROSE_GOLD_LEGGINGS);
                        entries.add(ModArmors.ROSE_GOLD_BOOTS);

                        entries.add(ModArmors.COPPER_HELMET);
                        entries.add(ModArmors.COPPER_CHESTPLATE);
                        entries.add(ModArmors.COPPER_LEGGINGS);
                        entries.add(ModArmors.COPPER_BOOTS);

                        entries.add(ModArmors.STEEL_HELMET);
                        entries.add(ModArmors.STEEL_CHESTPLATE);
                        entries.add(ModArmors.STEEL_LEGGINGS);
                        entries.add(ModArmors.STEEL_BOOTS);

                        entries.add(ModArmors.ARMADILLO_HELMET);
                        entries.add(ModArmors.ARMADILLO_CHESTPLATE);
                        entries.add(ModArmors.ARMADILLO_LEGGINGS);
                        entries.add(ModArmors.ARMADILLO_BOOTS);

                        entries.add(ModArmors.TURTLE_CHESTPLATE);
                        entries.add(ModArmors.TURTLE_LEGGINGS);
                        entries.add(ModArmors.TURTLE_BOOTS);

                        entries.add(ModArmors.DIVING_HELMET);
                        entries.add(ModArmors.DIVING_CHESTPLATE);
                        entries.add(ModArmors.DIVING_LEGGINGS);
                        entries.add(ModArmors.DIVING_BOOTS);

                        entries.add(ModArmors.GUARDIAN_HELMET);
                        entries.add(ModArmors.GUARDIAN_CHESTPLATE);
                        entries.add(ModArmors.GUARDIAN_LEGGINGS);
                        entries.add(ModArmors.GUARDIAN_BOOTS);

                        entries.add(ModBlocks.ROSE_GOLD_BLOCK);
                        entries.add(ModBlocks.STEEL_BLOCK);

                        entries.add(ModBlocks.ECHO_BLOCK);
                        entries.add(ModBlocks.BUDDING_ECHO);
                        entries.add(ModBlocks.MEDIUM_ECHO_BUD);
                        entries.add(ModBlocks.SMALL_ECHO_BUD);
                        entries.add(ModBlocks.LARGE_ECHO_BUD);
                        entries.add(ModBlocks.ECHO_CLUSTER);
                        entries.add(ModBlocks.ECHO_BRICK);
                        entries.add(ModBlocks.ECHO_BRICK_STAIRS);
                        entries.add(ModBlocks.ECHO_BRICK_SLAB);
                        entries.add(ModBlocks.ECHO_BRICK_WALL);
                        entries.add(ModBlocks.CHISELED_ECHO_BRICK);

                        entries.add(ModBlocks.SCULK_WOOD_SAPLING);
                        entries.add(ModBlocks.SCULK_WOOD_LOG);
                        entries.add(ModBlocks.STRIPPED_SCULK_WOOD_LOG);
                        entries.add(ModBlocks.SCULK_WOOD_PLANKS);
                        entries.add(ModBlocks.SCULK_WOOD_DOOR);
                        entries.add(ModBlocks.SCULK_WOOD_TRAPDOOR);
                        entries.add(ModBlocks.SCULK_WOOD_STAIRS);
                        entries.add(ModBlocks.SCULK_WOOD_SLAB);
                        entries.add(ModBlocks.SCULK_WOOD_BUTTON);
                        entries.add(ModBlocks.SCULK_WOOD_PRESSURE_PLATE);
                        entries.add(ModBlocks.SCULK_WOOD_FENCE);
                        entries.add(ModBlocks.SCULK_WOOD_FENCE_GATE);

                        entries.add(ModItems.BUNNYCENT);
                        entries.add(ModItems.COPPER_BUNNYCOIN);
                        entries.add(ModItems.GOLD_BUNNYCOIN);
                        entries.add(ModItems.DIAMOND_BUNNYCOIN);

                        entries.add(ModItems.PANCAKE_RABBIT);

                        entries.add(ModItems.POINT_LIGHT_MUSIC_DISC);
                        entries.add(ModItems.TRUE);

                        entries.add(ModBlocks.COPPER_BUNNYCOIN_BLOCK);
                        entries.add(ModBlocks.GOLD_BUNNYCOIN_BLOCK);
                        entries.add(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);
                        entries.add(ModBlocks.PANCAKE_RABBIT_BLOCK);
                        entries.add(ModBlocks.PANCAKE_RABBIT_ORE);
                        entries.add(ModBlocks.ENCHANTING_STAND);
                    }).build());

    public static void registerItemGroups() {
        Bunnycraft.LOGGER.info("Registering Item Groups for" + Bunnycraft.MOD_ID);
    }
}