package net.bunnycraft.block;

import com.mojang.serialization.MapCodec;
import net.bunnycraft.block.entity.BunnyBankEntity;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BunnyBankBlock extends BlockWithEntity implements BlockEntityProvider, Equipment {
    public static final MapCodec<BunnyBankBlock> CODEC = BunnyBankBlock.createCodec(BunnyBankBlock::new);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return switch (state.get(Properties.HORIZONTAL_FACING)) {
            case SOUTH -> makeShapeSouth();
            case WEST -> makeShapeWest();
            case EAST -> makeShapeEast();
            default -> makeShapeNorth();
        };
    }



    protected BunnyBankBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                .with(WATERLOGGED, false)
        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BunnyBankEntity(pos,state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING,Properties.WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof BunnyBankEntity bunnyBankEntity) {
            player.openHandledScreen(bunnyBankEntity);
        }
        return ActionResult.SUCCESS;
    }

    public VoxelShape makeShapeNorth(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0, 0.1875, 0.375, 0.125, 0.3125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.625, 0, 0.1875, 0.75, 0.125, 0.3125), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.625, 0, 0.625, 0.75, 0.125, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0, 0.625, 0.375, 0.125, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5625, 0.5, 0.3125, 0.75, 1.0625, 0.4375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.125, 0.1875, 0.75, 0.5, 0.75), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.25, 0.5, 0.3125, 0.4375, 1.0625, 0.4375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.375, 0.1875, 0.75, 0.625, 0.4375, 0.9375), BooleanBiFunction.OR);

        return shape;
    }

    public VoxelShape makeShapeWest(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18750000000000003, 0, 0.6250000000000001, 0.3124999999999999, 0.125, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18750000000000003, 0, 0.24999999999999997, 0.3124999999999999, 0.125, 0.3750000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6249999999999999, 0, 0.24999999999999997, 0.7499999999999999, 0.125, 0.3750000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6249999999999999, 0, 0.6250000000000001, 0.7499999999999999, 0.125, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3124999999999999, 0.5, 0.24999999999999997, 0.4374999999999999, 1.0625, 0.4375000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.18750000000000003, 0.125, 0.24999999999999997, 0.7499999999999999, 0.5, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3124999999999999, 0.5, 0.5625000000000001, 0.4374999999999999, 1.0625, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.7499999999999999, 0.1875, 0.3750000000000001, 0.9374999999999999, 0.4375, 0.6250000000000001), BooleanBiFunction.OR);

        return shape;
    }

    public VoxelShape makeShapeSouth(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6249999999999998, 0, 0.6875, 0.7499999999999998, 0.125, 0.8124999999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2499999999999998, 0, 0.6875, 0.3749999999999998, 0.125, 0.8124999999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2499999999999998, 0, 0.2500000000000001, 0.3749999999999998, 0.125, 0.375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6249999999999998, 0, 0.2500000000000001, 0.7499999999999998, 0.125, 0.375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2499999999999998, 0.5, 0.5625, 0.4374999999999998, 1.0625, 0.6875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.2499999999999998, 0.125, 0.2500000000000001, 0.7499999999999998, 0.5, 0.8124999999999999), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5624999999999998, 0.5, 0.5625, 0.7499999999999998, 1.0625, 0.6875), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3749999999999998, 0.1875, 0.06250000000000011, 0.6249999999999998, 0.4375, 0.2500000000000001), BooleanBiFunction.OR);

        return shape;
    }

    public VoxelShape makeShapeEast(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6874999999999997, 0, 0.2500000000000001, 0.8124999999999996, 0.125, 0.3750000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.6874999999999997, 0, 0.6250000000000001, 0.8124999999999996, 0.125, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.24999999999999992, 0, 0.6250000000000001, 0.37499999999999967, 0.125, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.24999999999999992, 0, 0.2500000000000001, 0.37499999999999967, 0.125, 0.3750000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5624999999999997, 0.5, 0.5625000000000001, 0.6874999999999997, 1.0625, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.24999999999999992, 0.125, 0.2500000000000001, 0.8124999999999996, 0.5, 0.7500000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.5624999999999997, 0.5, 0.2500000000000001, 0.6874999999999997, 1.0625, 0.4375000000000001), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.06249999999999989, 0.1875, 0.3750000000000001, 0.24999999999999992, 0.4375, 0.6250000000000001), BooleanBiFunction.OR);

        return shape;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
