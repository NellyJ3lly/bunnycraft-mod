package net.bunnycraft.world.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.bunnycraft.Bunnycraft;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.HashSet;
import java.util.List;

public class SculkDroopDecorator extends TreeDecorator {
    public static final Codec<SculkDroopDecorator> CODEC = Codec.unit(SculkDroopDecorator::new);
    public static final MapCodec<SculkDroopDecorator> SCULK_DROOP_DECORATOR_MAP_CODEC = CODEC.fieldOf("droop_decorator");

    protected final float probability = 0.5F;
//    protected final int exclusionRadiusXZ = 1;
//    protected final int exclusionRadiusY = 0;
//    protected final BlockStateProvider blockProvider = BlockStateProvider.of(Blocks.SCULK);
//    protected final int requiredEmptyBlocks = 1;
//    protected final List<Direction> directions = List.of(Direction.DOWN);

    @Override
    protected TreeDecoratorType<?> getType() {
        return Bunnycraft.CUSTOM_DECORATOR_TYPE;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        Random random = generator.getRandom();
        generator.getLeavesPositions().forEach(pos -> {
            BlockPos blockPos;

            if (random.nextFloat() < this.probability && generator.isAir(blockPos = pos.west())) {
                SculkDroopDecorator.placeVines(blockPos, generator);
            }
            if (random.nextFloat() < this.probability && generator.isAir(blockPos = pos.east())) {
                SculkDroopDecorator.placeVines(blockPos, generator);
            }
            if (random.nextFloat() < this.probability && generator.isAir(blockPos = pos.north())) {
                SculkDroopDecorator.placeVines(blockPos, generator);
            }
            if (random.nextFloat() < this.probability && generator.isAir(blockPos = pos.south())) {
                SculkDroopDecorator.placeVines(blockPos, generator);
            }
        });
    }

    /**
     * Places a vine at a given position and then up to 4 more vines going downwards.
     */
    private static void placeVines(BlockPos pos, TreeDecorator.Generator generator) {
        generator.replace(pos,Blocks.SCULK.getDefaultState());
        pos = pos.down();

        for (int i = 3; generator.isAir(pos) && i > 0; --i) {
            generator.replace(pos,Blocks.SCULK.getDefaultState());
            pos = pos.down();
        }
    }
}
