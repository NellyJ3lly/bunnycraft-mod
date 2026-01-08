package net.bunnycraft.world.tree;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator SCULKWOOD = new SaplingGenerator(Bunnycraft.MOD_ID + ":sculkwood",
            Optional.empty(),Optional.of(ModConfiguredFeatures.SCULKWOOD_KEY),Optional.empty());
}
