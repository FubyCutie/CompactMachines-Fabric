package dev.compactmods.machines.core;

import com.mojang.brigadier.CommandDispatcher;
import dev.compactmods.machines.command.CMCommandRoot;
import dev.compactmods.machines.command.data.CMDataSubcommand;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.stream.Collectors;

public class ServerEventHandler {

    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        CMCommandRoot.register(dispatcher);
        CMDataSubcommand.make();
    }

    @SubscribeEvent
    public static void onWorldLoaded(final WorldEvent.Load evt) {
        if(evt.getWorld() instanceof ServerLevel sl && sl.dimension().equals(Registration.COMPACT_DIMENSION))
        {
            final var serv = sl.getServer();
            final var owBorder = serv.overworld().getWorldBorder();
            final var cwBorder = sl.getWorldBorder();

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
            PacketDistributor.DIMENSION.with(() -> Registration.COMPACT_DIMENSION)
                    .send(new ClientboundSetBorderSizePacket(cwBorder));

        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent evt) {
        final var player = evt.getPlayer();
        if(player.level.dimension().equals(Registration.COMPACT_DIMENSION) && player instanceof ServerPlayer sp) {
            // Send a fake world border to the player instead of the "real" one in overworld
            sp.connection.send(new ClientboundInitializeBorderPacket(new WorldBorder()));
        }
    }

    @SubscribeEvent
    public static void onPlayerDimChange(final PlayerEvent.PlayerChangedDimensionEvent evt) {
        if(evt.getTo().equals(Registration.COMPACT_DIMENSION)) {
            final var player = evt.getPlayer();
            if(player instanceof ServerPlayer sp) {
                // Send a fake world border to the player instead of the "real" one in overworld
                sp.connection.send(new ClientboundInitializeBorderPacket(new WorldBorder()));
            }
        }
    }
}
