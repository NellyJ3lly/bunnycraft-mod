package net.bunnycraft.mixin.screen;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenMixin extends ForgingScreenHandler  {

    private final Property levelCost;
    private int repairItemUsage;
    private String newItemName;

    public AnvilScreenMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.ANVIL, syncId, inventory, context);
        this.levelCost = Property.create();
        this.addProperty(this.levelCost);
    }

    @Override
    public void updateResult() {

    }

    public int getNextCost(int cost) {
        return (int)Math.min((long)cost * 2L + 1L, 2147483647L);
    }

    @Override
    public boolean canTakeOutput(PlayerEntity player, boolean present) {
        return (player.isInCreativeMode() || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0;
    }

    @Override
    public void onTakeOutput(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            player.addExperienceLevels(-this.levelCost.get());
        }

        this.input.setStack(0, ItemStack.EMPTY);
        if (this.repairItemUsage > 0) {
            ItemStack itemStack = this.input.getStack(1);
            if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
                itemStack.decrement(this.repairItemUsage);
                this.input.setStack(1, itemStack);
            } else {
                this.input.setStack(1, ItemStack.EMPTY);
            }
        } else {
            this.input.setStack(1, ItemStack.EMPTY);
        }

        this.levelCost.set(0);
        this.context.run((world, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            if (!player.isInCreativeMode() && blockState.isIn(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.12F) {
                BlockState blockState2 = AnvilBlock.getLandingState(blockState);
                if (blockState2 == null) {
                    world.removeBlock(pos, false);
                    world.syncWorldEvent(1029, pos, 0);
                } else {
                    world.setBlockState(pos, blockState2, 2);
                    world.syncWorldEvent(1030, pos, 0);
                }
            } else {
                world.syncWorldEvent(1030, pos, 0);
            }

        });
    }

    @Override
    public boolean canUse(BlockState state) {
        return state.isIn(BlockTags.ANVIL);
    }

    @Override
    public ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create().input(0, 27, 47, (stack) -> true).input(1, 76, 47, (stack) -> true).output(2, 134, 47).build();
    }
}
