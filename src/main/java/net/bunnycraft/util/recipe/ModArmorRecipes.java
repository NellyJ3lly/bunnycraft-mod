package net.bunnycraft.util.recipe;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;

import static net.minecraft.data.server.recipe.RecipeProvider.*;

public interface ModArmorRecipes {
    default ShapedRecipeJsonBuilder makeHelmet(Item Helmet, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Helmet)
                .pattern("XXX")
                .pattern("X X")
                .input('X', Ingot)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeChestplate(Item Chestplate, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Chestplate)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .input('X', Ingot)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeLeggings(Item Leggings, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Leggings)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .input('X', Ingot)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeBoots(Item Boots, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Boots)
                .pattern("X X")
                .pattern("X X")
                .input('X', Ingot)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default SmithingTransformRecipeJsonBuilder upgradeToPiece(Item Template,Item PieceToTransmute,Item input, Item result) {
        return SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(Template), Ingredient.ofItems(PieceToTransmute), Ingredient.ofItems(input), RecipeCategory.TOOLS, result)
                .criterion(hasItem(input), conditionsFromItem(input));
    }
}
