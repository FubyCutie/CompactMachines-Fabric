package dev.compactmods.machines.api.tunnels.capability;

import com.google.common.collect.ImmutableSet;
import dev.compactmods.machines.api.tunnels.lifecycle.InstancedTunnel;
import dev.compactmods.machines.api.tunnels.lifecycle.TunnelInstance;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;

public interface CapabilityTunnel<Tunnel extends TunnelInstance> extends InstancedTunnel<Tunnel> {

    ImmutableSet<StorageType> getSupportedCapabilities();

    /**
     * Fetch a capability instance from a tunnel.
     * @param type Capability type. See implementations like {@link IItemHandler} as a reference.
     * @param <CapType> Type of capability to fetch off tunnel.
     * @return LazyOptional instance of the capability, or LO.empty otherwise.
     */
    <CapType> LazyOptional<CapType> getCapability(StorageType type, Tunnel instance);

    enum StorageType {
        ITEM, FLUID, ENERGY // TODO: Modded storage types
    }

}
