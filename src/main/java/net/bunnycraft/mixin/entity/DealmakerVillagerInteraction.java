package net.bunnycraft.mixin.entity;

import net.bunnycraft.item.ModArmors;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class DealmakerVillagerInteraction extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    public DealmakerVillagerInteraction(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "Lnet/minecraft/entity/passive/VillagerEntity;prepareOffersFor(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At("TAIL")
    )
    private void BunnycraftAddDealmakerBonusToVillager(PlayerEntity player, CallbackInfo ci) {
        if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(ModArmors.DEALMAKER)) {
            for(TradeOffer tradeOffer2 : this.getOffers()) {
                double d = 0.3 + (double)0.0625F;
                int k = (int)Math.floor(d * (double)tradeOffer2.getOriginalFirstBuyItem().getCount());
                tradeOffer2.increaseSpecialPrice(-Math.max(k, 1));
            }
        }

    }

    @Shadow
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Shadow
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Shadow
    protected void afterUsing(TradeOffer offer) {

    }

    @Shadow
    protected void fillRecipes() {

    }

    @Shadow
    public void onInteractionWith(EntityInteraction interaction, Entity entity) {

    }

    @Shadow
    public VillagerData getVillagerData() {
        return null;
    }

    @Shadow
    public void setVillagerData(VillagerData villagerData) {

    }
}
