package net.bunnycraft.world;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.world.decorator.SculkDroopDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.*;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.Objects;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> SCULKWOOD_KEY = registerKey("sculk");

    static List<Direction> SculkWoodDirection = List.of(Direction.DOWN);

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context) {
        register(context,SCULKWOOD_KEY,Feature.TREE,new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.SCULK_WOOD_LOG),
                new BendingTrunkPlacer(3,3,1,4,ConstantIntProvider.create(1)),

                BlockStateProvider.of(Blocks.SCULK),
                new AcaciaFoliagePlacer(ConstantIntProvider.create(2),ConstantIntProvider.create(1)),

                new TwoLayersFeatureSize(1,0,1)).dirtProvider(BlockStateProvider.of(Blocks.SCULK))

                .decorators(List.of(
                        new SculkDroopDecorator()
                        )
                ).build());
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Bunnycraft.MOD_ID,name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                  RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key,new ConfiguredFeature<>(feature,configuration));
    }
}
