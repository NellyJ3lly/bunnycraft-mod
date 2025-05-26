package net.bunnycraft.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemClassAdditions implements ToggleableFeature, ItemConvertible, FabricItem {

    @Override
    public Item asItem() {
        return null;
    }

    @Override
    public FeatureSet getRequiredFeatures() {
        return null;
    }
}
