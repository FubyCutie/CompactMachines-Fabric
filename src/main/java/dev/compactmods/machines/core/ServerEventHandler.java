package dev.compactmods.machines.core;

import dev.compactmods.machines.api.room.upgrade.ILevelLoadedUpgradeListener;
import dev.compactmods.machines.dimension.Dimension;
import dev.compactmods.machines.upgrade.RoomUpgradeManager;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;

import java.util.stream.Collectors;

public class ServerEventHandler {

    public static void onWorldLoaded(MinecraftServer server, ServerLevel sl) {
        if(sl.dimension().equals(Dimension.COMPACT_DIMENSION))
        {
            final var serv = sl.getServer();
            final var owBorder = serv.overworld().getWorldBorder();
            final var cwBorder = sl.getWorldBorder();

            final var levelUpgrades = RoomUpgradeManager.get(sl);
            levelUpgrades.implementing(ILevelLoadedUpgradeListener.class).forEach(inst -> {
                final var upg = inst.upgrade();
                upg.onLevelLoaded(sl, inst.room());
            });

            // Filter border listeners down to the compact world, then remove them from the OW listener list
            final var listeners = owBorder.listeners.stream()
                    .filter(border -> border instanceof BorderChangeListener.DelegateBorderChangeListener)
                    .map(BorderChangeListener.DelegateBorderChangeListener.class::cast)
                    .filter(list -> list.worldBorder == cwBorder)
                    .collect(Collectors.toSet());

            for(var listener : listeners)
                owBorder.removeListener(listener);

            // Fix set compact world border if it was loaded weirdly
            cwBorder.setCenter(0, 0);
            cwBorder.setSize(WorldBorder.MAX_SIZE);
            PlayerLookup.world(sl).forEach(serverPlayer -> {
                ServerPlayNetworking.getSender(serverPlayer).sendPacket(new ClientboundSetBorderSizePacket(cwBorder));
            });
        }
    }

    public static void onPlayerLogin(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        final var player = handler.getPlayer();
        if(player.level.dimension().equals(Dimension.COMPACT_DIMENSION)) {
            // Send a fake world border to the player instead of the "real" one in overworld
            player.connection.send(new ClientboundInitializeBorderPacket(new WorldBorder()));
        }
    }

    public static void onPlayerDimChange(ServerPlayer sp, ServerLevel origin, ServerLevel destination) {
        if(destination.dimension().equals(Dimension.COMPACT_DIMENSION)) {
            // Send a fake world border to the player instead of the "real" one in overworld
            sp.connection.send(new ClientboundInitializeBorderPacket(new WorldBorder()));
        }
    }
}
