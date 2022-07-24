package dev.compactmods.machines.upgrade;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.api.room.upgrade.RoomUpgrade;
import dev.compactmods.machines.core.Registration;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MachineRoomUpgrades {

    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CompactMachines.MOD_ID, "room_upgrades");
    public static final ResourceKey<Registry<RoomUpgrade>> REGISTRY_KEY = ResourceKey.createRegistryKey(REGISTRY_NAME);

    public static final Registry<RoomUpgrade> REGISTRY = FabricRegistryBuilder.createSimple(RoomUpgrade.class, REGISTRY_NAME).buildAndRegister();

    private static final LazyRegistrar<RoomUpgrade> UPGRADES = LazyRegistrar.create(REGISTRY, CompactMachines.MOD_ID);

    // ================================================================================================================
    public static final RegistryObject<RoomUpgrade> CHUNKLOAD = UPGRADES.register(ChunkloadUpgrade.REG_ID.getPath(), ChunkloadUpgrade::new);

    public static final RegistryObject<Item> CHUNKLOADER = Registration.ITEMS.register("chunkloader_upgrade", () -> new ChunkloadUpgradeItem(new Item.Properties()
//            .tab(CompactMachines.COMPACT_MACHINES_ITEMS)
            .stacksTo(1)));

    // ================================================================================================================

    public static void init() {
        UPGRADES.register();
    }
}
