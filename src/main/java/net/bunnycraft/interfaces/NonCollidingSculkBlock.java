package net.bunnycraft.interfaces;

import net.bunnycraft.item.ModTools;
import net.bunnycraft.util.ModTags;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Unique;

public interface NonCollidingSculkBlock {

    default int getEchoArmor(PlayerEntity playerEntity) {
        int EchoArmorAmount = 0;

        for (ItemStack stack : playerEntity.getArmorItems()) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial().getIdAsString().equals("bunnycraft:echo")) {
                    EchoArmorAmount++;
                }
            }
        }

        return EchoArmorAmount;
    }

    default boolean getSculkItem(PlayerEntity playerEntity) {
        boolean hasSculkCane = playerEntity.getStackInHand(Hand.MAIN_HAND).isOf(ModTools.SCULK_CANE) || playerEntity.getStackInHand(Hand.OFF_HAND).isOf(ModTools.SCULK_CANE);

        return hasSculkCane || getEchoArmor(playerEntity) > 0;
    }

    default boolean checkIfPlayerIsInSculk(BlockView world, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            if (entityShapeContext.getEntity() instanceof PlayerEntity player) {
                boolean insideSculk = world.getBlockState(player.getBlockPos()).isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS) || world.getBlockState(player.getBlockPos().add(0,1,0)).isIn(ModTags.Blocks.COLLIDABLE_SCULK_BLOCKS);
                return (getSculkItem(player) && player.isSneaking()) || insideSculk;
            }
        }
        return false;
    }

    default Vec3d getMoveSpeedInSculk(Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            switch (getEchoArmor(playerEntity)) {
                case 1 : {return new Vec3d(1.05F,1.05F,1.05F);}
                case 2 : {return new Vec3d(1.25F,1.25F,1.25F);}
                case 3 : {return new Vec3d(1.5F,1.5F,1.5F);}
                case 4 : {return new Vec3d(2F,2F,2F);}
            };
        }

        return new Vec3d(0.9F,1F,0.9F);
    }
}
