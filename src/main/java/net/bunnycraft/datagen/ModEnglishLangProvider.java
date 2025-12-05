package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLangProvider extends FabricLanguageProvider {

    public ModEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModBlocks.ECHO_BLOCK,"Echo Block");
        translationBuilder.add(ModBlocks.BUDDING_ECHO,"Budding Echo");
        translationBuilder.add(ModBlocks.ECHO_CLUSTER,"Echo Cluster");

        translationBuilder.add(ModBlocks.LARGE_ECHO_BUD,"Large Echo Bud");
        translationBuilder.add(ModBlocks.MEDIUM_ECHO_BUD,"Medium Echo Bud");
        translationBuilder.add(ModBlocks.SMALL_ECHO_BUD,"Small Echo Bud");

        translationBuilder.add(ModBlocks.SCULK_WOOD_SAPLING,"Sculk Wood Sapling");
        translationBuilder.add(ModBlocks.SCULK_WOOD_LOG,"Sculk Wood Log");
        translationBuilder.add(ModBlocks.SCULK_WOOD_WOOD,"Sculk Wood");
        translationBuilder.add(ModBlocks.STRIPPED_SCULK_WOOD_LOG,"Stripped Sculk Wood Log");
        translationBuilder.add(ModBlocks.STRIPPED_SCULK_WOOD_WOOD,"Stripped Sculk Wood");
        translationBuilder.add(ModBlocks.SCULK_WOOD_PLANKS,"Sculk Wood Planks");
        translationBuilder.add(ModBlocks.SCULK_WOOD_DOOR,"Sculk Wood Door");
        translationBuilder.add(ModBlocks.SCULK_WOOD_TRAPDOOR,"Sculk Wood Trapdoor");
        translationBuilder.add(ModBlocks.SCULK_WOOD_STAIRS,"Sculk Wood Stairs");
        translationBuilder.add(ModBlocks.SCULK_WOOD_SLAB,"Sculk Wood Slab");
        translationBuilder.add(ModBlocks.SCULK_WOOD_PRESSURE_PLATE,"Sculk Wood Pressure Plate");
        translationBuilder.add(ModBlocks.SCULK_WOOD_FENCE,"Sculk Wood Fence");
        translationBuilder.add(ModBlocks.SCULK_WOOD_FENCE_GATE,"Sculk Wood Fence Gate");

        translationBuilder.add(ModBlocks.ECHO_BRICK,"Echo Brick");
        translationBuilder.add(ModBlocks.ECHO_BRICK_SLAB,"Echo Brick Slab");
        translationBuilder.add(ModBlocks.ECHO_BRICK_STAIRS,"Echo Brick Stairs");
        translationBuilder.add(ModBlocks.ECHO_BRICK_WALL,"Echo Brick Wall");
        translationBuilder.add(ModBlocks.CHISELED_ECHO_BRICK,"Chiseled Echo Brick");
        translationBuilder.add(ModTools.ECHOLOCATOR,"Echolocator");
    }
}
