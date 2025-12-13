package net.bunnycraft.world;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> SCULKWOOD_KEY = registerKey("sculk");


    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context) {
        register(context,SCULKWOOD_KEY,Feature.TREE,new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.SCULK_WOOD_LOG),
                new BendingTrunkPlacer(3,1,1,3,ConstantIntProvider.create(1)),

                BlockStateProvider.of(Blocks.SCULK),
                new AcaciaFoliagePlacer(ConstantIntProvider.create(2),ConstantIntProvider.create(1)),

                new TwoLayersFeatureSize(1,0,2)).dirtProvider(BlockStateProvider.of(Blocks.SCULK)).build()
        );
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Bunnycraft.MOD_ID,name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                  RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key,new ConfiguredFeature<>(feature,configuration));
    }
}
