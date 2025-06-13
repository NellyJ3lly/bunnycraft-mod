package net.bunnycraft.mixin.item;

import net.bunnycraft.Bunnycraft;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ShovelItem.class)
public class ShovelItemMixin extends MiningToolItem {
    public ShovelItemMixin(ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material, effectiveBlocks, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {



        MutableText text = Text.translatable("tooltip.bunnycraft.shovel_knockback_info") ; // adds the tool tip info

        tooltip.add( text.formatted(Formatting.GRAY) ); // appends the tooltip

        Bunnycraft.LOGGER.info(tooltip.toString());

        super.appendTooltip(stack, context, tooltip, type);
    }
}
