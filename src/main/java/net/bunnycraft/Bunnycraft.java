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

	//list of variables that you can tweak to change and balance different parts of the mod. for now im only gonna add ones that i think would be annoying to find/change

	//basic damages, note these do not update vanilla tools yet. someone needs to do that
	public static final int swordDmg = 3;
	public static final float spearDmg = 3;
	public static final float axeDmg = 6;
	public static final float pickaxeDmg = 5;
	public static final float shovelDmg = 1.5f;
	public static final float hoeDmg = 0;

	//range stats, note these are added to base range
	public static final int pickaxeRange = 1;
	public static final int swordRange = 0;
	public static final int spearRange = 3;
	public static final int axeRange = 0;
	public static final int shovelRange = 1;
	public static final int hoeRange = 2;


	public static final float steelSlowdown = -0.07f; // this corresponds to a percent slowdown per armor piece
}