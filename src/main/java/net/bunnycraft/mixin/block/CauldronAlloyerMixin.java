package net.bunnycraft.mixin.block;

//this class modifys the empty cauldron and turns it into the alloyer. i did this instead of a new cauldron state because the alloyer would use the same behavior map as the empty cauldron

import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.custom.CauldronAlloyerEntity;
import net.bunnycraft.interfaces.CauldronAlloyerHeatInterface;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin (CauldronBlock.class)
public class CauldronAlloyerMixin extends Block implements BlockEntityProvider, CauldronAlloyerHeatInterface {

    //lets me gain access to the protected method setDefaultState within CauldronBlocks parent class Block
    @Mixin(value = Block.class, remap = false)
    public interface BlockInvoker {
        @Invoker("setDefaultState")
        void invokeSetDefaultState(BlockState state);
    }

    //adds the heat property to the cauldrons blockstate
    @Unique
    private static final IntProperty bunnycraft$HEAT = IntProperty.of("heat", 0, 6);
    @Override
    public IntProperty bunnycraft$getHEAT() {
        return bunnycraft$HEAT;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(bunnycraft$HEAT);
    }
    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectDefaultState(AbstractBlock.Settings settings, CallbackInfo ci) {
        //allows access to init methods
        Block thisBlock = (Block) (Object) this;

        ((BlockInvoker) this).invokeSetDefaultState(thisBlock.getDefaultState().with(bunnycraft$HEAT, 0));
    }

    public CauldronAlloyerMixin(Settings settings) {

        super(settings);

        setDefaultState(this.stateManager.getDefaultState().with(bunnycraft$HEAT, 0));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CauldronAlloyerEntity(pos, state);
    }

    //tells the game to not render the block so the custom renderer can do that
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // This tells Minecraft to skip rendering the default block model
        // and hand off all rendering responsibility to the BlockEntityRenderer.
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    //overrides

    /**
     * @author Dognmyface
     * @reason prevents the cauldron from filling with water while your trying to alloy something
     */
    @Overwrite
    public static boolean canFillWithPrecipitation(World world, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return world.getRandom().nextFloat() < 0.05F;
        } else {
            return precipitation == Biome.Precipitation.SNOW ? world.getRandom().nextFloat() < 0.1F : false;
        }
    }


    //called when a player uses this cauldron.
    @Unique
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {



        if (player.getMainHandStack().isEmpty()) {
            CauldronAlloyerEntity block = getBlockEntity(world, pos);
            block.use(null, player, world); // calling this with null returns all items from the cauldron
            player.swingHand(player.getActiveHand());
        }

        return ActionResult.PASS;
    }

    @Unique
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        //checks if the item is an ingot used for alloying
        if (!stack.isEmpty() && !player.isInSneakingPose()) {
            if (getBlockEntity(world, pos).use(stack, player, world)) { // checks if it can pass to deefault cauldron interaction, will not pass if the items were not returned

                //if the item is not in the alloy list do default cauldron behavior
                CauldronBehavior cauldronBehavior = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map().get(stack.getItem());
                return cauldronBehavior.interact(state, world, pos, player, hand, stack);
            } else {
                //i want to prevent block placement here but its not working
                return ItemActionResult.SUCCESS;
            }

        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            CauldronAlloyerEntity blockEntity = (CauldronAlloyerEntity) world.getBlockEntity(pos);
            if(blockEntity != null) {
                blockEntity.clearItemDisplays();
                ItemScatterer.spawn(world, pos, blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }


    @Unique
    protected boolean canBeFilledByDripstone(Fluid fluid) {
        return false;
    }


    //helper method
    @Unique
    private static CauldronAlloyerEntity getBlockEntity(World world, BlockPos pos) {

        if (world == null || pos == null) {
            return null;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof CauldronAlloyerEntity) {
            return (CauldronAlloyerEntity) blockEntity;
        } else {
            return null;
        }

    }



    @Unique
    protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (world.isClient()) {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof CauldronAlloyerEntity cauldronAlloyer) {
            cauldronAlloyer.tryAlloy();
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean movedByPiston) {
        if (world.isClient()) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof CauldronAlloyerEntity cauldronAlloyer) {
            cauldronAlloyer.tryAlloy();
        }
        super.neighborUpdate(state, world, pos, block, neighborPos, movedByPiston);
    }
        @Nullable
    @Unique
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()) {
            return checkType(type, ModBlockEntities.CAULDRON_ALLOYER_ENTITY,
                    (currentWorld, pos, currentBlockState, blockEntity) -> blockEntity.clientTick());
        }
        return checkType(type, ModBlockEntities.CAULDRON_ALLOYER_ENTITY,
                (currentWorld, pos, currentBlockState, blockEntity) -> blockEntity.serverTick());
    }

    @SuppressWarnings("unchecked") // the cast is nessesary
    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
            BlockEntityType<A> givenType,
            BlockEntityType<E> expectedType,
            BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
