package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mixin(SculkSpreadManager.Cursor.class)
public class SculkSpreadManager$CursorMixin {
    @Shadow private BlockPos pos;

    @Inject(
            method = "spread(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/block/entity/SculkSpreadManager;Z)V",
            at = @At("HEAD"),
            cancellable = true)
    private void Bunnycraft$AllowMoreBlocksToBeCursors(WorldAccess world, BlockPos pos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock, CallbackInfo ci) {
        if (world.getBlockState(this.pos).isOf(Blocks.OAK_SAPLING)) {
            world.setBlockState(this.pos.add(0,-1,0),Blocks.SCULK.getDefaultState(),3);
        }
    }
}
