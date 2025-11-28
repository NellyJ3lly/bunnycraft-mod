package net.bunnycraft.mixin.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(SculkSpreadManager.class)
public class SculkSpreadManagerMixin {

    @Shadow private List<SculkSpreadManager.Cursor> cursors;

    @Inject(
            method = "tick(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Z)V",
            at = @At("HEAD"),
            cancellable = true)
    public void tick2(WorldAccess world, BlockPos pos, Random random, boolean shouldConvertToBlock, CallbackInfo ci) {
        if (!this.cursors.isEmpty()) {
            this.cursors.forEach(cursor -> {
                BlockPos cursorpos = cursor.getPos();
                Block cursorblock = world.getBlockState(cursor.getPos()).getBlock();

                // replaces the block below as well which may be bad
                // we can assume that it's grass though
                if (cursorblock == Blocks.OAK_SAPLING) {
                    ci.cancel();
                    BlockState blockState2 = ModBlocks.SCULK_WOOD_SAPLING.getDefaultState();
                    BlockState blockState3 = Blocks.SCULK.getDefaultState();
                    BlockState blockState4 = Blocks.AIR.getDefaultState();

                    world.setBlockState(cursorpos,blockState4,3);
                    world.setBlockState(cursorpos.add(0,-1,0),blockState3,3);
                    world.setBlockState(cursorpos,blockState2,3);
                }
            });
        }
    }
}
