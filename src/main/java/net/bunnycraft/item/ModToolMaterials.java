package net.bunnycraft.item;

import com.google.common.base.Suppliers;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.Objects;
import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
    // note for myself remember that only the last tool material needs a ;
    COPPER(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            161, 5.5F, 1.5F, 12, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    STEEL(ModTags.Blocks.INCORRECT_FOR_STEEL_TOOL,
            3842, 7.0F, 5F, 11, () -> Ingredient.ofItems(ModItems.STEEL_INGOT)),
    ROSE_GOLD(ModTags.Blocks.INCORRECT_FOR_ROSE_GOLD_TOOL,
            535, 14.0F, 2.0F, 26, () -> Ingredient.ofItems(ModItems.ROSE_GOLD_INGOT)),
    PRISMARINE(ModTags.Blocks.INCORRECT_FOR_PRISMARINE_TOOL,
            300, 5.0F, 4.0F, 16, () -> Ingredient.ofItems(Items.PRISMARINE));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(final TagKey<Block> inverseTag, final int itemDurability, final float miningSpeed, final float attackDamage, final int enchantability, final Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        Objects.requireNonNull(repairIngredient);
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
