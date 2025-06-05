package net.bunnycraft.entity.custom;

import net.bunnycraft.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class AlloyLiquidEntity extends Entity {

    private static final TrackedData<Integer> HUE_COLOR = DataTracker.registerData(AlloyLiquidEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public AlloyLiquidEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public AlloyLiquidEntity(World world, double x, double y, double z, int color) {
        this(ModEntities.ALLOY_LIQUID_ENTITY, world);
        this.setPosition(x, y, z);
        this.getDataTracker().set(HUE_COLOR, color);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

        builder.add(HUE_COLOR, 0xFFFFFF); // defaults to white

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }


    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    public void setHueColor(int color) {
        this.getDataTracker().set(HUE_COLOR, color);
    }

    public int getHueColor() {
        return this.getDataTracker().get(HUE_COLOR);
    }


}
