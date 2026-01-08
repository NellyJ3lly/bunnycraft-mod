package net.bunnycraft.mixin.entity;

import net.bunnycraft.block.SculkSpreadableBlock;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {

    @Shadow public abstract ActionResult interact(PlayerEntity player, Hand hand);

    protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "canStayAttached()Z",
            at = @At("HEAD"),
            cancellable = true)
    public void MakeItemFrameStayonSculk(CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = this.getWorld().getBlockState(this.attachedBlockPos.offset(this.facing.getOpposite()));
        if (blockState.isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    @Shadow
    protected Box calculateBoundingBox(BlockPos pos, Direction side) {
        return null;
    }

    @Shadow
    public void onPlace() {

    }

    @Shadow
    public void onBreak(@Nullable Entity breaker) {

    }

    @Shadow
    protected void initDataTracker(DataTracker.Builder builder) {

    }
}
