package net.bunnycraft.block;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.world.tree.ModSaplingGenerators;
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

    public static final Block BUNNY_BANK = registerBlock("bunny_bank",
            new BunnyBankBlock(AbstractBlock.Settings.create().strength(0f,1200f)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.METAL)));

    public static final Block SCULK_BATTERY = registerBlock("sculk_battery",
            new SculkBatteryBlock(AbstractBlock.Settings.create()
                    .nonOpaque()
                    .sounds(BlockSoundGroup.METAL).luminance(state -> 6)));

    public static final Block SCULK_BERRY_BUSH = registerBlock(
            "sculk_berry_bush",
            new SculkBerryBushBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DARK_AQUA).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> 3)));

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

    public static final Block BUDDING_ECHO = registerBlock("budding_echo", new BuddingEchoBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLUE).ticksRandomly().strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block ECHO_BLOCK = registerBlock("echo_block", new SculkSpreadableBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block ECHO_BRICK = registerBlock("echo_brick", new SculkSpreadableBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));
    public static final Block ECHO_BRICK_STAIRS = registerBlock("echo_brick_stairs", new StairsBlock(ModBlocks.ECHO_BRICK.getDefaultState(),AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));
    public static final Block ECHO_BRICK_SLAB = registerBlock("echo_brick_slab", new SlabBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));
    public static final Block ECHO_BRICK_WALL = registerBlock("echo_brick_wall", new WallBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));
    public static final Block CHISELED_ECHO_BRICK = registerBlock("chiseled_echo_brick", new SculkSpreadableBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool().sounds(BlockSoundGroup.SCULK_CATALYST)));


    public static final Block ECHO_CLUSTER = registerBlock("echo_cluster",
            new EchoClusterBlock(7.0F, 3.0F,
                    AbstractBlock.Settings.create().solid().nonOpaque().mapColor(MapColor.BLUE).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                            .strength(1.5F).luminance((state) -> 3)
                            .pistonBehavior(PistonBehavior.DESTROY), 4));
    public static final Block LARGE_ECHO_BUD = registerBlock("large_echo_bud", new EchoClusterBlock(5.0F, 3.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance((state) -> 2), 3));
    public static final Block MEDIUM_ECHO_BUD = registerBlock("medium_echo_bud", new EchoClusterBlock(4.0F, 3.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance((state) -> 1), 2));
    public static final Block SMALL_ECHO_BUD = registerBlock("small_echo_bud", new EchoClusterBlock(3.0F, 4.0F,AbstractBlock.Settings.copyShallow(ECHO_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD), 1));

    public static final Block SCULK_WOOD_SAPLING = registerBlock("sculk_wood_sapling", new SculkSaplingBlock(ModSaplingGenerators.SCULKWOOD, AbstractBlock.Settings.create().mapColor(MapColor.BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block SCULK_WOOD_LOG = registerBlock("sculk_wood_log",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block SCULK_WOOD_WOOD = registerBlock("sculk_wood_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_SCULK_WOOD_LOG = registerBlock("stripped_sculk_wood_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_SCULK_WOOD_WOOD = registerBlock("stripped_sculk_wood_wood",new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Block SCULK_WOOD_PLANKS = registerBlock("sculk_wood_planks", new Block(AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

    public static final Block SCULK_WOOD_DOOR = registerBlock("sculk_wood_door", new DoorBlock(BlockSetType.OAK,AbstractBlock.Settings.create().strength(2f).requiresTool().nonOpaque()));
    public static final Block SCULK_WOOD_TRAPDOOR = registerBlock("sculk_wood_trapdoor", new TrapdoorBlock(BlockSetType.OAK,AbstractBlock.Settings.create().strength(2f).requiresTool().nonOpaque()));
    public static final Block SCULK_WOOD_STAIRS = registerBlock("sculk_wood_stairs", new StairsBlock(ModBlocks.SCULK_WOOD_PLANKS.getDefaultState(),AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block SCULK_WOOD_SLAB = registerBlock("sculk_wood_slab", new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block SCULK_WOOD_BUTTON = registerBlock("sculk_wood_button", new ButtonBlock(BlockSetType.OAK,4,AbstractBlock.Settings.create().strength(2f).requiresTool().noCollision()));
    public static final Block SCULK_WOOD_PRESSURE_PLATE = registerBlock("sculk_wood_pressure_plate", new PressurePlateBlock(BlockSetType.OAK,AbstractBlock.Settings.create().strength(2f).requiresTool().noCollision()));
    public static final Block SCULK_WOOD_FENCE = registerBlock("sculk_wood_fence", new FenceBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block SCULK_WOOD_FENCE_GATE = registerBlock("sculk_wood_fence_gate", new FenceGateBlock(WoodType.OAK,AbstractBlock.Settings.create().strength(2f).requiresTool()));

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
        Bunnycraft.LOGGER.info("Registering Blocks for " + Bunnycraft.MOD_ID);

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
