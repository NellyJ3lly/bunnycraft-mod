package net.bunnycraft.entity.custom;

import net.bunnycraft.entity.ModEntities;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.item.custom.SpearItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpearEntity extends PersistentProjectileEntity {

    //TODO loyalty beyond functionality: if the entity is destroyed it inserts the item back into the players inventory
    // if the player dies the item follows into their inventory after respawning
    // if the entity goes below the bottom of the world (the void in the end) activate normal loyalty and begin coming back
    // buff the loyalty return speed and make it bounce right off the ground rather than stick as if you hit a mob

    private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> FIRE_ASPECT = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private float Damage;
    private boolean dealtDamage;
    public static final TrackedData<Byte> MATERIAL = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BYTE);
    //private ItemStack originalStack;

    //helps register the entity i think
    public SpearEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);


    }



    //creates the entity from the item
    public SpearEntity(World world, LivingEntity owner, ItemStack stack, SpearItem spearItem) {


        super(ModEntities.SPEAR, owner, world, stack, null);
        this.dataTracker.set(LOYALTY, this.getLoyalty(stack));
        this.dataTracker.set(FIRE_ASPECT, this.getFireAspect(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());

        Damage = spearItem.getToolMaterial().getAttackDamage() + ModTools.spearDmg;

        for(int i = 0; ModTools.getSpear(i) != null; i++) {

            if (ModTools.getSpear(i) == stack.getItem()) {
                this.dataTracker.set(MATERIAL, (byte) i);
                break; // no need to keep checking
            }
        }
        //used by the renderer to figure out which texture to use, doing it this way makes it so you cant load custom models based on material but that could possibly be changed idk how tho


    }


    //idk what this does but its also called from SpearItem, maybe spawning from commands? dont remove
    public SpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.SPEAR, x, y, z, world, stack, stack);
        this.dataTracker.set(LOYALTY, this.getLoyalty(stack));
        this.dataTracker.set(FIRE_ASPECT, this.getFireAspect(stack));
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }


    //initializes the tracked variables so they can be accessed from anywhere and be updated
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LOYALTY, (byte)0);
        builder.add(FIRE_ASPECT, (byte)0);
        builder.add(ENCHANTED, false);
        builder.add(MATERIAL, (byte)0);


    }




    //the tick function, mostly used for loyalty
    @Override
    public void tick() {
        //checks if the trident has fallen to the ground
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }


        //loyalty stuff copied from the trident class
        Entity entity = this.getOwner();
        int i = this.dataTracker.get(LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoClip()) && entity != null) {
            if (!this.isOwnerAlive()) {

                if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();

            } else {
                this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());

                //makes it target below eyes so i dont get jumpscared by a spear in my face
                vec3d = new Vec3d(vec3d.getX(), vec3d.getY() - 0.5f, vec3d.getZ());

                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * i, this.getZ());
                if (this.getWorld().isClient) {
                this.lastRenderY = this.getY();
                }

                double d = 0.05 * i;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));

            }
        }

        super.tick();
    }

    //hits entities method copied then modified from the trident entity class
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {

        Entity entity = entityHitResult.getEntity();
        // sets to the base damage if theres no enchants
        float finalDamage = Damage;
        //gets the owner
        Entity entity2 = this.getOwner();

        //damage source stuff
        DamageSource damageSource = this.getDamageSources().thrown(this, (Entity)(entity2 == null ? this : entity2));
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            //i think this line is for enchants that modify the damage
            finalDamage = EnchantmentHelper.getDamage(serverWorld, Objects.requireNonNull(this.getWeaponStack()), entity, damageSource, Damage);
        }

        if (this.dataTracker.get(FIRE_ASPECT) != 0 && !this.getWorld().isClient() && entity.getType() != EntityType.ENDERMAN) {
            entity.setOnFireFor(this.dataTracker.get(FIRE_ASPECT) * 4); // 4 is the fire aspect time in the enchantment file
        }

        this.dealtDamage = true;
        if (entity.damage(damageSource, finalDamage)) {
            //doesnt deal damage to endermen
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }





            //i think this is for enchants
            if (this.getWorld() instanceof ServerWorld serverWorld) {

                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack());

            }

            //knocks back the entity
            if (entity instanceof LivingEntity livingEntity) {



                this.knockback(livingEntity, damageSource);

                //calls the onhit method of the entity so it can react
                this.onHit(livingEntity);


            }
        }

        this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    //almost exactly the same as persistent projectile entity, but replaced this.weapon with this.getWeaponStack which fixed knockback enchant doingn nothing
    protected void knockback(LivingEntity target, DamageSource source) {
        double d = this.getWeaponStack() != null && this.getWorld() instanceof ServerWorld serverWorld
                ? EnchantmentHelper.modifyKnockback(serverWorld, Objects.requireNonNull(this.getWeaponStack()), target, source, 0.0F)
                : 0.0F;
        if (d > 0.0) {
            double e = Math.max(0.0, 1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply(d * 0.6 * e);
            if (vec3d.lengthSquared() > 0.0) {
                target.addVelocity(vec3d.x, 0.1, vec3d.z);
            }
        }
    }

    @Nullable
    @Override
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onBlockHitEnchantmentEffects(ServerWorld world, BlockHitResult blockHitResult, ItemStack weaponStack) {
        Vec3d vec3d = blockHitResult.getBlockPos().clampToWithin(blockHitResult.getPos());
        EnchantmentHelper.onHitBlock(
                world,
                weaponStack,
                this.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null,
                this,
                null,
                vec3d,
                world.getBlockState(blockHitResult.getBlockPos()),
                item -> this.kill()
        );
    }





        //ovverides age to not do anything so as to not despawn your precious netherite spear

    @Override
    public void age() {
        int i = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PickupPermission.ALLOWED || i <= 0) {
            super.age();
        }
    }

    //-------------------------------------------------------------------------------------------------------Helper methods

    //helper method for getting the loyalty, i think getTridentReturnAcceleration is just the loyalty level
    private byte getLoyalty(ItemStack stack) {
        return this.getWorld() instanceof ServerWorld serverWorld
                ? (byte) MathHelper.clamp(EnchantmentHelper.getTridentReturnAcceleration(serverWorld, stack, this), 0, 127)
                : 0;
    }

//checks the level of fire aspect because apparently EnchantmentHelper.getLevel doesnt work
    public byte getFireAspect(ItemStack stack){
        for (RegistryEntry<Enchantment> enchantments : stack.getEnchantments().getEnchantments()){
            if (enchantments.toString().contains(Enchantments.FIRE_ASPECT.getValue().toString())){
                return (byte) ((byte) stack.getEnchantments().getLevel(enchantments));
            }
        }
        return (byte)0;
    }


    //helper method for checking if the owner is alive or not (or is in spectator)
    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        return entity == null || !entity.isAlive() ? false : !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return switch (this.getDataTracker().get(MATERIAL)) {
            case 0 -> new ItemStack(ModTools.WOODEN_SPEAR);
            case 1 -> new ItemStack(ModTools.STONE_SPEAR);
            case 2 -> new ItemStack(ModTools.COPPER_SPEAR);
            case 3 -> new ItemStack(ModTools.IRON_SPEAR);
            case 4 -> new ItemStack(ModTools.GOLDEN_SPEAR);
            case 5 -> new ItemStack(ModTools.DIAMOND_SPEAR);
            case 6 -> new ItemStack(ModTools.NETHERITE_SPEAR);
            default -> null;
        };
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dealtDamage = nbt.getBoolean("DealtDamage");
        this.dataTracker.set(LOYALTY, this.getLoyalty(this.getItemStack()));
        this.dataTracker.set(FIRE_ASPECT, this.getFireAspect(this.getItemStack()));
        this.dataTracker.set(MATERIAL, nbt.getByte("Material"));
    }


    //picks up the spear and adds a stack if not in creative
    //TODO rewrite this so only the spears owner can pick it up if the spear has loyalty, pretty sure i fixed but check again anyway
    @Override
    protected boolean tryPickup(PlayerEntity player) {
        if (player.isInCreativeMode()) {
            return true;
        } else if (player.getInventory().containsAny(itemStack -> itemStack.isOf(ModItems.EMPTY_SPEAR_SLOT))) {

            if (player.getInventory().getSlotWithStack(ModItems.EMPTY_SPEAR_SLOT.getDefaultStack()) == -1 && this.isOwner(player) && this.isNoClip()) {
                player.getInventory().offHand.set(0, this.asItemStack());
                return true;
            } else if (this.isOwner(player) && this.isNoClip()) {
                player.getInventory().setStack(player.getInventory().getSlotWithStack(ModItems.EMPTY_SPEAR_SLOT.getDefaultStack()), this.asItemStack());
                return true;
            }
            return false;


        } else {
            return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
        }
    }


    //nbt data, i think this is called when stuff is saved and read is called when stuff is loaded, anything that persists should go in both
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("DealtDamage", this.dealtDamage);
        nbt.putByte("Material", this.dataTracker.get(MATERIAL));

    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

}
