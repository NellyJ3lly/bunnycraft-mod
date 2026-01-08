package net.bunnycraft.mixin.entity;

import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public class SheepShearsTypeMixin extends AnimalEntity implements Shearable {
    public SheepShearsTypeMixin(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Unique
    public ItemStack ShearUsed = null;

    @Inject(
            method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void Bunnycraft$ChangeToTag(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ModTags.Items.SHEAR_ENCHANTABLE)) {
            if (!this.getWorld().isClient && this.isShearable()) {
                this.ShearUsed = itemStack;
                cir.cancel();
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, getSlotForHand(hand));
                cir.setReturnValue(ActionResult.success(true));
            }
        }
    }

    @ModifyConstant(
            method = "sheared",
            constant = @Constant(intValue = 1,ordinal = 1)
    )
    public int Bunnycraft$SteelShearsMore(int originalValue) {
        if (this.ShearUsed == null) {return originalValue;}

        if (this.ShearUsed.isOf(ModTools.STEEL_SHEARS)) {
            return 2 + this.random.nextInt(3);
        } else {
            return originalValue;
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
