package dev.compactmods.machines.room;

import dev.compactmods.machines.api.room.IRoomInformation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

public record RoomInformation(ServerLevel level, ChunkPos chunk, RoomSize size)
        implements IRoomInformation {
    @Override
    public void readFromNbt(CompoundTag tag) {

    }

    @Override
    public void writeToNbt(CompoundTag tag) {

    }
}
