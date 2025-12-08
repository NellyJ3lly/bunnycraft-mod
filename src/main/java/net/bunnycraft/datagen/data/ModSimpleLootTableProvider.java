package net.bunnycraft.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModSimpleLootTableProvider extends SimpleFabricLootTableProvider {
    public ModSimpleLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.GENERIC);
    }

    public static final RegistryKey<LootTable> ADDITIONAL_SNIFFER_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("bunnycraft", "additional_sniffer_drops"));

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(ADDITIONAL_SNIFFER_LOOT, LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(Items.BONE)
                                .weight(30)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,7))))
                        .with(ItemEntry.builder(Items.COCOA_BEANS)
                                .weight(15)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.RAW_COPPER)
                                .weight(15)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,6))))
                        .with(ItemEntry.builder(Items.RAW_IRON)
                                .weight(7)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,3))))
                        .with(ItemEntry.builder(Items.RAW_GOLD)
                                .weight(7)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,3))))
                        .with(ItemEntry.builder(Items.COAL)
                                .weight(5)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,5))))
                        .with(ItemEntry.builder(Items.EMERALD)
                                .weight(5)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,2))))
                        .with(ItemEntry.builder(Items.RAW_COPPER_BLOCK)
                                .weight(2)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.RAW_IRON_BLOCK)
                                .weight(2)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.RAW_GOLD_BLOCK)
                                .weight(2)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.DIAMOND)
                                .weight(1)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1,3)))))
        );
    }
}
