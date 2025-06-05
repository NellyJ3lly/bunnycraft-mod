package net.bunnycraft.entity;


import net.bunnycraft.Bunnycraft;
import net.bunnycraft.entity.custom.AlloyLiquidEntity;
import net.bunnycraft.entity.custom.SpearEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {


    //registers the spear entity
    public static final EntityType<SpearEntity> SPEAR = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "spear_entity"),
            EntityType.Builder.<SpearEntity>create(SpearEntity::new, SpawnGroup.MISC)
                    .dimensions(1.5f, 1.5f).build());

    //registers the spear entity
    public static final EntityType<SpearEntity> ROSE_GOLD_SPEAR = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "rose_gold_spear_entity"),
            EntityType.Builder.<SpearEntity>create(SpearEntity::new, SpawnGroup.MISC)
                    .dimensions(1.5f, 1.5f).build());

    public static final EntityType<AlloyLiquidEntity> ALLOY_LIQUID_ENTITY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Bunnycraft.MOD_ID, "alloy_liquid_entity"),
            EntityType.Builder.<AlloyLiquidEntity>create(AlloyLiquidEntity::new, SpawnGroup.MISC)
                    .dimensions(1, .1f).build());

    //dummy event for initializing the class
    public static void registerModEntities() {
        Bunnycraft.LOGGER.info("Registering Mod Entities for" + Bunnycraft.MOD_ID);
    }
}
