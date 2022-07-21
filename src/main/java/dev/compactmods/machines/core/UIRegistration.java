package dev.compactmods.machines.core;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.location.LevelBlockPosition;
import dev.compactmods.machines.room.menu.MachineRoomMenu;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class UIRegistration {
    private static final LazyRegistrar<MenuType<?>> CONTAINERS = LazyRegistrar.create(Registry.MENU, CompactMachines.MOD_ID);

    public static final RegistryObject<MenuType<MachineRoomMenu>> MACHINE_MENU = CONTAINERS.register("machine", () -> new ExtendedScreenHandlerType<>(
            ((windowId, inv, data) -> {
                data.readBlockPos();
                final var mach = data.readWithCodec(LevelBlockPosition.CODEC);
                final var room = data.readChunkPos();

                return new MachineRoomMenu(windowId, room, mach);
            })
    ));

    public static void init() {
        CONTAINERS.register();
    }
}
