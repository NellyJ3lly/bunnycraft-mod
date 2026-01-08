package net.bunnycraft.item.custom;

import net.minecraft.item.Item;

public class BunnycoinItem extends Item {
    public BunnycoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxCount() {
        return 128;
    }
}
