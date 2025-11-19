package net.bunnycraft.networking;

import net.bunnycraft.Bunnycraft;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record HorizontalCollisionPayload(boolean horizontalCollision) implements CustomPayload {
    public static final Identifier CLIMBING_CLAW_COLLISION_PAYLOAD_ID = Identifier.of(Bunnycraft.MOD_ID, "climbing_claw_collision");
    public static final CustomPayload.Id<HorizontalCollisionPayload> ID = new CustomPayload.Id<>(CLIMBING_CLAW_COLLISION_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, HorizontalCollisionPayload> CODEC = PacketCodec.of(HorizontalCollisionPayload::write, HorizontalCollisionPayload::read);
    public static final Type<RegistryByteBuf, HorizontalCollisionPayload> TYPE = new CustomPayload.Type<>(ID,CODEC);



    private static HorizontalCollisionPayload read(RegistryByteBuf registryByteBuf) {
        return new HorizontalCollisionPayload(registryByteBuf.readBoolean());
    }

    private void write(RegistryByteBuf registryByteBuf) {
        registryByteBuf.writeBoolean(horizontalCollision);
    }


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
