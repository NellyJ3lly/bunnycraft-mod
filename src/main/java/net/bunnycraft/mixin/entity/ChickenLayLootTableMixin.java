package net.bunnycraft.mixin.entity;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.bunnycraft.util.ModLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenLayLootTableMixin extends AnimalEntity {
    @Shadow
    public int eggLayTime;
    @Shadow
    public boolean hasJockey;

    protected ChickenLayLootTableMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public boolean hasJockey() {
        return this.hasJockey;
    }

    @Inject(
            method = "tickMovement()V",
            at = @At("HEAD")
    )
    public void Bunnycraft$EggLayingAddition(CallbackInfo ci) {
        if (!this.getWorld().isClient && this.isAlive() && !this.isBaby() && !this.hasJockey() && --this.eggLayTime <= 0) {
            World lootTable = this.getWorld();
            if (lootTable instanceof ServerWorld serverWorld) {
                LootTable lootTable2 = serverWorld.getServer().getReloadableRegistries().getLootTable(ModLootTables.CHICKEN_LAY_EGG);
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder(serverWorld)).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.THIS_ENTITY, this).build(LootContextTypes.SHEARING);

                for (ItemStack itemStack : lootTable2.generateLoot(lootContextParameterSet)) {
                    this.dropStack(itemStack, this.getHeight());
                }
            }
        }
    }
}
