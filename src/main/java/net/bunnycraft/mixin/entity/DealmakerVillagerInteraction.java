package net.bunnycraft.mixin.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.bunnycraft.item.armor.ModArmorItem;
import net.bunnycraft.item.armor.ModArmors;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.*;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiPredicate;

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
