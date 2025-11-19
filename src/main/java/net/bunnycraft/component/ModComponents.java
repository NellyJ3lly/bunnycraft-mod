package net.bunnycraft.component;

import com.mojang.serialization.Codec;
import net.bunnycraft.Bunnycraft;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final ComponentType<Boolean> CAN_CLIMB_ON_BLOCK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bunnycraft.MOD_ID, "can_climb_on_block"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Boolean> DAMAGE_WHILE_CLIMBING = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bunnycraft.MOD_ID, "damage_while_climbing"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void registerComponents() {
        Bunnycraft.LOGGER.info("Registering Components for" + Bunnycraft.MOD_ID);
    }
}