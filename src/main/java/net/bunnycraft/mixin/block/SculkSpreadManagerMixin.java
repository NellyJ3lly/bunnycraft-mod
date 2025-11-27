package net.bunnycraft.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.SculkCatalystBlockEntity;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.block.MultifaceGrowthBlock.hasDirection;
import static net.minecraft.client.render.WorldRenderer.DIRECTIONS;

@Mixin(SculkVeinBlock.class)
public abstract class SculkSpreadManagerMixin {

    @Unique
    SculkVeinBlock sculkvein = (SculkVeinBlock) (Object) this;

    @Inject(
            method =    "convertToBlock(Lnet/minecraft/block/entity/SculkSpreadManager;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void convertAmethyst(SculkSpreadManager spreadManager, WorldAccess world, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        TagKey<Block> tagKey = spreadManager.getReplaceableTag();

        for(Direction direction : Direction.shuffle(random)) {
            if (hasDirection(blockState, direction)) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isOf(Blocks.AMETHYST_BLOCK)) {
                    cir.cancel();
                    BlockState blockState3 = Blocks.BLUE_WOOL.getDefaultState();
                    world.setBlockState(blockPos, blockState3, 3);
                    Block.pushEntitiesUpBeforeBlockChange(blockState2, blockState3, world, blockPos);
                    world.playSound((PlayerEntity) null, blockPos, SoundEvents.BLOCK_SCULK_SPREAD, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }
}
