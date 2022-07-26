package dev.compactmods.machines.room.data;

import dev.compactmods.machines.CompactMachines;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class CompactMachinesLootFunctions {

    private static final LazyRegistrar<LootItemFunctionType> LOOT_FUNCS = LazyRegistrar.create(Registry.LOOT_FUNCTION_TYPE, CompactMachines.MOD_ID);

    public static RegistryObject<LootItemFunctionType> COPY_ROOM_BINDING = LOOT_FUNCS.register("copy_room_binding",
            () -> new LootItemFunctionType(new CopyRoomBindingFunction.Serializer()));

    public static void init() {
        LOOT_FUNCS.register();
    }
}
