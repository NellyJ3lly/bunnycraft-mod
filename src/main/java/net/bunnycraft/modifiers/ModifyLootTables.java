package net.bunnycraft.modifiers;

import net.bunnycraft.datagen.ModSimpleLootTableProvider;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.util.ModLootTables;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModifyLootTables {
    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin()) {
                if(LootTables.ANCIENT_CITY_CHEST == key){
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.1f))
                            .with(ItemEntry.builder(ModItems.STEEL_UPGRADE_TEMPLATE))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
                    tableBuilder.pool(poolBuilder);
                    poolBuilder.build();
                }
                if(LootTables.SNIFFER_DIGGING_GAMEPLAY == key){
                    LootPool.Builder smithingTemplatePoolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .conditionally(RandomChanceLootCondition.builder(0.25f))
                            .with(LootTableEntry.builder(ModSimpleLootTableProvider.ADDITIONAL_SNIFFER_LOOT).build());
                    tableBuilder.pool(smithingTemplatePoolBuilder);
                    smithingTemplatePoolBuilder.build();
                }
            }
        });
    }
}
