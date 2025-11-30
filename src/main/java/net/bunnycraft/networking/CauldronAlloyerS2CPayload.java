package net.bunnycraft.networking;

import net.bunnycraft.Bunnycraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

public record CauldronAlloyerS2CPayload(int[] items, int fluidColor, float fluidLevel, BlockPos pos) implements CustomPayload {
    public static final Identifier CAULDRON_ALLOYER_PAYLOAD_ID = Identifier.of(Bunnycraft.MOD_ID, "cauldron_alloyer");
    public static final CustomPayload.Id<CauldronAlloyerS2CPayload> ID = new CustomPayload.Id<>(CAULDRON_ALLOYER_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, CauldronAlloyerS2CPayload> CODEC = PacketCodec.of(CauldronAlloyerS2CPayload::write, CauldronAlloyerS2CPayload::read);

    private static CauldronAlloyerS2CPayload read(RegistryByteBuf registryByteBuf) {
        return new CauldronAlloyerS2CPayload(registryByteBuf.readIntArray(),registryByteBuf.readInt(), registryByteBuf.readFloat(), registryByteBuf.readBlockPos());
    }

    private void write(RegistryByteBuf registryByteBuf) {
        registryByteBuf.writeIntArray(items);
        registryByteBuf.writeInt(fluidColor);
        registryByteBuf.writeFloat(fluidLevel);
        registryByteBuf.writeBlockPos(pos);
    }



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
