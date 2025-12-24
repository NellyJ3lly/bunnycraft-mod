package net.bunnycraft.block.entity;

import net.bunnycraft.networking.SculkBatteryS2CPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class SculkBatteryBlockEntity extends BlockEntity {
    private int experienceLevel;
    private int totalExperience;
    private float experienceProgress;
    private int clientExperienceLevel = 0;
    private int clientTotalExperience = 0;

    public SculkBatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SCULK_BATTERY_BLOCK_ENTITY, pos, state);

    }

    public int getMaxLevel() {
        return 60;
    }

    public int getMaxExperience() {
        return 9062;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("Levels", this.experienceLevel);
        nbt.putInt("TotalExperience", this.totalExperience);
        nbt.putFloat("Progress", this.experienceProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.experienceLevel = nbt.getInt("Levels");
        this.totalExperience = nbt.getInt("TotalExperience");
        this.experienceProgress = nbt.getInt("Progress");

        this.clientExperienceLevel = nbt.getInt("Levels");
        this.clientTotalExperience = nbt.getInt("TotalExperience");

        updateDisplays();
    }

    public int getExperienceLevels() {
        return this.experienceLevel;
    }
    public int getTotalExperience() {
        return this.totalExperience;
    }
    public int convertLevelsToExperience(int Levels) {
        int TotalExperience = 0;
        for (int Level = 0; Level <= Levels; Level++) {
            TotalExperience += getLevelExperience(Level);
        }

        return TotalExperience;
    }

    @Override
    public void markDirty() {
        assert this.getWorld() != null;
        if (!this.getWorld().isClient) {
            updateDisplays();
        }

        super.markDirty();
    }

    public void addExperienceLevels(int levels) {
        this.experienceLevel += levels;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experienceProgress = 0.0f;
            this.totalExperience = 0;
        }
        if (levels > 0 && this.experienceLevel % 5 == 0) {
            float f = this.experienceLevel > 30 ? 1.0f : (float)this.experienceLevel / 30.0f;
        }
        this.markDirty();
    }

    public void addExperience(int experience) {
        this.experienceProgress += (float)experience / (float)this.getNextLevelExperience();
        this.totalExperience = MathHelper.clamp(this.totalExperience + experience, 0, Integer.MAX_VALUE);
        while (this.experienceProgress < 0.0f) {
            float f = this.experienceProgress * (float)this.getNextLevelExperience();
            if (this.experienceLevel > 0) {
                this.addExperienceLevels(-1);
                this.experienceProgress = 1.0f + f / (float)this.getNextLevelExperience();
                continue;
            }
            this.addExperienceLevels(-1);
            this.experienceProgress = 0.0f;
        }
        while (this.experienceProgress >= 1.0f) {
            this.experienceProgress = (this.experienceProgress - 1.0f) * (float)this.getNextLevelExperience();
            this.addExperienceLevels(1);
            this.experienceProgress /= (float)this.getNextLevelExperience();
        }
        this.markDirty();
    }

    public int getNextLevelExperience() {
        if (this.experienceLevel >= 30) {
            return 112 + (this.experienceLevel - 30) * 9;
        }
        if (this.experienceLevel >= 15) {
            return 37 + (this.experienceLevel - 15) * 5;
        }
        return 7 + this.experienceLevel * 2;
    }

    public int getLevelExperience(int Level) {
        if (Level >= 30) {
            return 112 + (Level - 30) * 9;
        }
        if (Level >= 15) {
            return 37 + (Level - 15) * 5;
        }
        return 7 + Level * 2;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        assert this.getWorld() != null;

        return createNbt(registryLookup);
    }


    private void updateDisplays() {
        if (this.getWorld() instanceof ServerWorld server) {
            SculkBatteryS2CPayload payload = new SculkBatteryS2CPayload(this.getExperienceLevels(),this.getTotalExperience(), this.getPos());

            for (ServerPlayerEntity player : PlayerLookup.world( server )) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    public void setClientExperienceLevels(int Levels) {
        this.clientExperienceLevel = Levels;
    }
    public void setClientTotalExperience(int TotalExperience) {
        this.clientTotalExperience = TotalExperience;
    }

    public int getClientTotalExperience() {
        return this.clientTotalExperience;
    }

}
