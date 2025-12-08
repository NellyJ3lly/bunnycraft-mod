package net.bunnycraft.datagen.data;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {

    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PANCAKE_RABBIT_ORE, oreDrops(ModBlocks.PANCAKE_RABBIT_ORE, ModItems.PANCAKE_RABBIT));
        addDrop(ModBlocks.PANCAKE_RABBIT_BLOCK);

        addDrop(ModBlocks.PANCAKE_RABBIT_ORE, oreDrops(ModBlocks.PANCAKE_RABBIT_ORE, ModItems.PANCAKE_RABBIT));

        addDrop(ModBlocks.COPPER_BUNNYCOIN_BLOCK);
        addDrop(ModBlocks.GOLD_BUNNYCOIN_BLOCK);
        addDrop(ModBlocks.DIAMOND_BUNNYCOIN_BLOCK);

        addDrop(ModBlocks.ROSE_GOLD_BLOCK);
        addDrop(ModBlocks.STEEL_BLOCK);

        addDrop(ModBlocks.PIPIS_BLOCK);
        addDrop(ModBlocks.MS_PIPIS_BLOCK);

        addDrop(ModBlocks.ENCHANTING_STAND);

        addDrop(ModBlocks.ECHO_BLOCK);
        addDrop(ModBlocks.ECHO_BRICK);
        addDrop(ModBlocks.ECHO_BRICK_WALL);
        addDrop(ModBlocks.ECHO_BRICK_SLAB);
        addDropWithSilkTouch(ModBlocks.SMALL_ECHO_BUD);
        addDropWithSilkTouch(ModBlocks.MEDIUM_ECHO_BUD);
        addDropWithSilkTouch(ModBlocks.LARGE_ECHO_BUD);
        multipleOreDrops(ModBlocks.ECHO_CLUSTER,Items.ECHO_SHARD,1,4);

        addDrop(ModBlocks.SCULK_WOOD_SAPLING);
        addDrop(ModBlocks.SCULK_WOOD_WOOD);
        addDrop(ModBlocks.SCULK_WOOD_LOG);
        addDrop(ModBlocks.STRIPPED_SCULK_WOOD_LOG);
        addDrop(ModBlocks.STRIPPED_SCULK_WOOD_WOOD);
        addDrop(ModBlocks.SCULK_WOOD_PLANKS);
        addDrop(ModBlocks.SCULK_WOOD_STAIRS);
        addDrop(ModBlocks.SCULK_WOOD_SAPLING);
        addDrop(ModBlocks.SCULK_WOOD_DOOR);
        addDrop(ModBlocks.SCULK_WOOD_TRAPDOOR);
        addDrop(ModBlocks.SCULK_WOOD_PRESSURE_PLATE);
        addDrop(ModBlocks.SCULK_WOOD_BUTTON);
    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
