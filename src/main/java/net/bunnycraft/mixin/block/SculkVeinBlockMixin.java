package net.bunnycraft.mixin.block;

import net.bunnycraft.interfaces.ConvertableBlocks;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(SculkVeinBlock.class)
public abstract class SculkVeinBlockMixin extends MultifaceGrowthBlock implements SculkSpreadable, Waterloggable, SpreadableBlock {
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
    private void Bunnycraft$ConvertBlocks(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        for(Direction direction : Direction.shuffle(random)) {
            if (hasDirection(world.getBlockState(pos), direction)) {
                BlockPos blockPos = pos.offset(direction);
                Block oldBlock = world.getBlockState(blockPos).getBlock();

                if (ConvertableBlocks.BLOCK_CONVERSION().containsKey(oldBlock)) {
                    cir.cancel();
                    convertBlock(world,blockPos,oldBlock.getDefaultState(),ConvertableBlocks.BLOCK_CONVERSION().get(oldBlock).getDefaultState());
                }
            }
        }
    }

    @Inject(
            method = "spreadAtSamePosition(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V",
            at = @At("HEAD")
    )
    private void Bunnycraft$checkforNonFullBlocks(WorldAccess world, BlockState state, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.isOf(this)) {
            this.CheckForNonFullBlocks(world,pos);
        }
    }
}
