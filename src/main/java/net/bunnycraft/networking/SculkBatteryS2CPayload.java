package net.bunnycraft.networking;

import net.bunnycraft.Bunnycraft;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SculkBatteryS2CPayload(int experienceLevel, int totalExperience, BlockPos pos) implements CustomPayload {
    public static final Identifier SCULK_BATTERY_PAYLOAD_ID = Identifier.of(Bunnycraft.MOD_ID, "sculk_battery");
    public static final Id<SculkBatteryS2CPayload> ID = new Id<>(SCULK_BATTERY_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, SculkBatteryS2CPayload> CODEC = PacketCodec.of(SculkBatteryS2CPayload::write, SculkBatteryS2CPayload::read);

    private static SculkBatteryS2CPayload read(RegistryByteBuf registryByteBuf) {
        return new SculkBatteryS2CPayload(registryByteBuf.readInt(), registryByteBuf.readInt(), registryByteBuf.readBlockPos());
    }

    private void write(RegistryByteBuf registryByteBuf) {
        registryByteBuf.writeInt(experienceLevel);
        registryByteBuf.writeInt(totalExperience);
        registryByteBuf.writeBlockPos(pos);
    }



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
