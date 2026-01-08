package net.bunnycraft.sound;

import net.bunnycraft.Bunnycraft;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent POINT_LIGHT = registerSoundEvent("point_light");
    public static final RegistryKey<JukeboxSong> POINT_LIGHT_KEY = RegistryKey.of(
            RegistryKeys.JUKEBOX_SONG, Identifier.of(Bunnycraft.MOD_ID,"point_light"));

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Bunnycraft.MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }

    public static void registerSounds() {
        Bunnycraft.LOGGER.info("Registering Sounds for " + Bunnycraft.MOD_ID);
    }
}
