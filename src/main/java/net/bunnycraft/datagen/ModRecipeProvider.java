package net.bunnycraft.datagen;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.armor.ModArmors;
import net.bunnycraft.util.ModArmorRecipes;
import net.bunnycraft.util.ModToolRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
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

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.ROSE_GOLD_INGOT, RecipeCategory.DECORATIONS, ModBlocks.ROSE_GOLD_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_INGOT, RecipeCategory.DECORATIONS, ModBlocks.STEEL_BLOCK);

        //offerCompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROSE_GOLD_BLOCK, ModItems.MOLTEN_ROSE_GOLD); // these havee duplicate recipe identifiers, idk how to fix
        //offerCompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEEL_BLOCK, ModItems.MOLTEN_STEEL);


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModTools.STEEL_SHEARS)
                .pattern(" X")
                .pattern("X ")
                .input('X', ModItems.STEEL_INGOT)
                .criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.TRIDENT)
                .pattern("-O-")
                .pattern(" / ")
                .pattern(" / ")
                .input('-', Items.IRON_INGOT)
                .input('/', Items.PRISMARINE_SHARD)
                .input('O', Items.HEART_OF_THE_SEA)
                .criterion(hasItem(Items.PRISMARINE), conditionsFromItem(Items.PRISMARINE))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT)).offerTo(exporter);

        makeHelmet(ModArmors.DEALMAKER,ModItems.PIPIS).offerTo(exporter);

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
    }
}