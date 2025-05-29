package net.bunnycraft.mixin;


import net.bunnycraft.item.ModTools;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ZombieEntity.class)
public abstract class Brushable_Zombie extends HostileEntity {

    boolean brushed = false;
    public boolean brushzombie() {
        if (this.isBaby() || this.brushed) {
            return false;
        } else {
            this.dropStack(new ItemStack(Items.ROTTEN_FLESH));
            this.dropStack(new ItemStack(Items.BLUE_WOOL));
            this.dropStack(new ItemStack(Items.PURPLE_WOOL));
            this.emitGameEvent(GameEvent.ENTITY_INTERACT);
            this.playSoundIfNotSilent(SoundEvents.ENTITY_ZOMBIE_HURT);

            convertWhenBrushed();
            return true;
        }
    }
    
    protected void convertWhenBrushed() {
        if (!this.isSilent()) {
            this.getWorld().syncWorldEvent((PlayerEntity)null, 1, this.getBlockPos(), 0);
        }
        this.convertTo(EntityType.SKELETON);
    }

    public void convertTo(EntityType<? extends SkeletonEntity> entityType) {
        SkeletonEntity zombieEntity = (SkeletonEntity)this.convertTo(entityType, true);
    }

    protected Brushable_Zombie(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    // make it so shears will take more durability while steel shears take less
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if ((itemStack.isOf(Items.SHEARS) || itemStack.isOf(ModTools.STEEL_SHEARS)) && this.brushzombie()) {
            itemStack.damage(8, player, getSlotForHand(hand));
            this.brushed = true;
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return ActionResult.FAIL;
        }
    }
}
