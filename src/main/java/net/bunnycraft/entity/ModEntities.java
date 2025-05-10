package net.bunnycraft.entity;


import net.bunnycraft.Bunnycraft;
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
                    .dimensions(2, 2).build());

    //dummy event for initializing the class
    public static void registerModEntities() {
        Bunnycraft.LOGGER.info("Registering Mod Entities for" + Bunnycraft.MOD_ID);
    }
}
