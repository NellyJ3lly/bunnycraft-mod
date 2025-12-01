package net.bunnycraft.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;

public class EchoClusterBlock extends AmethystClusterBlock{

    private final int experienceDropped;
    public EchoClusterBlock(float height, float xzOffset, Settings settings,int experienceDropped) {
        super(height,xzOffset,settings);
        this.experienceDropped = experienceDropped;
    }

    @Override
    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (dropExperience) {
            IntProvider intProvider = ConstantIntProvider.create(this.experienceDropped);

            this.dropExperienceWhenMined(world, pos, tool,intProvider);
        }

    }
}
