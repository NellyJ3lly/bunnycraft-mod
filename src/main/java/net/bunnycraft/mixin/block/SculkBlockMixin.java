package net.bunnycraft.mixin.block;

import net.bunnycraft.block.ModBlocks;
import net.bunnycraft.interfaces.SpreadableBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.MultifaceGrowthBlock.hasDirection;

@Mixin(SculkBlock.class)
public abstract class SculkBlockMixin extends ExperienceDroppingBlock implements SculkSpreadable, SpreadableBlock {

    public SculkBlockMixin(IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        if (world.getBlockState(pos.add(0,1,0)).isOf(Blocks.OAK_SAPLING)) {
            world.setBlockState(pos.add(0,1,0),ModBlocks.SCULK_WOOD_SAPLING.getDefaultState(),3);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();

        this.LoopPositions(world,cursor.getPos().add(0,1,0));


        return i;

//        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
//            BlockPos blockPos = cursor.getPos();
//            boolean bl = blockPos.isWithinDistance(catalystPos, (double)spreadManager.getMaxDistance());
//            if (!bl && shouldNotDecay(world, blockPos)) {
//                int j = spreadManager.getExtraBlockChance();
//                if (random.nextInt(j) < i) {
//                    BlockPos blockPos2 = blockPos.up();
//                    BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());
//                    world.setBlockState(blockPos2, blockState, 3);
//                    world.playSound((PlayerEntity)null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
//                }
//
//                return Math.max(0, i - j);
//            } else {
//                return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
//            }
//        } else {
//            return i;
//        }
    }
}
