package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.block.SculkVeinBlock$SculkVeinGrowChecker")
class SculkVeinGrowCheckerMixin extends LichenGrower.LichenGrowChecker {

    public SculkVeinGrowCheckerMixin(MultifaceGrowthBlock lichen) {
        super(lichen);
    }

    @Inject(
            method = "canGrow(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;)Z",
            at = @At("HEAD"),
            cancellable = true)
    public void canGrow(BlockView world, BlockPos pos, BlockPos growPos, Direction direction, BlockState state, CallbackInfoReturnable<Boolean> cir) {
            BlockState blockState = world.getBlockState(growPos.offset(direction));

        if (blockState.isOf(ModBlocks.BUDDING_ECHO)) {
            cir.cancel();
            cir.setReturnValue(false);
        }
    }
}
