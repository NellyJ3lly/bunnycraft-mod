package net.bunnycraft.datagen;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.armor.ModArmors;
import net.bunnycraft.util.BunnycoinCrafting;
import net.bunnycraft.util.ModArmorRecipes;
import net.bunnycraft.util.ModToolRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider implements ModToolRecipes, ModArmorRecipes {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PANCAKE_RABBIT, RecipeCategory.DECORATIONS, ModBlocks.PANCAKE_RABBIT_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.COPPER_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.COPPER_BUNNYCOIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.GOLD_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.GOLD_BUNNYCOIN_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.DIAMOND_BUNNYCOIN, RecipeCategory.DECORATIONS, ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);

        BunnycoinCrafting.makeCoin(exporter,"copper_gold_conversion",ModItems.GOLD_BUNNYCOIN,ModItems.COPPER_BUNNYCOIN);
        BunnycoinCrafting.makeCoin(exporter,"gold_diamond_conversion",ModItems.DIAMOND_BUNNYCOIN,ModItems.GOLD_BUNNYCOIN);


        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.ROSE_GOLD_INGOT, RecipeCategory.DECORATIONS, ModBlocks.ROSE_GOLD_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_INGOT, RecipeCategory.DECORATIONS, ModBlocks.STEEL_BLOCK);

//        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ENCHANTING_STAND)
//                .pattern(" B ")
//                .pattern("GDG")
//                .pattern(" D ")
//                .input('B', Items.BOOK)
//                .input('D', Items.DEEPSLATE)
//                .input('G', Items.BOOK)
//                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
//                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MOLD, 8)
                .pattern("OOO")
                .input('O', Items.CLAY_BALL)
                .criterion(hasItem(Items.CAULDRON), conditionsFromItem(Items.CAULDRON)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModTools.STEEL_SHEARS)
                .pattern(" X")
                .pattern("X ")
                .input('X', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ROSE_GOLD_BLOCK)
                        .pattern("XXX")
                        .pattern("XXX")
                        .pattern("XXX")
                        .input('X', ModItems.MOLTEN_ROSE_GOLD)
                        .criterion(hasItem(ModItems.MOLTEN_ROSE_GOLD), conditionsFromItem(ModItems.MOLTEN_ROSE_GOLD))
                        .offerTo(exporter, Identifier.of(Bunnycraft.MOD_ID, "rose_gold_molten_compacting"));

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

        makePickaxe(ModTools.STEEL_PICKAXE,ModItems.STEEL_INGOT).offerTo(exporter);
        makeSword(ModTools.STEEL_SWORD,ModItems.STEEL_INGOT).offerTo(exporter);
        makeAxe(ModTools.STEEL_AXE,ModItems.STEEL_INGOT).offerTo(exporter);
        makeShovel(ModTools.STEEL_SHOVEL,ModItems.STEEL_INGOT).offerTo(exporter);
        makeHoe(ModTools.STEEL_HOE,ModItems.STEEL_INGOT).offerTo(exporter);

        makeSpear(ModTools.WOODEN_SPEAR, "minecraft", "planks", Items.STICK).offerTo(exporter);
        makeSpear(ModTools.STONE_SPEAR, "minecraft", "stone_tool_materials", Items.COBBLESTONE).offerTo(exporter);
        makeSpear(ModTools.COPPER_SPEAR, Items.COPPER_INGOT).offerTo(exporter);
        makeSpear(ModTools.IRON_SPEAR, Items.IRON_INGOT).offerTo(exporter);
        makeSpear(ModTools.GOLDEN_SPEAR, Items.GOLD_INGOT).offerTo(exporter);
        makeSpear(ModTools.DIAMOND_SPEAR, Items.DIAMOND).offerTo(exporter);
        upgradeToNetherite(ModTools.DIAMOND_SPEAR, ModTools.NETHERITE_SPEAR).offerTo(exporter, getItemPath(ModTools.NETHERITE_SPEAR) + "_smithing");
        makeSpear(ModTools.STEEL_SPEAR, ModItems.STEEL_INGOT).offerTo(exporter);
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

        makeHelmet(ModArmors.STEEL_HELMET,ModItems.STEEL_INGOT).offerTo(exporter);
        makeChestplate(ModArmors.STEEL_CHESTPLATE,ModItems.STEEL_INGOT).offerTo(exporter);
        makeLeggings(ModArmors.STEEL_LEGGINGS,ModItems.STEEL_INGOT).offerTo(exporter);
        makeBoots(ModArmors.STEEL_BOOTS,ModItems.STEEL_INGOT).offerTo(exporter);

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