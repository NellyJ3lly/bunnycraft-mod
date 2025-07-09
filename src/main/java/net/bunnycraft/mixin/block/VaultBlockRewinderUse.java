package net.bunnycraft.mixin.block;

import net.bunnycraft.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.enums.VaultState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.VaultBlock.VAULT_STATE;

@Mixin(VaultBlock.class)
public class VaultBlockRewinderUse {

    @Inject(
          method = "Lnet/minecraft/block/VaultBlock;onUseWithItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ItemActionResult;",
          at = @At("HEAD")
    )
    public void onUseWithRewinder(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        if (!stack.isEmpty() && state.get(VAULT_STATE) == VaultState.INACTIVE) {
            if (stack.isOf(ModItems.VAULT_REWINDER)) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)world;
                    BlockEntity var10 = serverWorld.getBlockEntity(pos);
                    if (var10 instanceof VaultBlockEntity) {
                        VaultBlockEntity vaultBlockEntity = (VaultBlockEntity)var10;
                        VaultBlockEntity.Server.tryUnlock(serverWorld, pos, state, vaultBlockEntity.getConfig(), vaultBlockEntity.getServerData(), vaultBlockEntity.getSharedData(), player, stack);
                    }
                }
            }
        }
    }

    public ItemActionResult onUseWithRewinderSwing(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        if (!stack.isEmpty() && state.get(VAULT_STATE) == VaultState.INACTIVE) {
            if (stack.isOf(ModItems.VAULT_REWINDER)) {
                return ItemActionResult.SUCCESS;
            }
        }
        return ItemActionResult.FAIL;
    }
}
