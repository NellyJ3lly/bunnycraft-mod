package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.interfaces.ConvertableBlocks;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkBlock.class)
public abstract class SculkBlockMixin extends ExperienceDroppingBlock implements SculkSpreadable, SpreadableBlock,ConvertableBlocks {

    public SculkBlockMixin(IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
    }

    // I'm pretty sure this is fine as an override because the block itself doesn't have this method
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        convertAboveBlock(world,pos);
    }

    @Inject(
            method = "spread(Lnet/minecraft/block/entity/SculkSpreadManager$Cursor;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/block/entity/SculkSpreadManager;Z)I",
            at = @At("HEAD")
    )
    public void Bunnycraft$ConvertNonFullBlocks(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock, CallbackInfoReturnable<Integer> cir) {
        if (cursor.getCharge() != 0) {
            this.CheckForNonFullBlocks(world,cursor.getPos().add(0,1,0));
        }
    }

    @Inject(
            method = "getExtraBlockState(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;Z)Lnet/minecraft/block/BlockState;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void Bunnycraft$SculkPlaceModBlocks(WorldAccess world, BlockPos pos, Random random, boolean allowShrieker, CallbackInfoReturnable<BlockState> cir) {
        int chance = random.nextInt(11);

        if (chance != 0) {
            if (chance >= 3) {
                cir.cancel();
                cir.setReturnValue(ModBlocks.SCULK_WOOD_SAPLING.getDefaultState());
            }
        }
    }
}
