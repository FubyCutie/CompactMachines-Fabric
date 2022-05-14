package dev.compactmods.machines.network;

import dev.compactmods.machines.CompactMachines;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "4.0.0";
    public static final SimpleChannel MAIN_CHANNEL = new SimpleChannel(
            new ResourceLocation(CompactMachines.MOD_ID, "main"));

    public static void initialize() {
        MAIN_CHANNEL.initServerListener();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            MAIN_CHANNEL.initClientListener();
        MAIN_CHANNEL.registerS2CPacket(TunnelAddedPacket.class, 1);
    }
}
