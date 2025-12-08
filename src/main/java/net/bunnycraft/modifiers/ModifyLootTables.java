package net.bunnycraft.modifiers;

import net.bunnycraft.datagen.data.ModSimpleLootTableProvider;
import net.bunnycraft.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModifyLootTables {
    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin()) {
                if(LootTables.ANCIENT_CITY_CHEST.equals(key)){
                    LootPool.Builder smithingTemplatePoolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(ModItems.STEEL_UPGRADE_TEMPLATE))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
                    tableBuilder.pool(smithingTemplatePoolBuilder);
                    smithingTemplatePoolBuilder.build();
                }
                if(LootTables.SNIFFER_DIGGING_GAMEPLAY.equals(key)){
                    LootPool.Builder sniffer_digging_builder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(LootTableEntry.builder(ModSimpleLootTableProvider.ADDITIONAL_SNIFFER_LOOT).build());
                    sniffer_digging_builder.build();
                    tableBuilder.pool(sniffer_digging_builder);
                }
            }
        });
    }
}
