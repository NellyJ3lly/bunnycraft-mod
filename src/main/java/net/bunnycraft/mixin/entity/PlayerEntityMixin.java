package net.bunnycraft.mixin.entity;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.bunnycraft.block.entity.BunnyBankInventory;
import net.bunnycraft.interfaces.IMyPlayerEntity;
import net.bunnycraft.item.ModArmors;
import net.bunnycraft.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//player mixin is used to detect when a player drops the spear slot reserver, and deletes it

@Mixin (PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IMyPlayerEntity {
    private final PlayerAbilities abilities = new PlayerAbilities();

    public BunnyBankInventory bunnyBankInventory = new BunnyBankInventory();

    @Unique
    private static final byte hoeSweepRange = 4; // change this to tweak how far the hoe sweeps, sword default is 1


    @Unique
    public BunnyBankInventory bunnycraft_mod$getBunnyBankInventory() {
        return this.bunnyBankInventory;
    }

    @Inject(
            method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("TAIL")
    )
    public void writeBunnyBankData(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("BunnyBankItems",this.bunnyBankInventory.toNbtList(this.getRegistryManager()));
    }

    @Inject(
            method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("TAIL")
    )
    public void readBunnyBankData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("BunnyBankItems", NbtElement.LIST_TYPE)) {
            this.bunnyBankInventory.readNbtList(nbt.getList("BunnyBankItems", NbtElement.COMPOUND_TYPE), this.getRegistryManager());
        }
    }

    @Inject(
            method = "Lnet/minecraft/entity/player/PlayerEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("TAIL")
    )
    public void getBunnyBankStackReference(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("BunnyBank",this.bunnyBankInventory.toNbtList(this.getRegistryManager()));
    }


    //this doesnt matter and should be ignored, simply gives me access to protected variables in LivingEntity
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
            at = @At(value = "HEAD"),
            cancellable = true)

    public void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.isOf(ModItems.EMPTY_SPEAR_SLOT)) {
            cir.setReturnValue(null);
        }
    }

    @Unique
    private boolean wearingDivingSuit() {
        ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = this.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = this.getEquippedStack(EquipmentSlot.FEET);

        return
                feet.isOf(ModArmors.DIVING_BOOTS) || helmet.isOf(ModArmors.DIVING_LEGGINGS)
                        || chest.isOf(ModArmors.DIVING_CHESTPLATE) || legs.isOf(ModArmors.DIVING_HELMET);
    }

    @ModifyReturnValue(
            method = "Lnet/minecraft/entity/player/PlayerEntity;isSwimming()Z",
            at = @At("RETURN")
    )
    public boolean isSwimming(boolean original) {
        return original && !this.wearingDivingSuit();
    }



    @Shadow
    protected abstract float getDamageAgainst(Entity target, float baseDamage, DamageSource damageSource);

    @Inject(
            method = "attack",
            at = @At(value = "HEAD"),
            cancellable = true)
    public void attack(Entity target, CallbackInfo ci) { // kinda copies the original method but if its a hoe does extra stuff and cancels the original method
        // not in my scope rn but we could probably change it to use a modifyreturnvalue or something else
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof HoeItem) {
            ci.cancel();

            if (target.isAttackable()) {
                if (!target.handleAttack(player)) {

                    float f = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                    ItemStack itemStack = player.getWeaponStack();
                    DamageSource damageSource = player.getDamageSources().playerAttack(player);
                    float g = this.getDamageAgainst(target, f, damageSource) - f;
                    float h = player.getAttackCooldownProgress(0.5F);
                    f *= 0.2F + h * h * 0.8F;
                    g *= h;
                    player.resetLastAttackedTicks();
                    if (target.getType().isIn(EntityTypeTags.REDIRECTABLE_PROJECTILE)
                            && target instanceof ProjectileEntity projectileEntity
                            && projectileEntity.deflect(ProjectileDeflection.REDIRECTED, player, player, true)) {
                        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory());
                    } else {
                        if (f > 0.0F || g > 0.0F) {
                            boolean bl = h > 0.9F;
                            boolean bl2;
                            if (player.isSprinting() && bl) {
                                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
                                bl2 = true;
                            } else {
                                bl2 = false;
                            }

                            f += itemStack.getItem().getBonusAttackDamage(target, f, damageSource);
                            boolean bl3 = bl
                                    && player.fallDistance > 0.0F
                                    && !player.isOnGround()
                                    && !player.isClimbing()
                                    && !player.isTouchingWater()
                                    && !player.hasStatusEffect(StatusEffects.BLINDNESS)
                                    && !player.hasVehicle()
                                    && target instanceof LivingEntity
                                    && !player.isSprinting();
                            if (bl3) {
                                f *= 1.5F;
                            }

                            float i = f + g;

                            byte bl4 = 0; // changed from boolean to byte so i can track what item was used, the number will corespond to the bounding box expansion
                            double d = player.horizontalSpeed - player.prevHorizontalSpeed;
                            if (bl && !bl3 && !bl2 && player.isOnGround() && d < player.getMovementSpeed()) {
                                ItemStack itemStack2 = player.getStackInHand(Hand.MAIN_HAND);
                                if (itemStack2.getItem() instanceof HoeItem) { // added this if
                                    bl4 = hoeSweepRange;
                                }
                            }

                            float j = 0.0F;
                            if (target instanceof LivingEntity livingEntity) {
                                j = livingEntity.getHealth();
                            }

                            Vec3d vec3d = target.getVelocity();
                            boolean bl5 = target.damage(damageSource, i);
                            if (bl5) {
                                float k = this.getKnockbackAgainst(target, damageSource) + (bl2 ? 1.0F : 0.0F);
                                if (k > 0.0F) {
                                    if (target instanceof LivingEntity livingEntity2) {
                                        livingEntity2.takeKnockback(
                                                k * 0.5F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)), -MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))
                                        );
                                    } else {
                                        target.addVelocity(
                                                -MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)) * k * 0.5F, 0.1, MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0)) * k * 0.5F
                                        );
                                    }

                                    player.setVelocity(player.getVelocity().multiply(0.6, 1.0, 0.6));
                                    player.setSprinting(false);
                                }

                                if (bl4 != 0) { // changed condition from checking if bl4 is true (originally was a boolean)
                                    float l = 1.0F + (float) player.getAttributeValue(EntityAttributes.PLAYER_SWEEPING_DAMAGE_RATIO) * f;

                                    for (LivingEntity livingEntity3 : player.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(bl4, 1, bl4))) { // changed bounding box numbers to use bl4 which is increased if its a hoe
                                        if (livingEntity3 != player
                                                && livingEntity3 != target
                                                && !player.isTeammate(livingEntity3)
                                                && (!(livingEntity3 instanceof ArmorStandEntity) || !((ArmorStandEntity) livingEntity3).isMarker())
                                                && player.squaredDistanceTo(livingEntity3) < player.getAttributeValue(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE) + 4) { // changed from 9 to get the entity interaction range
                                            float m = this.getDamageAgainst(livingEntity3, l, damageSource) * h;
                                            livingEntity3.takeKnockback(
                                                    0.4F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)), -MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))
                                            );
                                            livingEntity3.damage(damageSource, m);
                                            if (player.getWorld() instanceof ServerWorld serverWorld) {
                                                EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity3, damageSource);
                                            }
                                        }
                                    }

                                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                                    player.spawnSweepAttackParticles();
                                }

                                if (target instanceof ServerPlayerEntity && target.velocityModified) {
                                    ((ServerPlayerEntity) target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                                    target.velocityModified = false;
                                    target.setVelocity(vec3d);
                                }

                                if (bl3) {
                                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
                                    player.addCritParticles(target);
                                }

                                if (!bl3 && bl4 == 0) { // changed from !bl3 && !bl4
                                    if (bl) {
                                        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
                                    } else {
                                        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);
                                    }
                                }

                                if (g > 0.0F) {
                                    player.addEnchantedHitParticles(target);
                                }

                                player.onAttacking(target);
                                Entity entity = target;
                                if (target instanceof EnderDragonPart) {
                                    entity = ((EnderDragonPart) target).owner;
                                }

                                boolean bl6 = false;
                                if (player.getWorld() instanceof ServerWorld serverWorld2) {
                                    if (entity instanceof LivingEntity livingEntity3x) {
                                        bl6 = itemStack.postHit(livingEntity3x, player);
                                    }

                                    EnchantmentHelper.onTargetDamaged(serverWorld2, target, damageSource);
                                }

                                if (!player.getWorld().isClient && !itemStack.isEmpty() && entity instanceof LivingEntity) {
                                    if (bl6) {
                                        itemStack.postDamageEntity((LivingEntity) entity, player);
                                    }

                                    if (itemStack.isEmpty()) {
                                        if (itemStack == player.getMainHandStack()) {
                                            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                                        } else {
                                            player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
                                        }
                                    }
                                }

                                if (target instanceof LivingEntity) {
                                    float n = j - ((LivingEntity) target).getHealth();
                                    player.increaseStat(Stats.DAMAGE_DEALT, Math.round(n * 10.0F));
                                    if (player.getWorld() instanceof ServerWorld && n > 2.0F) {
                                        int o = (int) (n * 0.5);
                                        ((ServerWorld) player.getWorld())
                                                .spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), o, 0.1, 0.0, 0.1, 0.2);
                                    }
                                }

                                player.addExhaustion(0.1F);
                            } else {
                                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }
                    }
                }
            }
        }
    }
}



//    @Overwrite
//    public void bunnycraft_hoesweep(Entity target) {
//
//        // this line is revolutionary, it lets me call methods from the class im mixing into
//        PlayerEntity player = (PlayerEntity) (Object) this;
//
//        //player is all copied from PlayerEntity class except commented part
//        if (target.isAttackable()) {
//            if (!target.handleAttack(player)) {
//                float f = player.isUsingRiptide() ? this.riptideAttackDamage : (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
//                ItemStack itemStack = player.getWeaponStack();
//                DamageSource damageSource = player.getDamageSources().playerAttack(player);
//                float g = this.getDamageAgainst(target, f, damageSource) - f;
//                float h = player.getAttackCooldownProgress(0.5F);
//                f *= 0.2F + h * h * 0.8F;
//                g *= h;
//                player.resetLastAttackedTicks();
//                if (target.getType().isIn(EntityTypeTags.REDIRECTABLE_PROJECTILE)
//                        && target instanceof ProjectileEntity projectileEntity
//                        && projectileEntity.deflect(ProjectileDeflection.REDIRECTED, player, player, true)) {
//                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory());
//                } else {
//                    if (f > 0.0F || g > 0.0F) {
//                        boolean bl = h > 0.9F;
//                        boolean bl2;
//                        if (player.isSprinting() && bl) {
//                            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
//                            bl2 = true;
//                        } else {
//                            bl2 = false;
//                        }
//
//                        f += itemStack.getItem().getBonusAttackDamage(target, f, damageSource);
//                        boolean bl3 = bl
//                                && player.fallDistance > 0.0F
//                                && !player.isOnGround()
//                                && !player.isClimbing()
//                                && !player.isTouchingWater()
//                                && !player.hasStatusEffect(StatusEffects.BLINDNESS)
//                                && !player.hasVehicle()
//                                && target instanceof LivingEntity
//                                && !player.isSprinting();
//                        if (bl3) {
//                            f *= 1.5F;
//                        }
//
//                        float i = f + g;
//
//
//                        byte bl4 = 0; // changed from boolean to byte so i can track what item was used, the number will corespond to the bounding box expansion
//                        double d = player.horizontalSpeed - player.prevHorizontalSpeed;
//                        if (bl && !bl3 && !bl2 && player.isOnGround() && d < player.getMovementSpeed()) {
//                            ItemStack itemStack2 = player.getStackInHand(Hand.MAIN_HAND);
//                            if (itemStack2.getItem() instanceof SwordItem) {
//                                bl4 = 1; // changed from setting to false
//                            }
//                            if(itemStack2.getItem() instanceof HoeItem) { // added this if
//                                bl4 = hoeSweepRange;
//                            }
//                        }
//
//                        float j = 0.0F;
//                        if (target instanceof LivingEntity livingEntity) {
//                            j = livingEntity.getHealth();
//                        }
//
//                        Vec3d vec3d = target.getVelocity();
//                        boolean bl5 = target.damage(damageSource, i);
//                        if (bl5) {
//                            float k = this.getKnockbackAgainst(target, damageSource) + (bl2 ? 1.0F : 0.0F);
//                            if (k > 0.0F) {
//                                if (target instanceof LivingEntity livingEntity2) {
//                                    livingEntity2.takeKnockback(
//                                            k * 0.5F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)), -MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))
//                                    );
//                                } else {
//                                    target.addVelocity(
//                                            -MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)) * k * 0.5F, 0.1, MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0)) * k * 0.5F
//                                    );
//                                }
//
//                                player.setVelocity(player.getVelocity().multiply(0.6, 1.0, 0.6));
//                                player.setSprinting(false);
//                            }
//
//                            if (bl4 != 0) { // changed condition from checking if bl4 is true (originally was a boolean)
//                                float l = 1.0F + (float)player.getAttributeValue(EntityAttributes.PLAYER_SWEEPING_DAMAGE_RATIO) * f;
//
//                                for (LivingEntity livingEntity3 : player.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(bl4, 0.25, bl4))) { // changed bounding box numbers to use bl4 which is increased if its a hoe
//                                    if (livingEntity3 != player
//                                            && livingEntity3 != target
//                                            && !player.isTeammate(livingEntity3)
//                                            && (!(livingEntity3 instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity3).isMarker())
//                                            && player.squaredDistanceTo(livingEntity3) < player.getAttributeValue(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE) + 4) { // changed from 9 to get the entity interaction range
//                                        float m = this.getDamageAgainst(livingEntity3, l, damageSource) * h;
//                                        livingEntity3.takeKnockback(
//                                                0.4F, MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)), -MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0))
//                                        );
//                                        livingEntity3.damage(damageSource, m);
//                                        if (player.getWorld() instanceof ServerWorld serverWorld) {
//                                            EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity3, damageSource);
//                                        }
//                                    }
//                                }
//
//                                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
//                                player.spawnSweepAttackParticles();
//                            }
//
//                            if (target instanceof ServerPlayerEntity && target.velocityModified) {
//                                ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
//                                target.velocityModified = false;
//                                target.setVelocity(vec3d);
//                            }
//
//                            if (bl3) {
//                                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
//                                player.addCritParticles(target);
//                            }
//
//                            if (!bl3 && bl4 == 0) { // changed from !bl3 && !bl4
//                                if (bl) {
//                                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
//                                } else {
//                                    player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);
//                                }
//                            }
//
//                            if (g > 0.0F) {
//                                player.addEnchantedHitParticles(target);
//                            }
//
//                            player.onAttacking(target);
//                            Entity entity = target;
//                            if (target instanceof EnderDragonPart) {
//                                entity = ((EnderDragonPart)target).owner;
//                            }
//
//                            boolean bl6 = false;
//                            if (player.getWorld() instanceof ServerWorld serverWorld2) {
//                                if (entity instanceof LivingEntity livingEntity3x) {
//                                    bl6 = itemStack.postHit(livingEntity3x, player);
//                                }
//
//                                EnchantmentHelper.onTargetDamaged(serverWorld2, target, damageSource);
//                            }
//
//                            if (!player.getWorld().isClient && !itemStack.isEmpty() && entity instanceof LivingEntity) {
//                                if (bl6) {
//                                    itemStack.postDamageEntity((LivingEntity)entity, player);
//                                }
//
//                                if (itemStack.isEmpty()) {
//                                    if (itemStack == player.getMainHandStack()) {
//                                        player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
//                                    } else {
//                                        player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
//                                    }
//                                }
//                            }
//
//                            if (target instanceof LivingEntity) {
//                                float n = j - ((LivingEntity)target).getHealth();
//                                player.increaseStat(Stats.DAMAGE_DEALT, Math.round(n * 10.0F));
//                                if (player.getWorld() instanceof ServerWorld && n > 2.0F) {
//                                    int o = (int)(n * 0.5);
//                                    ((ServerWorld)player.getWorld())
//                                            .spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), o, 0.1, 0.0, 0.1, 0.2);
//                                }
//                            }
//
//                            player.addExhaustion(0.1F);
//                        } else {
//                            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);
//                        }
//                    }
//                }
//            }
//        }
//    }

