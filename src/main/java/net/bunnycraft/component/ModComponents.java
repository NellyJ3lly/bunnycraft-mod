package net.bunnycraft.component;

import com.mojang.serialization.Codec;
import net.bunnycraft.Bunnycraft;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.Block;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final ComponentType<Boolean> CAN_CLIMB_ON_BLOCK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bunnycraft.MOD_ID, "can_climb_on_block"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Boolean> HORIZONTAL_COLLISION = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bunnycraft.MOD_ID, "horizontal_collision"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Float> ECHO_FUEL = register("echo_fuel", floatBuilder -> floatBuilder.codec(Codec.FLOAT));

    public static final ComponentType<String> BLOCK_FILTER = register("block_filter", stringBuilder -> stringBuilder.codec(Codec.STRING));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Bunnycraft.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerComponents() {

        Bunnycraft.LOGGER.info("Registering Components for" + Bunnycraft.MOD_ID);
    }
}