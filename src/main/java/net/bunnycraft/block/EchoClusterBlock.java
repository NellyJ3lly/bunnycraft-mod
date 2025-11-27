package net.bunnycraft.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.Constant;

public class EchoClusterBlock extends ExperienceDroppingBlock{
    public static final MapCodec<EchoClusterBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.FLOAT.fieldOf("height").forGetter((block) -> block.height), Codec.FLOAT.fieldOf("aabb_offset").forGetter((block) -> block.xzOffset), createSettingsCodec()).apply(instance, EchoClusterBlock::new));
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    private final float height;
    private final float xzOffset;
    protected final VoxelShape northShape;
    protected final VoxelShape southShape;
    protected final VoxelShape eastShape;
    protected final VoxelShape westShape;
    protected final VoxelShape upShape;
    protected final VoxelShape downShape;

    public EchoClusterBlock(Settings settings, float height, float xzOffset, VoxelShape northShape, VoxelShape southShape, VoxelShape eastShape, VoxelShape westShape, VoxelShape upShape, VoxelShape downShape) {
        super(ConstantIntProvider.create(2), settings);
        this.height = height;
        this.xzOffset = xzOffset;
        this.northShape = northShape;
        this.southShape = southShape;
        this.eastShape = eastShape;
        this.westShape = westShape;
        this.upShape = upShape;
        this.downShape = downShape;
    }

    public MapCodec<EchoClusterBlock> getCodec() {
        return CODEC;
    }

    public EchoClusterBlock(float height, float xzOffset, AbstractBlock.Settings settings) {
        super(ConstantIntProvider.create(1),settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, false)).with(FACING, Direction.UP));
        this.upShape = Block.createCuboidShape((double)xzOffset, (double)0.0F, (double)xzOffset, (double)(16.0F - xzOffset), (double)height, (double)(16.0F - xzOffset));
        this.downShape = Block.createCuboidShape((double)xzOffset, (double)(16.0F - height), (double)xzOffset, (double)(16.0F - xzOffset), (double)16.0F, (double)(16.0F - xzOffset));
        this.northShape = Block.createCuboidShape((double)xzOffset, (double)xzOffset, (double)(16.0F - height), (double)(16.0F - xzOffset), (double)(16.0F - xzOffset), (double)16.0F);
        this.southShape = Block.createCuboidShape((double)xzOffset, (double)xzOffset, (double)0.0F, (double)(16.0F - xzOffset), (double)(16.0F - xzOffset), (double)height);
        this.eastShape = Block.createCuboidShape((double)0.0F, (double)xzOffset, (double)xzOffset, (double)height, (double)(16.0F - xzOffset), (double)(16.0F - xzOffset));
        this.westShape = Block.createCuboidShape((double)(16.0F - height), (double)xzOffset, (double)xzOffset, (double)16.0F, (double)(16.0F - xzOffset), (double)(16.0F - xzOffset));
        this.height = height;
        this.xzOffset = xzOffset;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction)state.get(FACING);
        switch (direction) {
            case NORTH:
                return this.northShape;
            case SOUTH:
                return this.southShape;
            case EAST:
                return this.eastShape;
            case WEST:
                return this.westShape;
            case DOWN:
                return this.downShape;
            case UP:
            default:
                return this.upShape;
        }
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = (Direction)state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction == ((Direction)state.get(FACING)).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return (BlockState)((BlockState)this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(FACING, ctx.getSide());
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED, FACING});
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        FACING = Properties.FACING;
    }
}
