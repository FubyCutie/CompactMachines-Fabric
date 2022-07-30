package dev.compactmods.machines.room.network;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.location.LevelBlockPosition;
import dev.compactmods.machines.dimension.MissingDimensionException;
import dev.compactmods.machines.util.PlayerUtil;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.ChunkPos;

public record PlayerRequestedTeleportPacket(LevelBlockPosition machine, ChunkPos room) implements C2SPacket {

    public PlayerRequestedTeleportPacket(FriendlyByteBuf buf) {
        this(buf.readWithCodec(LevelBlockPosition.CODEC), buf.readChunkPos());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeWithCodec(LevelBlockPosition.CODEC, machine);
        buf.writeChunkPos(room);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            try {
                PlayerUtil.teleportPlayerIntoMachine(player.level, player, machine.getBlockPosition());
            } catch (MissingDimensionException e) {
                CompactMachines.LOGGER.error("Failed to teleport player into machine.", e);
            }
        });
    }
}
