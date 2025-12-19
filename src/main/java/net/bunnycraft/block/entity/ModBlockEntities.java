package net.bunnycraft.block.entity;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.BunnyBankBlock;
import net.bunnycraft.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<CauldronAlloyerEntity> CAULDRON_ALLOYER_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "cauldron_alloyer_entity"),
                    BlockEntityType.Builder.create(CauldronAlloyerEntity::new, Blocks.CAULDRON).build(null));

    public static final BlockEntityType<EnchantingStandEntity> ENCHANTING_STAND_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "enchanting_stand_entity"),
                    BlockEntityType.Builder.create(EnchantingStandEntity::new, ModBlocks.ENCHANTING_STAND).build(null));

    public static final BlockEntityType<BunnyBankEntity> BUNNY_BANK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "bunny_bank_entity"),
                    BlockEntityType.Builder.create(BunnyBankEntity::new, ModBlocks.BUNNY_BANK).build(null));

    public static final BlockEntityType<SculkBatteryBlockEntity> SCULK_BATTERY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "sculk_battery_block_entity"),
                    BlockEntityType.Builder.create(SculkBatteryBlockEntity::new, ModBlocks.SCULK_BATTERY).build(null));


    public static void registerBlockEntities() {
        Bunnycraft.LOGGER.info("Registering Block Entities for " + Bunnycraft.MOD_ID);
    }
}
