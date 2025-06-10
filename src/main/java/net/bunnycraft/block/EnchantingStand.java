package net.bunnycraft.block;

import com.mojang.serialization.MapCodec;
import net.bunnycraft.block.entity.ModBlockEntities;
import net.bunnycraft.block.entity.custom.EnchantingStandEntity;
import net.bunnycraft.block.entity.custom.EnchantingStandScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Nameable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantingStand extends BlockWithEntity {
    public static final MapCodec<EnchantingStand> CODEC = createCodec(EnchantingStand::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final List<BlockPos> POWER_PROVIDER_OFFSETS = BlockPos.stream(-2, 0, -2, 2, 1, 2)
            .filter(pos -> Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2)
            .map(BlockPos::toImmutable)
            .toList();

    @Override
    public MapCodec<EnchantingStand> getCodec() {
        return CODEC;
    }

    public EnchantingStand(AbstractBlock.Settings settings) {
        super(settings);
    }

    public static boolean canAccessPowerProvider(World world, BlockPos tablePos, BlockPos providerOffset) {
        return world.getBlockState(tablePos.add(providerOffset)).isIn(BlockTags.ENCHANTMENT_POWER_PROVIDER)
                && world.getBlockState(tablePos.add(providerOffset.getX() / 2, providerOffset.getY(), providerOffset.getZ() / 2))
                .isIn(BlockTags.ENCHANTMENT_POWER_TRANSMITTER);
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        for (BlockPos blockPos : POWER_PROVIDER_OFFSETS) {
            if (random.nextInt(16) == 0 && canAccessPowerProvider(world, pos, blockPos)) {
                world.addParticle(
                        ParticleTypes.ENCHANT,
                        pos.getX() + 0.5,
                        pos.getY() + 2.0,
                        pos.getZ() + 0.5,
                        blockPos.getX() + random.nextFloat() - 0.5,
                        blockPos.getY() - random.nextFloat() - 1.0F,
                        blockPos.getZ() + random.nextFloat() - 0.5
                );
            }
        }
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchantingStandEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntities.ENCHANTING_STAND_ENTITY, EnchantingStandEntity::tick) : null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EnchantingStandEntity) {
            Text text = ((Nameable)blockEntity).getDisplayName();

            /*
            return new SimpleNamedScreenHandlerFactory(
                    (syncId, inventory, player) -> new EnchantingStandScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), text
            );*/

            return new ExtendedScreenHandlerFactory<>() {


                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new EnchantingStandScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
                }

                @Override
                public Text getDisplayName() {
                    return text;
                }

                @Override
                public Object getScreenOpeningData(ServerPlayerEntity player) {
                    return pos;
                }
            };
        } else {
            return null;
        }
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
