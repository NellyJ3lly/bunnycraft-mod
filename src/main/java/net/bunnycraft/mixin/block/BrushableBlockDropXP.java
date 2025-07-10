package net.bunnycraft.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlockEntity.class)
public class BrushableBlockDropXP extends BlockEntity {

    public BrushableBlockDropXP(BlockEntityType<?> type, BlockPos pos, BlockState state, IntProvider experienceDropped) {
        super(type, pos, state);
    }

    @Inject(
            method = "Lnet/minecraft/block/entity/BrushableBlockEntity;finishBrushing(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At("HEAD")
    )
    public void addXPAfterBrushing(PlayerEntity player, CallbackInfo ci) {
        if (this.world != null && this.world.getServer() != null) {
            IntProvider size = UniformIntProvider.create(1,5);
            int size2 = size.get(Random.create());
            ExperienceOrbEntity.spawn((ServerWorld) this.world, Vec3d.ofCenter(pos), size2);
        }
    }
}
