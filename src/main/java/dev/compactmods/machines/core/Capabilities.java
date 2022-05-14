package dev.compactmods.machines.core;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.api.room.IRoomInformation;
import dev.compactmods.machines.machine.CompactMachineBlockEntity;
import dev.compactmods.machines.room.capability.PlayerRoomHistoryCapProvider;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.resources.ResourceLocation;

public class Capabilities implements EntityComponentInitializer, BlockComponentInitializer {

    public static final ComponentKey<IRoomInformation> ROOM = ComponentRegistry.getOrCreate(new ResourceLocation(CompactMachines.MOD_ID, "room"), IRoomInformation.class);

    public static final ComponentKey<PlayerRoomHistoryCapProvider> ROOM_HISTORY = ComponentRegistry.getOrCreate(new ResourceLocation(CompactMachines.MOD_ID, "room_history"), PlayerRoomHistoryCapProvider.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(ROOM_HISTORY, PlayerRoomHistoryCapProvider::new);
    }

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
//        registry.registerFor(CompactMachineBlockEntity.class, ROOM, be -> {
//            LazyOptional<IRoomInformation> optional = be.getLazyRoom();
//            if (optional != null)
//                return optional.getValueUnsafer();
//            return null;
//        });
    }
}
