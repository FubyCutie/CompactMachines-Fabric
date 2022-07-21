package dev.compactmods.machines.room.data;

import dev.compactmods.machines.CompactMachines;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class CMLootFunctions {

    public static LootItemFunctionType COPY_ROOM_BINDING;

    public static void onLootSerializing() {
        COPY_ROOM_BINDING = Registry.register(Registry.LOOT_FUNCTION_TYPE,
                new ResourceLocation(CompactMachines.MOD_ID, "copy_room_binding"),
                new LootItemFunctionType(new CopyRoomBindingFunction.Serializer()));
    }
}
