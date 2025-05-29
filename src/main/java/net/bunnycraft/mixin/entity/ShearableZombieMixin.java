package net.bunnycraft.mixin.entity;


import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieEntity.class)
public abstract class ShearableZombieMixin extends HostileEntity {

    boolean brushed = false;
    public boolean brushzombie() {
        if (this.isBaby() || this.brushed) {
            return false;
        } else {
            World lootTable = this.getWorld();
            if (lootTable instanceof ServerWorld serverWorld) {
                LootTable lootTable2 = serverWorld.getServer().getReloadableRegistries().getLootTable(ModLootTables.ZOMBIE_SHEARED);
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder(serverWorld)).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.THIS_ENTITY, this).build(LootContextTypes.SHEARING);
                ObjectListIterator var4 = lootTable2.generateLoot(lootContextParameterSet).iterator();

                while(var4.hasNext()) {
                    ItemStack itemStack = (ItemStack)var4.next();
                    this.dropStack(itemStack, this.getHeight());
                }
            }
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

    protected ShearableZombieMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if ((itemStack.isOf(Items.SHEARS) || itemStack.isOf(ModTools.STEEL_SHEARS)) && this.brushzombie()) {
            if (itemStack.isOf(Items.SHEARS)) {
                itemStack.damage(4, player, getSlotForHand(hand));
            } else { itemStack.damage(1, player, getSlotForHand(hand)); }
            this.brushed = true;
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return ActionResult.FAIL;
        }
    }
}
