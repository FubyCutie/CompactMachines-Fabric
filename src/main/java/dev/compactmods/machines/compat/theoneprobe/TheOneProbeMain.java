package dev.compactmods.machines.compat.theoneprobe;

import dev.compactmods.machines.compat.theoneprobe.overrides.CompactMachineNameOverride;
import dev.compactmods.machines.compat.theoneprobe.providers.CompactMachineProvider;
import dev.compactmods.machines.compat.theoneprobe.providers.TunnelProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbePlugin;

import java.util.function.Function;

public class TheOneProbeMain implements ITheOneProbePlugin {
    @Override
    public void onLoad(ITheOneProbe PROBE) {
        PROBE.registerBlockDisplayOverride(new CompactMachineNameOverride());
        PROBE.registerProvider(new CompactMachineProvider());
        PROBE.registerProvider(new TunnelProvider());
    }

}
