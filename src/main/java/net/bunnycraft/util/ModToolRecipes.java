package net.bunnycraft.util;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import static net.minecraft.data.server.recipe.RecipeProvider.conditionsFromItem;
import static net.minecraft.data.server.recipe.RecipeProvider.hasItem;

public interface ModToolRecipes {
    default ShapedRecipeJsonBuilder makePickaxe(Item Pickaxe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Pickaxe)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot).input('#', Items.STICK).criterion(hasItem(Ingot), conditionsFromItem(Pickaxe));
    }

    default ShapedRecipeJsonBuilder makeAxe(Item Axe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Axe)
                .pattern("XX ")
                .pattern("X# ")
                .pattern(" # ")
                .input('X', Ingot).input('#', Items.STICK).criterion(hasItem(Ingot), conditionsFromItem(Axe));
    }

    default ShapedRecipeJsonBuilder makeHoe(Item Hoe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Hoe)
                .pattern("XX ")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot).input('#', Items.STICK).criterion(hasItem(Ingot), conditionsFromItem(Hoe));
    }

    default ShapedRecipeJsonBuilder makeShovel(Item Shovel, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Shovel)
                .pattern(" X ")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot).input('#', Items.STICK).criterion(hasItem(Ingot), conditionsFromItem(Shovel));
    }

    default ShapedRecipeJsonBuilder makeSword(Item Sword, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Sword)
                .pattern(" X ")
                .pattern(" X ")
                .pattern(" # ")
                .input('X', Ingot).input('#', Items.STICK).criterion(hasItem(Ingot), conditionsFromItem(Sword));
    }
}
