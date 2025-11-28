package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSpreadable;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SculkSpreadManager.Cursor.class)
public class SculkSpreadManager$CursorMixin {
//    @Inject(
//            method = "getSpreadable(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/SculkSpreadable;",
//            at = @At("HEAD"),
//            cancellable = true)
//    private static void tick3(BlockState state, CallbackInfoReturnable<SculkSpreadable> cir) {
//        Block var2 = state.getBlock();
//
//        SculkSpreadable var10000 = SculkSpreadable.VEIN_ONLY_SPREADER;
//
//        cir.setReturnValue(var10000);
//
//        System.out.println(var2);
//    }

}
