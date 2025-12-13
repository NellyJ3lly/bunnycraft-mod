package net.bunnycraft;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.CauldronAlloyerEntity;
import net.bunnycraft.component.ModComponents;
import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.bunnycraft.item.ModItemGroups;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.modifiers.ModifyLootTables;
import net.bunnycraft.networking.HorizontalCollisionPayload;
import net.bunnycraft.networking.CauldronAlloyerS2CPayload;
import net.bunnycraft.sound.ModSounds;
import net.bunnycraft.screen.ModScreenHandlers;
import net.bunnycraft.world.ModConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bunnycraft implements ModInitializer, SpreadableBlock {
	public static final String MOD_ID = "bunnycraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void ClimbingClaws(PlayerEntity entity,boolean horizontalCollision) {
		ItemStack claws = entity.getStackInHand(Hand.MAIN_HAND);

		if (claws.isOf(ModTools.CLIMBING_CLAW)) {
			claws.set(ModComponents.HORIZONTAL_COLLISION,horizontalCollision);
		}
	}

	@Override
	public void onInitialize() {
		ModComponents.registerComponents();
		ModItems.registerModItems();
		ModTools.registerModTools();
		ModArmors.registerModArmors();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModItemGroups.registerItemGroups();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerModScreenHandlers();
		ModSounds.registerSounds();

		StrippableBlockRegistry.register(ModBlocks.SCULK_WOOD_LOG,ModBlocks.STRIPPED_SCULK_WOOD_LOG);
		StrippableBlockRegistry.register(ModBlocks.SCULK_WOOD_WOOD,ModBlocks.STRIPPED_SCULK_WOOD_WOOD);


		// not sure if this is the proper way to do set up a list like this but we'll see
		this.SetupList();

		ModifyLootTables.modifyLootTables();

		//allows the wood spear to be used as fuel for a burn time of 200 ticks
		FuelRegistry.INSTANCE.add(ModTools.WOODEN_SPEAR, 200);

		ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
			if (blockEntity instanceof CauldronAlloyerEntity cauldron) {
				cauldron.onServerChunkLoad();
			}
		});

		PayloadTypeRegistry.playC2S().register(HorizontalCollisionPayload.ID,HorizontalCollisionPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(
				HorizontalCollisionPayload.ID,(payload,context) -> {
					PlayerEntity entity = context.player().getWorld().getPlayerByUuid(context.player().getUuid());
                    assert entity != null;
					ClimbingClaws(entity, payload.horizontalCollision());
			}
		);

        // registers the payload for the cauldron to send info to the client so the renderer can display the correct item
        PayloadTypeRegistry.playS2C().register(CauldronAlloyerS2CPayload.ID, CauldronAlloyerS2CPayload.CODEC);

        LOGGER.info("Bunnycraft Loading Complete!");
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

	public static final float shovelKnockback = 2; // base knockback for shovel
}