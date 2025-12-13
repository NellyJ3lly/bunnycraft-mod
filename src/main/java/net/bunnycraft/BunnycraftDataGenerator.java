package net.bunnycraft;

import net.bunnycraft.datagen.*;
import net.bunnycraft.datagen.assets.ModModelProvider;
import net.bunnycraft.datagen.data.*;
import net.bunnycraft.trim.ModTrimMaterials;
import net.bunnycraft.trim.ModTrimPatterns;
import net.bunnycraft.world.ModConfiguredFeatures;
import net.bunnycraft.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class BunnycraftDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
		pack.addProvider(ModSimpleLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
//		pack.addProvider(ModEnglishLangProvider::new);
	}


	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.TRIM_MATERIAL, ModTrimMaterials::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, ModTrimPatterns::bootstrap);

		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
