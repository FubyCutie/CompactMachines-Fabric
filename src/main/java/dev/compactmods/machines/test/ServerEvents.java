package dev.compactmods.machines.test;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.core.Registration;
import dev.compactmods.machines.util.DimensionUtil;
import net.minecraft.server.MinecraftServer;

public class ServerEvents {

    public static void onServerStarted(final MinecraftServer serv) {
        var compactLevel = serv.getLevel(Registration.COMPACT_DIMENSION);
        if (compactLevel == null) {
            CompactMachines.LOGGER.warn("Compact dimension not found; recreating it.");
            DimensionUtil.createAndRegisterWorldAndDimension(serv);
        }
    }
}
