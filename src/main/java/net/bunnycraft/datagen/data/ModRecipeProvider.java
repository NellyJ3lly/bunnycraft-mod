package net.bunnycraft.datagen.data;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.util.BunnycoinCrafting;
import net.bunnycraft.util.recipe.ModArmorRecipes;
import net.bunnycraft.util.recipe.ModToolRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider implements ModToolRecipes, ModArmorRecipes {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    SmithingTransformRecipeJsonBuilder upgradeItem(Item Template, Item PieceToTransmute, Item AddonItem, Item result) {
        return SmithingTransformRecipeJsonBuilder.create(
                Ingredient.ofItems(Template),
                        Ingredient.ofItems(PieceToTransmute),
                        Ingredient.ofItems(AddonItem),
                        RecipeCategory.TOOLS, result)
                .criterion(hasItem(Template), conditionsFromItem(Template));
    }

    // move more blocks here later
    public void generateBlockRecipes(RecipeExporter exporter) {
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.SCULK_WOOD_SLAB,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createStairsRecipe(ModBlocks.SCULK_WOOD_STAIRS,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createDoorRecipe(ModBlocks.SCULK_WOOD_DOOR,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createTrapdoorRecipe(ModBlocks.SCULK_WOOD_TRAPDOOR,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createFenceRecipe(ModBlocks.SCULK_WOOD_FENCE,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createFenceGateRecipe(ModBlocks.SCULK_WOOD_FENCE_GATE,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);
        createPressurePlateRecipe(
                RecipeCategory.REDSTONE,ModBlocks.SCULK_WOOD_PRESSURE_PLATE,Ingredient.ofItems(ModBlocks.SCULK_WOOD_PLANKS))
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,ModBlocks.ECHO_BRICK)
                .input('#', ModBlocks.ECHO_BLOCK)
                .pattern("##")
                .pattern("##")
                .criterion(hasItem(ModBlocks.ECHO_BLOCK),conditionsFromItem(ModBlocks.ECHO_BLOCK)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE,ModBlocks.SCULK_WOOD_BUTTON)
                .input(ModBlocks.SCULK_WOOD_PLANKS)
                .criterion(hasItem(ModBlocks.SCULK_WOOD_PLANKS),conditionsFromItem(ModBlocks.SCULK_WOOD_PLANKS)).offerTo(exporter);

        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.ECHO_BRICK_SLAB,Ingredient.ofItems(ModBlocks.ECHO_BRICK))
                .criterion(hasItem(ModBlocks.ECHO_BRICK),conditionsFromItem(ModBlocks.ECHO_BRICK)).offerTo(exporter);
        createStairsRecipe(ModBlocks.ECHO_BRICK_STAIRS,Ingredient.ofItems(ModBlocks.ECHO_BRICK))
                .criterion(hasItem(ModBlocks.ECHO_BRICK),conditionsFromItem(ModBlocks.ECHO_BRICK)).offerTo(exporter);
        createChiseledBlockRecipe(RecipeCategory.BUILDING_BLOCKS,ModBlocks.CHISELED_ECHO_BRICK,Ingredient.ofItems(ModBlocks.ECHO_BRICK_SLAB))
                .criterion(hasItem(ModBlocks.ECHO_BRICK),conditionsFromItem(ModBlocks.ECHO_BRICK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,ModBlocks.ECHO_BRICK_WALL,6)
                .input('#', ModBlocks.ECHO_BRICK)
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(ModBlocks.ECHO_BRICK),conditionsFromItem(ModBlocks.ECHO_BRICK)).offerTo(exporter);

    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PANCAKE_RABBIT, RecipeCategory.DECORATIONS, ModBlocks.PANCAKE_RABBIT_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.COPPER_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.COPPER_BUNNYCOIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.GOLD_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.GOLD_BUNNYCOIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.DIAMOND_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.PIPIS, RecipeCategory.DECORATIONS, ModBlocks.PIPIS_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.MS_PIPIS, RecipeCategory.DECORATIONS, ModBlocks.MS_PIPIS_BLOCK);


        BunnycoinCrafting.makeCoin(exporter,"cent_copper_conversion",ModItems.COPPER_BUNNYCOIN,ModItems.BUNNYCENT);
        BunnycoinCrafting.makeCoin(exporter,"copper_gold_conversion",ModItems.GOLD_BUNNYCOIN,ModItems.COPPER_BUNNYCOIN);
        BunnycoinCrafting.makeCoin(exporter,"gold_diamond_conversion",ModItems.DIAMOND_BUNNYCOIN,ModItems.GOLD_BUNNYCOIN);

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.ROSE_GOLD_INGOT, RecipeCategory.DECORATIONS, ModBlocks.ROSE_GOLD_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_INGOT, RecipeCategory.DECORATIONS, ModBlocks.STEEL_BLOCK);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS,ModBlocks.SCULK_WOOD_PLANKS,4)
                .input(ModBlocks.SCULK_WOOD_LOG)
                .criterion(hasItem(ModBlocks.SCULK_WOOD_LOG),conditionsFromItem(ModBlocks.SCULK_WOOD_LOG)).offerTo(exporter);

        generateBlockRecipes(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MOLD, 8)
                .pattern("OOO")
                .input('O', Items.CLAY_BALL)
                .criterion(hasItem(Items.CAULDRON), conditionsFromItem(Items.CAULDRON)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModTools.WOODEN_CANE, 1)
                .pattern(" S ")
                .pattern(" W ")
                .pattern(" S ")
                .input('W', Items.OAK_PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModTools.CLIMBING_CLAW, 1)
                .pattern("I I")
                .pattern("LLL")
                .input('I', Items.IRON_INGOT)
                .input('L', Items.LEATHER)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_BOOK, 1)
                .pattern("###")
                .pattern("#BG")
                .pattern("LG ")
                .input('#', Items.AMETHYST_SHARD)
                .input('B', Items.BOOK)
                .input('G', Items.GOLD_INGOT)
                .input('L',Items.LAPIS_LAZULI)
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModTools.ECHOLOCATOR, 1)
                .pattern("#")
                .pattern("R")
                .pattern("N")
                .input('#', Blocks.CALIBRATED_SCULK_SENSOR)
                .input('R', Blocks.COMPARATOR)
                .input('N', Items.NETHERITE_INGOT)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ROSE_GOLD_BLOCK)
                        .pattern("XXX")
                        .pattern("XXX")
                        .pattern("XXX")
                        .input('X', ModItems.MOLTEN_ROSE_GOLD)
                        .criterion(hasItem(ModItems.MOLTEN_ROSE_GOLD), conditionsFromItem(ModItems.MOLTEN_ROSE_GOLD))
                        .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "rose_gold_molten_compacting"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VAULT_REWINDER)
                .pattern(" X ")
                .pattern("CIC")
                .pattern(" X ")
                .input('X', Items.REDSTONE)
                .input('C', Items.COPPER_INGOT)
                .input('I', Items.TRIAL_KEY)
                .criterion(hasItem(Items.TRIAL_KEY), conditionsFromItem(Items.TRIAL_KEY))
                .offerTo(exporter);


        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.STEEL_BLOCK)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', ModItems.MOLTEN_STEEL)
                .criterion(hasItem(ModItems.MOLTEN_STEEL), conditionsFromItem(ModItems.MOLTEN_STEEL))
                .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "steel_molten_compacting"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Blocks.NETHERITE_BLOCK)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', ModItems.MOLTEN_NETHERITE)
                .criterion(hasItem(ModItems.MOLTEN_NETHERITE), conditionsFromItem(ModItems.MOLTEN_NETHERITE))
                .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "netherite_molten_compacting"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.TRIDENT)
                .pattern("-O-")
                .pattern(" / ")
                .pattern(" / ")
                .input('-', Items.IRON_INGOT)
                .input('/', Items.PRISMARINE_SHARD)
                .input('O', Items.HEART_OF_THE_SEA)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_UPGRADE_TEMPLATE,2)
                .pattern("-T-")
                .pattern("-S-")
                .pattern("---")
                .input('-', Items.DIAMOND)
                .input('S', Items.DEEPSLATE)
                .input('T',ModItems.STEEL_UPGRADE_TEMPLATE)
                .criterion(hasItem(ModItems.STEEL_UPGRADE_TEMPLATE), conditionsFromItem(ModItems.STEEL_UPGRADE_TEMPLATE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.POINT_LIGHT_MUSIC_DISC,1)
                .pattern("ILR")
                .pattern("CSG")
                .pattern("BED")
                .input('I', Items.IRON_INGOT)
                .input('L', Items.LAPIS_LAZULI)
                .input('R',Items.REDSTONE)
                .input('C',Items.COPPER_INGOT)
                .input('S',Items.GLOW_INK_SAC)
                .input('G',Items.GOLD_INGOT)
                .input('B',Items.COAL)
                .input('E',Items.EMERALD)
                .input('D',Items.DIAMOND)
                .criterion(hasItem(ModItems.POINT_LIGHT_MUSIC_DISC), conditionsFromItem(ModItems.POINT_LIGHT_MUSIC_DISC)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT)
                        .input(ModItems.MOLTEN_ROSE_GOLD)
                        .input(ModItems.MOLD)
                        .criterion(hasItem(ModItems.MOLTEN_ROSE_GOLD), conditionsFromItem(ModItems.MOLTEN_ROSE_GOLD))
                        .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "rose_gold_ingot_casting"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STEEL_INGOT)
                .input(ModItems.MOLTEN_STEEL)
                .input(ModItems.MOLD)
                .criterion(hasItem(ModItems.MOLTEN_STEEL), conditionsFromItem(ModItems.MOLTEN_STEEL))
                .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "steel_ingot_casting"));

        makePickaxe(ModTools.COPPER_PICKAXE,Items.COPPER_INGOT).offerTo(exporter);
        makeSword(ModTools.COPPER_SWORD,Items.COPPER_INGOT).offerTo(exporter);
        makeAxe(ModTools.COPPER_AXE,Items.COPPER_INGOT).offerTo(exporter);
        makeShovel(ModTools.COPPER_SHOVEL,Items.COPPER_INGOT).offerTo(exporter);
        makeHoe(ModTools.COPPER_HOE,Items.COPPER_INGOT).offerTo(exporter);

        makePickaxe(ModTools.PRISMARINE_PICKAXE,Items.PRISMARINE).offerTo(exporter);
        makeSword(ModTools.PRISMARINE_SWORD,Items.PRISMARINE).offerTo(exporter);
        makeAxe(ModTools.PRISMARINE_AXE,Items.PRISMARINE).offerTo(exporter);
        makeShovel(ModTools.PRISMARINE_SHOVEL,Items.PRISMARINE).offerTo(exporter);
        makeHoe(ModTools.PRISMARINE_HOE,Items.PRISMARINE).offerTo(exporter);

        makePickaxe(ModTools.ROSE_GOLD_PICKAXE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeSword(ModTools.ROSE_GOLD_SWORD,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeAxe(ModTools.ROSE_GOLD_AXE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeShovel(ModTools.ROSE_GOLD_SHOVEL,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeHoe(ModTools.ROSE_GOLD_HOE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeSpear(ModTools.WOODEN_SPEAR, "minecraft", "planks", Items.STICK).offerTo(exporter);
        makeSpear(ModTools.STONE_SPEAR, "minecraft", "stone_tool_materials", Items.COBBLESTONE).offerTo(exporter);
        makeSpear(ModTools.COPPER_SPEAR, Items.COPPER_INGOT).offerTo(exporter);
        makeSpear(ModTools.IRON_SPEAR, Items.IRON_INGOT).offerTo(exporter);
        makeSpear(ModTools.GOLDEN_SPEAR, Items.GOLD_INGOT).offerTo(exporter);
        makeSpear(ModTools.DIAMOND_SPEAR, Items.DIAMOND).offerTo(exporter);
        upgradeToNetherite(ModTools.DIAMOND_SPEAR, ModTools.NETHERITE_SPEAR).offerTo(exporter, getItemPath(ModTools.NETHERITE_SPEAR) + "_smithing");
        makeSpear(ModTools.ROSE_GOLD_SPEAR, ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        offerSmithingTrimRecipe(exporter, ModItems.PANCAKE_RABBIT, Identifier.of(Bunnycraft.MOD_ID,"rabbit"));

        makeHelmet(ModArmors.DEALMAKER,ModItems.PIPIS).offerTo(exporter);

        makeHelmet(ModArmors.COPPER_HELMET,Items.COPPER_INGOT).offerTo(exporter);
        makeChestplate(ModArmors.COPPER_CHESTPLATE,Items.COPPER_INGOT).offerTo(exporter);
        makeLeggings(ModArmors.COPPER_LEGGINGS,Items.COPPER_INGOT).offerTo(exporter);
        makeBoots(ModArmors.COPPER_BOOTS,Items.COPPER_INGOT).offerTo(exporter);

        makeChestplate(ModArmors.TURTLE_CHESTPLATE,Items.TURTLE_SCUTE).offerTo(exporter);
        makeLeggings(ModArmors.TURTLE_LEGGINGS,Items.TURTLE_SCUTE).offerTo(exporter);
        makeBoots(ModArmors.TURTLE_BOOTS,Items.TURTLE_SCUTE).offerTo(exporter);

        makeHelmet(ModArmors.ROSE_GOLD_HELMET,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeChestplate(ModArmors.ROSE_GOLD_CHESTPLATE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeLeggings(ModArmors.ROSE_GOLD_LEGGINGS,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);
        makeBoots(ModArmors.ROSE_GOLD_BOOTS,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeHelmet(ModArmors.ARMADILLO_HELMET,Items.ARMADILLO_SCUTE).offerTo(exporter);
        makeChestplate(ModArmors.ARMADILLO_CHESTPLATE,Items.ARMADILLO_SCUTE).offerTo(exporter);
        makeLeggings(ModArmors.ARMADILLO_LEGGINGS,Items.ARMADILLO_SCUTE).offerTo(exporter);
        makeBoots(ModArmors.ARMADILLO_BOOTS,Items.ARMADILLO_SCUTE).offerTo(exporter);

        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_HELMET,ModItems.STEEL_INGOT,ModArmors.STEEL_HELMET).offerTo(exporter,"iron_helmet_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_CHESTPLATE,ModItems.STEEL_INGOT,ModArmors.STEEL_CHESTPLATE).offerTo(exporter,"iron_chestplate_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_LEGGINGS,ModItems.STEEL_INGOT,ModArmors.STEEL_LEGGINGS).offerTo(exporter,"iron_leggings_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_BOOTS,ModItems.STEEL_INGOT,ModArmors.STEEL_BOOTS).offerTo(exporter,"iron_boots_to_steel");

        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_PICKAXE,ModItems.STEEL_INGOT,ModTools.STEEL_PICKAXE).offerTo(exporter,"iron_pickaxe_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_SWORD,ModItems.STEEL_INGOT,ModTools.STEEL_SWORD).offerTo(exporter,"iron_sword_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_AXE,ModItems.STEEL_INGOT,ModTools.STEEL_AXE).offerTo(exporter,"iron_axe_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_SHOVEL,ModItems.STEEL_INGOT,ModTools.STEEL_SHOVEL).offerTo(exporter,"iron_shovel_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.IRON_HOE,ModItems.STEEL_INGOT,ModTools.STEEL_HOE).offerTo(exporter,"iron_hoe_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,ModTools.IRON_SPEAR,ModItems.STEEL_INGOT,ModTools.STEEL_SPEAR).offerTo(exporter,"iron_spear_to_steel");
        upgradeItem(ModItems.STEEL_UPGRADE_TEMPLATE,Items.SHEARS,ModItems.STEEL_INGOT,ModTools.STEEL_SHEARS).offerTo(exporter,"iron_shear_to_steel");

//        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ENCHANTING_STAND)
//                .pattern(" B ")
//                .pattern("GDG")
//                .pattern(" D ")
//                .input('B', Items.BOOK)
//                .input('D', Items.DEEPSLATE)
//                .input('G', Items.BOOK)
//                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
//                .offerTo(exporter);

        /// Guardian Armor

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.GUARDIAN_HELMET)
                .pattern("/S/")
                .pattern("/ /")
                .input('/', Items.PRISMARINE_SHARD).input('S', Items.PRISMARINE_CRYSTALS)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.GUARDIAN_CHESTPLATE)
                .pattern("O O")
                .pattern("/H/")
                .pattern("///")
                .input('O', Items.NAUTILUS_SHELL).input('H', Items.HEART_OF_THE_SEA).input('/', Items.PRISMARINE_SHARD)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.GUARDIAN_LEGGINGS)
                .pattern("///")
                .pattern("/ /")
                .pattern("/ /")
                .input('/', Items.PRISMARINE_SHARD)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.GUARDIAN_BOOTS)
                .pattern("/ /")
                .pattern("/ /")
                .input('/', Items.PRISMARINE_SHARD)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE)).offerTo(exporter);


        /// Diving Armor

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.DIVING_HELMET)
                .pattern("/R/")
                .pattern("/G/")
                .pattern("/R/")
                .input('R', Items.REDSTONE).input('G', Items.GLASS).input('/', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.DIVING_CHESTPLATE)
                .pattern("L L")
                .pattern("/R/")
                .pattern("L/L")
                .input('R', Items.REDSTONE).input('L', Items.LEATHER).input('/', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.DIVING_LEGGINGS)
                .pattern("LLL")
                .pattern("/ /")
                .pattern("L L")
                .input('/', Items.COPPER_INGOT).input('L', Items.LEATHER)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModArmors.DIVING_BOOTS)
                .pattern("L L")
                .pattern("/ /")
                .input('/', Items.COPPER_INGOT).input('L', Items.LEATHER)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(exporter);
    }
}