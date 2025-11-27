package net.bunnycraft.block;

import net.bunnycraft.Bunnycraft;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static net.minecraft.block.Blocks.createLogBlock;

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

    public static final Block BUDDING_ECHO = registerBlock("budding_echo", new BuddingEchoBlock(
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).ticksRandomly().strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block ECHO_CLUSTER = registerBlock("echo_cluster",
            new EchoClusterBlock(7.0F, 3.0F,
                    AbstractBlock.Settings.create().solid().nonOpaque().mapColor(MapColor.BLUE).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                            .strength(1.5F).luminance((state) -> 2)
                            .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_ECHO_BUD = registerBlock("large_echo_bud", new EchoClusterBlock(5.0F, 3.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance((state) -> 4)));
    public static final Block MEDIUM_ECHO_BUD = registerBlock("medium_echo_bud", new EchoClusterBlock(4.0F, 3.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance((state) -> 2)));
    public static final Block SMALL_ECHO_BUD = registerBlock("small_echo_bud", new EchoClusterBlock(3.0F, 4.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD).luminance((state) -> 1)));

    public static final Block SCULK_SAPLING = registerBlock("sculk_sapling", new SaplingBlock(SaplingGenerator.OAK, AbstractBlock.Settings.create().mapColor(MapColor.BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block SCULK_LOG = registerBlock("sculk_log",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block SCULK_WOOD = registerBlock("sculk_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_SCULK_LOG = registerBlock("stripped_sculk_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_SCULK_WOOD = registerBlock("stripped_sculk_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Block SCULK_PLANKS = registerBlock("sculk_planks", new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

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
