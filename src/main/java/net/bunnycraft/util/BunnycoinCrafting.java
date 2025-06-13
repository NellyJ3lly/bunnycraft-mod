package net.bunnycraft.util;

import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;

import static net.minecraft.data.server.recipe.RecipeProvider.conditionsFromItem;
import static net.minecraft.data.server.recipe.RecipeProvider.hasItem;

public class BunnycoinCrafting {
    public static void makeCoin(RecipeExporter exporter, String Path, Item output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output, 1).input('#', input)
                .pattern("##").pattern("##")
                .criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter,Path+"_convert_to_output");
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, input, 4)
                .input('#', output)
                .pattern("#")
                .criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter,Path+"_convert_to_input");
    }
}

