package net.bunnycraft.datagen;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
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
    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
