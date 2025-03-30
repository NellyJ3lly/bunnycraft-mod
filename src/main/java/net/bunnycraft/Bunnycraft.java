package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItemGroups;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bunnycraft implements ModInitializer {
	public static final String MOD_ID = "bunnycraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModTools.registerModTools();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		LOGGER.info("Hello Bunnycrafter!");
	}
}