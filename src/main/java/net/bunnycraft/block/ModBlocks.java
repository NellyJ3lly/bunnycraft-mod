package net.bunnycraft.block;

import net.bunnycraft.Bunnycraft;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block PANCAKE_RABBIT_ORE = registerBlock("pancake_rabbit_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PANCAKE_RABBIT_BLOCK = registerBlock("pancake_rabbit_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.STONE)));

    public static final Block PIPIS_BLOCK = registerBlock("pipis_block",
            new PipisBlock(AbstractBlock.Settings.create().strength(0f,1200f).nonOpaque().sounds(BlockSoundGroup.STONE)));

    public static final Block MS_PIPIS_BLOCK = registerBlock("ms_pipis_block",
            new PipisBlock(AbstractBlock.Settings.create().strength(0f,1200f)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.STONE)));


    // we will need to change the strengths and actual stuff to mine these blocks
    // for now I'm leaving it as is to focus on other things
    public static final Block COPPER_BUNNYCOIN_BLOCK = registerBlock("copper_bunnycoin_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.CHAIN)));
    public static final Block GOLD_BUNNYCOIN_BLOCK = registerBlock("gold_bunnycoin_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.CHAIN)));
    public static final Block DIAMOND_BUNNYCOIN_BLOCK = registerBlock("diamond_bunnycoin_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.CHAIN)));

    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.METAL)));

    public static final Block ROSE_GOLD_BLOCK = registerBlock("rose_gold_block",
            new Block(AbstractBlock.Settings.create().strength(1f).requiresTool().sounds(BlockSoundGroup.METAL)));

    public static final Block ENCHANTING_STAND = registerBlock("enchanting_stand",
            new EnchantingStand(AbstractBlock.Settings.create().strength(3, 6).requiresTool().sounds(BlockSoundGroup.DEEPSLATE).nonOpaque()));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Bunnycraft.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Bunnycraft.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Bunnycraft.LOGGER.info("Registering Mod Blocks for " + Bunnycraft.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.PANCAKE_RABBIT_ORE);
            entries.add(ModBlocks.PANCAKE_RABBIT_BLOCK);
            entries.add(ModBlocks.COPPER_BUNNYCOIN_BLOCK);
            entries.add(ModBlocks.GOLD_BUNNYCOIN_BLOCK);
            entries.add(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);

            entries.add(ModBlocks.ROSE_GOLD_BLOCK);
            entries.add(ModBlocks.STEEL_BLOCK);

            entries.add(ModBlocks.ENCHANTING_STAND);
        });
    }
}
