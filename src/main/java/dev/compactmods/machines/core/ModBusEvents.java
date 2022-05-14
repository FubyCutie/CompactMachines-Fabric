package dev.compactmods.machines.core;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.advancement.AdvancementTriggers;
import dev.compactmods.machines.network.NetworkHandler;

public class ModBusEvents {

    public static void setup() {
        CompactMachines.LOGGER.trace("Initializing network handler.");
        NetworkHandler.initialize();

        CompactMachines.LOGGER.trace("Registering advancement triggers.");
        AdvancementTriggers.init();
    }
}
