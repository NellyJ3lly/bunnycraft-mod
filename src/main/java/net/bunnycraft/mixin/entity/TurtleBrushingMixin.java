package net.bunnycraft.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ArmadilloEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import static net.minecraft.entity.LivingEntity.getSlotForHand;

@Mixin(TurtleEntity.class)
public class TurtleBrushingMixin extends AnimalEntity {

    protected TurtleBrushingMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.TURTLE_FOOD);
    }

    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {return (PassiveEntity)EntityType.TURTLE.create(world);}

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BRUSH) && this.brushScute()) {
            itemStack.damage(8, player, getSlotForHand(hand));
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return ActionResult.FAIL;
        }
    }

    public boolean brushScute() {
        if (this.isBaby()) {
            return false;
        } else {
            this.dropStack(new ItemStack(Items.TURTLE_SCUTE));
            this.emitGameEvent(GameEvent.ENTITY_INTERACT);
            this.playSoundIfNotSilent(SoundEvents.ENTITY_ARMADILLO_BRUSH);
            return true;
        }
    }
}
