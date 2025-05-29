package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {
    public static RegistryKey<LootTable> ZOMBIE_SHEARED = of("gameplay/zombie_sheared");

    private static RegistryKey<LootTable> of(String id) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Bunnycraft.MOD_ID, id));
    }

    public static void registerLootTables(){
    }
}
