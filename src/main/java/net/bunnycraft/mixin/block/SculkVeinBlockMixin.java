package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

import static net.bunnycraft.interfaces.ConvertableBlocks.BLOCK_CONVERSION;

@Mixin(SculkVeinBlock.class)
public abstract class SculkVeinBlockMixin extends MultifaceGrowthBlock implements SculkSpreadable, Waterloggable, SpreadableBlock {
    @Shadow
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    @Unique
    SculkVeinBlock sculkvein = (SculkVeinBlock) (Object) this;

    public SculkVeinBlockMixin(Settings settings) {
        super(settings);
    }

    @Unique
    private void convertBlock(WorldAccess world,BlockPos blockPos,BlockState oldBlockstate,BlockState newBlockState) {
        System.out.println(world.getBlockState(blockPos.add(0,1,0)));
        world.setBlockState(blockPos, newBlockState, 3);
        Block.pushEntitiesUpBeforeBlockChange(oldBlockstate, newBlockState, world, blockPos);
        world.playSound((PlayerEntity) null, blockPos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Inject(
            method = "convertToBlock(Lnet/minecraft/block/entity/SculkSpreadManager;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void convertAmethyst(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);

        for(Direction direction : Direction.shuffle(random)) {
            if (hasDirection(blockState, direction)) {
                BlockPos blockPos = pos.offset(direction);
                Block oldBlock = world.getBlockState(blockPos).getBlock();

                if (BLOCK_CONVERSION.containsKey(oldBlock)) {
                    cir.cancel();
                    convertBlock(world,blockPos,oldBlock.getDefaultState(),BLOCK_CONVERSION.get(oldBlock).getDefaultState());
                }
            }
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void spreadAtSamePosition(WorldAccess world, BlockState state, BlockPos pos, Random random) {
        if (state.isOf(this)) {
            for(Direction direction : DIRECTIONS) {
                BooleanProperty booleanProperty = getProperty(direction);

                this.LoopPositions(world,pos);

                if ((Boolean)state.get(booleanProperty) && world.getBlockState(pos.offset(direction)).isOf(Blocks.SCULK)) {
                    state = (BlockState)state.with(booleanProperty, false);
                }
            }

            if (!hasAnyDirection(state)) {
                FluidState fluidState = world.getFluidState(pos);
                state = (fluidState.isEmpty() ? Blocks.AIR : Blocks.WATER).getDefaultState();
            }

            world.setBlockState(pos, state, 3);
//            super.spreadAtSamePosition(world, state, pos, random);
        }
    }
}
