package dev.compactmods.machines.room.network;

import dev.compactmods.machines.dimension.MissingDimensionException;
import dev.compactmods.machines.room.Rooms;
import dev.compactmods.machines.room.exceptions.NonexistentRoomException;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.ChunkPos;

import java.util.function.Supplier;

public record PlayerStartedRoomTrackingPacket(ChunkPos room) implements C2SPacket {

    public PlayerStartedRoomTrackingPacket(FriendlyByteBuf buf) {
        this(buf.readChunkPos());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeChunkPos(room);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            try {
                var blocks = Rooms.getInternalBlocks(player.server, room);
                RoomNetworkHandler.CHANNEL.sendToClient(new InitialRoomBlockDataPacket(blocks), player);
            } catch (MissingDimensionException | NonexistentRoomException e) {
                e.printStackTrace();
            }
        });
    }
}
