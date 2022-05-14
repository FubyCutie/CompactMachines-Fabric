package dev.compactmods.machines.compat.theoneprobe;

import dev.compactmods.machines.compat.theoneprobe.providers.CompactMachineProvider;
import dev.compactmods.machines.compat.theoneprobe.providers.TunnelProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbePlugin;

public class TheOneProbeMain implements ITheOneProbePlugin {
    @Override
    public void onLoad(ITheOneProbe PROBE) {
        PROBE.registerProvider(new CompactMachineProvider());
        PROBE.registerProvider(new TunnelProvider());
    }
}
