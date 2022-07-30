package dev.compactmods.machines.room;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.room.capability.PlayerRoomHistoryCapProvider;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.resources.ResourceLocation;

public class RoomCapabilities implements EntityComponentInitializer {

    public static final ComponentKey<PlayerRoomHistoryCapProvider> ROOM_HISTORY = ComponentRegistry.getOrCreate(new ResourceLocation(CompactMachines.MOD_ID, "room_history"), PlayerRoomHistoryCapProvider.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ROOM_HISTORY, PlayerRoomHistoryCapProvider::new);
    }
}
