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
                        entries.add(ModItems.ROSE_GOLD_INGOT);
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.PANCAKE_RABBIT);

                        entries.add(ModBlocks.PANCAKE_RABBIT_BLOCK);
                        entries.add(ModBlocks.PANCAKE_RABBIT_ORE);

                        //add pickaxes
                        for(int i = 0; ModTools.pickaxeList.get(i) != null; i++) {
                            entries.add(ModTools.pickaxeList.get(i));
                        }

                        //add swords
                        for(int i = 0; ModTools.swordList.get(i) != null; i++) {
                            entries.add(ModTools.swordList.get(i));
                        }

                        //add spears
                        for(int i = 0; ModTools.spearList.get(i) != null; i++) {
                            entries.add(ModTools.spearList.get(i));
                        }

                        //add axes
                        for(int i = 0; ModTools.axeList.get(i) != null; i++) {
                            entries.add(ModTools.axeList.get(i));
                        }

                        //add shovels
                        for(int i = 0; ModTools.shovelList.get(i) != null; i++) {
                            entries.add(ModTools.shovelList.get(i));
                        }

                        //add hoes
                        for(int i = 0; ModTools.hoeList.get(i) != null; i++) {
                            entries.add(ModTools.hoeList.get(i));
                        }

                    }).build());

    public static void registerItemGroups() {
        Bunnycraft.LOGGER.info("Registering Item Groups for" + Bunnycraft.MOD_ID);
    }
}