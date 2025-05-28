package net.bunnycraft.datagen;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModToolRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider implements ModToolRecipes {
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


        makePickaxe(ModTools.COPPER_PICKAXE,Items.COPPER_INGOT).offerTo(exporter);
        makePickaxe(ModTools.STEEL_PICKAXE,ModItems.STEEL_INGOT).offerTo(exporter);
        makePickaxe(ModTools.ROSE_GOLD_PICKAXE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeSword(ModTools.COPPER_SWORD,Items.COPPER_INGOT).offerTo(exporter);
        makeSword(ModTools.STEEL_SWORD,ModItems.STEEL_INGOT).offerTo(exporter);
        makeSword(ModTools.ROSE_GOLD_SWORD,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeSpear(ModTools.WOODEN_SPEAR, "minecraft", "planks", Items.STICK).offerTo(exporter);
        makeSpear(ModTools.STONE_SPEAR, "minecraft", "stone_tool_materials", Items.COBBLESTONE).offerTo(exporter);
        makeSpear(ModTools.COPPER_SPEAR, Items.COPPER_INGOT).offerTo(exporter);
        makeSpear(ModTools.IRON_SPEAR, Items.IRON_INGOT).offerTo(exporter);
        makeSpear(ModTools.GOLDEN_SPEAR, Items.GOLD_INGOT).offerTo(exporter);
        makeSpear(ModTools.DIAMOND_SPEAR, Items.DIAMOND).offerTo(exporter);
        upgradeToNetherite(ModTools.DIAMOND_SPEAR, ModTools.NETHERITE_SPEAR).offerTo(exporter, getItemPath(ModTools.NETHERITE_SPEAR) + "_smithing");
        makeSpear(ModTools.STEEL_SPEAR, ModItems.STEEL_INGOT).offerTo(exporter);
        makeSpear(ModTools.ROSE_GOLD_SPEAR, ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeAxe(ModTools.COPPER_AXE,Items.COPPER_INGOT).offerTo(exporter);
        makeAxe(ModTools.STEEL_AXE,ModItems.STEEL_INGOT).offerTo(exporter);
        makeAxe(ModTools.ROSE_GOLD_AXE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeShovel(ModTools.COPPER_SHOVEL,Items.COPPER_INGOT).offerTo(exporter);
        makeShovel(ModTools.STEEL_SHOVEL,ModItems.STEEL_INGOT).offerTo(exporter);
        makeShovel(ModTools.ROSE_GOLD_SHOVEL,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        makeHoe(ModTools.COPPER_HOE,Items.COPPER_INGOT).offerTo(exporter);
        makeHoe(ModTools.STEEL_HOE,ModItems.STEEL_INGOT).offerTo(exporter);
        makeHoe(ModTools.ROSE_GOLD_HOE,ModItems.ROSE_GOLD_INGOT).offerTo(exporter);

        offerSmithingTrimRecipe(exporter, ModItems.PANCAKE_RABBIT, Identifier.of(Bunnycraft.MOD_ID,"rabbit"));
    }
}