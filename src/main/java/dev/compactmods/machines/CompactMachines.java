package dev.compactmods.machines;

import dev.compactmods.machines.command.CompactMachinesCommands;
import dev.compactmods.machines.command.argument.RoomPositionArgument;
import dev.compactmods.machines.config.CommonConfig;
import dev.compactmods.machines.config.EnableVanillaRecipesConfigCondition;
import dev.compactmods.machines.config.ServerConfig;
import dev.compactmods.machines.core.*;
import dev.compactmods.machines.graph.CMGraphRegistration;
import dev.compactmods.machines.room.RoomEventHandler;
import dev.compactmods.machines.room.data.CompactMachinesLootFunctions;
import dev.compactmods.machines.room.network.RoomNetworkHandler;
import dev.compactmods.machines.upgrade.MachineRoomUpgrades;
import dev.compactmods.machines.upgrade.command.RoomUpgradeArgument;
import dev.compactmods.machines.util.EnergyTransferable;
import io.github.fabricators_of_create.porting_lib.util.LazyItemGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import team.reborn.energy.api.EnergyStorage;

import javax.annotation.Nonnull;

public class CompactMachines implements ModInitializer {
    public static final String MOD_ID = "compactmachines";

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Marker CONN_MARKER = MarkerManager.getMarker("cm_connections");

    public static final CreativeModeTab COMPACT_MACHINES_ITEMS = new LazyItemGroup(MOD_ID) {
        @Override
        public @Nonnull
        ItemStack makeIcon() {
            return new ItemStack(Registration.MACHINE_BLOCK_ITEM_NORMAL.get());
        }
    };

    // public static CMRoomChunkloadingManager CHUNKLOAD_MANAGER;

    @Override
    public void onInitialize() {
        // Register blocks and items
        UIRegistration.init();
        Tunnels.init();
        CMGraphRegistration.init();
        MachineRoomUpgrades.init();
        CompactMachinesCommands.init();
        CompactMachinesLootFunctions.init();

        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, CommonConfig.CONFIG);
        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.SERVER, ServerConfig.CONFIG);
        ModConfigEvent.LOADING.register(CommonConfig::onLoaded);
        ServerWorldEvents.LOAD.register(ServerEventHandler::onWorldLoaded);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(ServerEventHandler::onPlayerDimChange);
        ServerPlayConnectionEvents.JOIN.register(ServerEventHandler::onPlayerLogin);
        CommonEventHandler.init();
        RoomEventHandler.init();
        ModBusEvents.setup();

        EnableVanillaRecipesConfigCondition.register();

        CompactMachinesNet.CHANNEL.initServerListener();
        RoomNetworkHandler.CHANNEL.initServerListener();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            CompactMachinesNet.CHANNEL.initClientListener();
            RoomNetworkHandler.CHANNEL.initClientListener();
        }

        Registration.init();

        EnergyStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof EnergyTransferable energyTransferable)
                return energyTransferable.getEnergyStorage(context);
            return null;
        });
    }
}
