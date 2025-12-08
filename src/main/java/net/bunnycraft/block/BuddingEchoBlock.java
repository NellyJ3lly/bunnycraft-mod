package net.bunnycraft.block;

import com.mojang.serialization.MapCodec;
import net.bunnycraft.interfaces.ConvertableBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import static net.minecraft.block.AbstractBlock.createCodec;


public class BuddingEchoBlock extends ExperienceDroppingBlock implements SculkSpreadable, ConvertableBlocks{
    public static final MapCodec<BuddingEchoBlock> CODEC = createCodec(BuddingEchoBlock::new);

    public static final int GROW_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public MapCodec<BuddingEchoBlock> getCodec() {
        return CODEC;
    }

    public BuddingEchoBlock(AbstractBlock.Settings settings) {
        super(ConstantIntProvider.create(4), settings);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(GROW_CHANCE) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                block = ModBlocks.SMALL_ECHO_BUD;
            } else if ((blockState.isOf(ModBlocks.SMALL_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) || state.isOf(Blocks.SCULK_VEIN)) {
                block = ModBlocks.MEDIUM_ECHO_BUD;
            } else if (blockState.isOf(ModBlocks.MEDIUM_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.LARGE_ECHO_BUD;
            } else if (blockState.isOf(ModBlocks.LARGE_ECHO_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.ECHO_CLUSTER;
            }

            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(AmethystClusterBlock.FACING, direction)).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(blockPos, blockState2);
            }
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        convertAboveBlock(world,pos);
    }


    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.SCULK_VEIN) || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }

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
            blockState = (BlockState)Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
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

    public boolean shouldConvertToSpreadable() {
        return false;
    }
}
