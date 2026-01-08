package net.bunnycraft.block;

import net.bunnycraft.interfaces.NonCollidingSculkBlock;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Unique;

public class SculkSpreadableBlock extends Block implements SculkSpreadable, NonCollidingSculkBlock {
    public SculkSpreadableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, getMoveSpeedInSculk(entity));
        }
    }

    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (checkIfPlayerIsInSculk(world,context)) {
            return VoxelShapes.empty();
        }

        return super.getCameraCollisionShape(state, world, pos, context);
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

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, (double)spreadManager.getMaxDistance());
            if (!bl && shouldNotDecay(world, blockPos)) {
                int j = spreadManager.getExtraBlockChance();
                if (random.nextInt(j) < i) {
                    BlockPos blockPos2 = blockPos.up();
                    BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());
                    world.setBlockState(blockPos2, blockState, 3);
                    world.playSound((PlayerEntity)null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return Math.max(0, i - j);
            } else {
                return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
            }
        } else {
            return i;
        }
    }


    private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.getMaxDistance();
        float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
        int j = MathHelper.square(24 - i);
        float g = Math.min(1.0F, f / (float)j);
        return Math.max(1, (int)((float)charge * g * 0.5F));
    }

    private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, Random random, boolean allowShrieker) {
        BlockState blockState;
        if (random.nextInt(11) == 0) {
            blockState = (BlockState) Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
        } else {
            blockState = Blocks.SCULK_SENSOR.getDefaultState();
        }

        return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? (BlockState)blockState.with(Properties.WATERLOGGED, true) : blockState;
    }

    private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        if (blockState.isAir() || blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER)) {
            int i = 0;

            for(BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4))) {
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER)) {
                    ++i;
                }

                if (i > 2) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
