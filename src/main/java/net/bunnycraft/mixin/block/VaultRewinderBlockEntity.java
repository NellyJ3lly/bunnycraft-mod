package net.bunnycraft.mixin.block;

import net.bunnycraft.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.enums.VaultState;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VaultBlockEntity.Server.class)
public class VaultRewinderBlockEntity {

    @Inject(
            method = "Lnet/minecraft/block/entity/VaultBlockEntity$Server;tryUnlock(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/vault/VaultConfig;Lnet/minecraft/block/vault/VaultServerData;Lnet/minecraft/block/vault/VaultSharedData;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD")
    )
    private static void TryRewind(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (serverData.hasRewardedPlayer(player) && stack.isOf(ModItems.VAULT_REWINDER)) {
            serverData.rewardedPlayers.remove(player.getUuid());
            stack.decrementUnlessCreative(1, player);
        }
    }

    @Shadow
    private static boolean canBeUnlocked(VaultConfig config, VaultState state) {
        return config.lootTable() != LootTables.EMPTY && !config.keyItem().isEmpty() && state != VaultState.INACTIVE;
    }
}
