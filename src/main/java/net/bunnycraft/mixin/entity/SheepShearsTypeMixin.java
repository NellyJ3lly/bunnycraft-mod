package net.bunnycraft.mixin.entity;

import net.bunnycraft.util.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SheepEntity.class)
public class SheepShearsTypeMixin extends AnimalEntity implements Shearable {
    public SheepShearsTypeMixin(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }
    /**
     * @author nellybean
     * @reason I'm gonna use an inject later but this just makes it so sheep are sheared with more types of shears
     */
    @Overwrite
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ModTags.Items.SHEAR_ENCHANTABLE)) {
            if (!this.getWorld().isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, getSlotForHand(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Shadow
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Shadow
    public void sheared(SoundCategory shearedSoundCategory) {

    }

    @Shadow
    public boolean isShearable() {
        return false;
    }
}
