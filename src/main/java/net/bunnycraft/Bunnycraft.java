package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.item.ModItemGroups;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bunnycraft implements ModInitializer {
	public static final String MOD_ID = "bunnycraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModTools.registerModTools();
		ModArmors.registerModArmors();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModItemGroups.registerItemGroups();
		LOGGER.info("Hello Bunnycrafter!");

		//allows the wood spear to be used as fuel for a burn time of 200 ticks
		FuelRegistry.INSTANCE.add(ModTools.WOODEN_SPEAR, 200);
	}
}

//list of classes modified when merging spearmod, check these for spear related errors

//classes added to bunnycraft: PlayerEntityMixin, ItemRendererMixin, ModelLoaderMixin, ModEntities, SpearEntity, SpearModel, SpearRenderer, ModModelPredicates

//classes merged from spearmod: Bunnycraft, BunnycraftClient, ModTags

//deleted old SpearItem class and added mine