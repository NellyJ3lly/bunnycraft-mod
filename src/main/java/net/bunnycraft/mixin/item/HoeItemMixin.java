package net.bunnycraft.mixin.item;

import net.bunnycraft.Bunnycraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

// NOTICE the hoe sweeping modifications is done in the player entity mixin

@Mixin(HoeItem.class)
public class HoeItemMixin extends MiningToolItem {
    public HoeItemMixin(ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material, effectiveBlocks, settings);
    }

    @Unique
    IntProvider bunnycraft$size = UniformIntProvider.create(1,2);


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.isIn(BlockTags.CROPS)) {
            ExperienceOrbEntity.spawn((ServerWorld) world, Vec3d.ofCenter(pos), bunnycraft$size.get(Random.create()));
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MutableText text = Text.translatable("tooltip.bunnycraft.hoe_sweeping_info") ; // adds the tool tip info

        tooltip.add( text.formatted(Formatting.GRAY) ); // appends the tooltip

//        Bunnycraft.LOGGER.info(tooltip.toString());

        super.appendTooltip(stack, context, tooltip, type);
    }
}
