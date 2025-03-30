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
                        entries.add(ModItems.ROSEGOLD_INGOT);
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.PANCAKE_RABBIT);

                        entries.add(ModBlocks.PANCAKE_RABBIT_BLOCK);
                        entries.add(ModBlocks.PANCAKE_RABBIT_ORE);

                        entries.add(ModTools.COPPER_PICKAXE);
                        entries.add(ModTools.COPPER_SWORD);
                        entries.add(ModTools.COPPER_AXE);
                        entries.add(ModTools.COPPER_HOE);
                        entries.add(ModTools.COPPER_SHOVEL);

                        entries.add(ModTools.STEEL_PICKAXE);
                        entries.add(ModTools.STEEL_SWORD);
                        entries.add(ModTools.STEEL_AXE);
                        entries.add(ModTools.STEEL_HOE);
                        entries.add(ModTools.STEEL_SHOVEL);

                    }).build());

    public static void registerItemGroups() {
        Bunnycraft.LOGGER.info("Registering Item Groups for" + Bunnycraft.MOD_ID);
    }
}