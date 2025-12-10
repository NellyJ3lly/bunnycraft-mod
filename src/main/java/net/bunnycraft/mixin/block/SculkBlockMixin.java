package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.interfaces.ConvertableBlocks;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.bunnycraft.item.ModTools;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkBlock.class)
public abstract class SculkBlockMixin extends ExperienceDroppingBlock implements SculkSpreadable, SpreadableBlock,ConvertableBlocks {
    public SculkBlockMixin(IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
    }

    private static final VoxelShape FALLING_SHAPE = VoxelShapes.cuboid((double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F, (double)0.9F, (double)1.0F);

    // I'm pretty sure this is fine as an override because the block itself doesn't have this method
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        convertAboveBlock(world,pos);
    }

    @Unique
    private boolean getSculkCane(PlayerEntity playerEntity) {
        return playerEntity.getStackInHand(Hand.MAIN_HAND).isOf(ModTools.SCULK_CANE) || playerEntity.getStackInHand(Hand.OFF_HAND).isOf(ModTools.SCULK_CANE);
    }


    @Unique
    private boolean checkIfPlayerIsInSculk(BlockView world, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            if (entityShapeContext.getEntity() instanceof PlayerEntity player) {
                boolean insideSculk = world.getBlockState(player.getBlockPos()).isOf(Blocks.SCULK) || world.getBlockState(player.getBlockPos().add(0,1,0)).isOf(Blocks.SCULK);
                return (getSculkCane(player) && player.isSneaking()) || insideSculk;
            }
        }
        return false;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.9F,1F,0.9F));
        }
    }

    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (checkIfPlayerIsInSculk(world,context)) {
            return VoxelShapes.empty();
        }

        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public boolean hasDynamicBounds() {
        return true;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            if (entityShapeContext.getEntity() instanceof PlayerEntity player) {

                boolean bool = context.isAbove(VoxelShapes.fullCube(), pos, false)
                        || world.getBlockState(player.getBlockPos().add(0,-1,0)).isAir();

                if (bool && !context.isDescending()) {
                    return super.getCollisionShape(state, world, pos, context);
                }
//
                if (checkIfPlayerIsInSculk(world,context)) {
                    return VoxelShapes.empty();
                }
            }
        }

        return super.getCollisionShape(state, world, pos, context);
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
