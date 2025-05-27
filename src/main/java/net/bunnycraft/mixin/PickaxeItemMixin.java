package net.bunnycraft.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin {

    //dont let intellij lie to you, all these class that say they are never used, override in the target mixin class

    //copied from the mace class overrides pickaxeItems superclass
    @Unique
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        Entity var5 = damageSource.getSource();
        if (var5 instanceof LivingEntity livingEntity) {
            if (!shouldDealAdditionalDamage(livingEntity)) {
                return 0.0F;
            } else {
                float h = livingEntity.fallDistance * .5f; // half as good as mace
                float i;

                //the damage falloff calculation
                if (h <= 3.0F) {
                    i = 4.0F * h;
                } else if (h <= 8.0F) {
                    i = 12.0F + 2.0F * (h - 3.0F);
                } else {
                    i = 22.0F + h - 8.0F;
                }

                World var10 = livingEntity.getWorld();
                if (var10 instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)var10;
                    return i + EnchantmentHelper.getSmashDamagePerFallenBlock(serverWorld, livingEntity.getWeaponStack(), target, damageSource, 0.0F) * h;
                } else {
                    return i;
                }
            }
        } else {
            return 0.0F;
        }
    }

    //copied from mace overrides pickaxeItems superclass
    @Unique
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity serverPlayerEntity && shouldDealAdditionalDamage(serverPlayerEntity)) {
            ServerWorld serverWorld = (ServerWorld)attacker.getWorld();
            if (serverPlayerEntity.shouldIgnoreFallDamageFromCurrentExplosion() && serverPlayerEntity.currentExplosionImpactPos != null) {
                if (serverPlayerEntity.currentExplosionImpactPos.y > serverPlayerEntity.getPos().y) {
                    serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                }
            } else {
                serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
            }

            serverPlayerEntity.setIgnoreFallDamageFromCurrentExplosion(true);
            serverPlayerEntity.setVelocity(serverPlayerEntity.getVelocity().withAxis(Direction.Axis.Y, 0.01F));
            serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
            if (target.isOnGround()) {
                //serverPlayerEntity.setSpawnExtraParticlesOnFall(false);
                SoundEvent soundEvent = SoundEvents.ITEM_MACE_SMASH_GROUND;
                serverWorld.playSound(
                        null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), soundEvent, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F
                );
            } else {
                serverWorld.playSound(
                        null,
                        serverPlayerEntity.getX(),
                        serverPlayerEntity.getY(),
                        serverPlayerEntity.getZ(),
                        SoundEvents.ITEM_MACE_SMASH_AIR,
                        serverPlayerEntity.getSoundCategory(),
                        1.0F,
                        1.0F
                );
            }
        }

        return true;
    }

    @Unique
    private static boolean shouldDealAdditionalDamage(LivingEntity attacker) {
        return attacker.fallDistance > 1.5F && !attacker.isFallFlying();
    }

    //also overrides
    @Unique
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        if (shouldDealAdditionalDamage(attacker)) {
            attacker.onLanding();
        }

    }
}
