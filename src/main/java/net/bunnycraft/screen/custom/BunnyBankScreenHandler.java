package net.bunnycraft.screen.custom;

import net.bunnycraft.block.entity.BunnyBankEntity;
import net.bunnycraft.block.entity.BunnyBankInventory;
import net.bunnycraft.interfaces.IMyPlayerEntity;
import net.bunnycraft.item.ModItems;
import net.bunnycraft.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BunnyBankScreenHandler extends ScreenHandler {
    private final BunnyBankInventory inventory;

    public BunnyBankScreenHandler(int syncId,PlayerInventory playerInventory) {
        super(ModScreenHandlers.BUNNY_BANK_SCREEN_HANDLER, syncId);
        IMyPlayerEntity playerEntity = (IMyPlayerEntity) playerInventory.player;
        this.inventory = playerEntity.bunnycraft_mod$getBunnyBankInventory();

        this.addSlot(new Slot(inventory,0,46,35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.BUNNYCENT);
            }
        });
        this.addSlot(new Slot(inventory,1,69,35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.COPPER_BUNNYCOIN);
            }
        });
        this.addSlot(new Slot(inventory,2,91,35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.GOLD_BUNNYCOIN);
            }
        });
        this.addSlot(new Slot(inventory,3,113,35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.DIAMOND_BUNNYCOIN);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public BunnyBankScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId,playerInventory);
    }



    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }



    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        System.out.println(slot);
        return  false;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
