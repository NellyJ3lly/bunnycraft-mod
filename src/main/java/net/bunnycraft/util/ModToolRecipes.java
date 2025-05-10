package net.bunnycraft.util;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static net.minecraft.data.server.recipe.RecipeProvider.*;

public interface ModToolRecipes {
    default ShapedRecipeJsonBuilder makePickaxe(Item Pickaxe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Pickaxe)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeAxe(Item Axe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Axe)
                .pattern("XX ")
                .pattern("X# ")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeHoe(Item Hoe, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Hoe)
                .pattern("XX ")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeShovel(Item Shovel, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Shovel)
                .pattern(" X ")
                .pattern(" # ")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeSword(Item Sword, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Sword)
                .pattern(" X ")
                .pattern(" X ")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    default ShapedRecipeJsonBuilder makeSpear(Item Spear, Item Ingot) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Spear)
                .pattern(" X ")
                .pattern("X#X")
                .pattern(" # ")
                .input('X', Ingot)
                .input('#', Items.STICK)
                .criterion(hasItem(Ingot), conditionsFromItem(Ingot));
    }

    //overflow method for the wood and stone which can use multiple different blocks as the ingot
    //unlock condition is the default item to unlock the recipe. should be the most common item of the tag provided

    default ShapedRecipeJsonBuilder makeSpear(Item Spear, String nameSpace, String tag, Item unlockCondition) {
        return ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Spear)
                .pattern(" X ")
                .pattern("X#X")
                .pattern(" # ")
                .input('X', TagKey.of(RegistryKeys.ITEM, Identifier.of(nameSpace, tag)))
                .input('#', Items.STICK)
                .criterion(hasItem(unlockCondition), unlockCondition == Items.STICK ? conditionsFromItem(Items.STICK) : conditionsFromTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(nameSpace, tag))));

    }

    default SmithingTransformRecipeJsonBuilder upgradeToNetherite(Item input, Item result) {
        return SmithingTransformRecipeJsonBuilder.create(
                Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(input), Ingredient.ofItems(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, result
        )
                .criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT));
    }
}
