package net.bunnycraft.util;

import net.bunnycraft.Bunnycraft;
import net.bunnycraft.block.entity.custom.EnchantingStandScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {

    public static final PacketCodec<PacketByteBuf, BlockPos> BLOCK_POS_PACKET_CODEC = PacketCodec.ofStatic(
            (buf, pos) -> buf.writeBlockPos(pos),
            buf -> buf.readBlockPos()
    );


    public static final ScreenHandlerType<EnchantingStandScreenHandler> ENCHANTING_STAND_SCREEN_HANDLER_TYPE =
            Registry.register(Registries.SCREEN_HANDLER,
                    Identifier.of(Bunnycraft.MOD_ID, "enchanting_stand_screen_handler"),
                    new ExtendedScreenHandlerType<>(
                            EnchantingStandScreenHandler::new,
                            BLOCK_POS_PACKET_CODEC
                    )
            );

    public static void registerModScreenHandlers() {
        Bunnycraft.LOGGER.info("registering Screen Handlers for " + Bunnycraft.MOD_ID);

    }

}
