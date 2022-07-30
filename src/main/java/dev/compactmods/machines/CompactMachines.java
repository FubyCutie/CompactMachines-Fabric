package dev.compactmods.machines;

import dev.compactmods.machines.api.core.Constants;
import dev.compactmods.machines.command.Commands;
import dev.compactmods.machines.config.CommonConfig;
import dev.compactmods.machines.config.EnableVanillaRecipesConfigCondition;
import dev.compactmods.machines.config.ServerConfig;
import dev.compactmods.machines.core.Registries;
import dev.compactmods.machines.core.UIRegistration;
import dev.compactmods.machines.dimension.Dimension;
import dev.compactmods.machines.graph.Graph;
import dev.compactmods.machines.machine.Machines;
import dev.compactmods.machines.room.data.LootFunctions;
import dev.compactmods.machines.shrinking.Shrinking;
import dev.compactmods.machines.tunnel.Tunnels;
import dev.compactmods.machines.upgrade.MachineRoomUpgrades;
import dev.compactmods.machines.wall.Walls;
import io.github.fabricators_of_create.porting_lib.util.LazyItemGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
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
    /**
     * @deprecated Switch usages to use api Constants in 1.20, eliminate it here
     */
    @Deprecated(forRemoval = true)
    public static final String MOD_ID = Constants.MOD_ID;

    public static final Logger LOGGER = LogManager.getLogger();
    public static final Marker CONN_MARKER = MarkerManager.getMarker("cm_connections");

    public static final CreativeModeTab COMPACT_MACHINES_ITEMS = new LazyItemGroup(MOD_ID) {
        @Override
        public @Nonnull
        ItemStack makeIcon() {
            return new ItemStack(Machines.MACHINE_BLOCK_ITEM_NORMAL.get());
        }
    };

    @Override
    public void onInitialize() {
        Registries.setup();
        preparePackages();
        doRegistration();

        // Configuration
        ModLoadingContext mlCtx = ModLoadingContext.get();
        mlCtx.registerConfig(ModConfig.Type.COMMON, CommonConfig.CONFIG);
        mlCtx.registerConfig(ModConfig.Type.SERVER, ServerConfig.CONFIG);

        EnableVanillaRecipesConfigCondition.register();

//        UIRegistration.init(); TODO: PORT
//        Tunnels.init();
//        CMGraphRegistration.init();
//        MachineRoomUpgrades.init();
//        CompactMachinesCommands.init();
//        CompactMachinesLootFunctions.init();
//
//        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, CommonConfig.CONFIG);
//        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.SERVER, ServerConfig.CONFIG);
//        ModConfigEvent.LOADING.register(CommonConfig::onLoaded);
//        ServerWorldEvents.LOAD.register(ServerEventHandler::onWorldLoaded);
//        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(ServerEventHandler::onPlayerDimChange);
//        ServerPlayConnectionEvents.JOIN.register(ServerEventHandler::onPlayerLogin);
//        CommonEventHandler.init();
//        RoomEventHandler.init();
//        ModBusEvents.setup();

        CompactMachinesNet.CHANNEL.initServerListener();
        RoomNetworkHandler.CHANNEL.initServerListener();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            CompactMachinesNet.CHANNEL.initClientListener();
            RoomNetworkHandler.CHANNEL.initClientListener();
        }

        Registration.init();

        ItemStorage.SIDED.registerForBlockEntities((blockEntity, context) -> {
            if (blockEntity instanceof CompactMachineBlockEntity machine)
                return machine.getTunnelContext(CapabilityTunnel.ITEM, context);
            if (blockEntity instanceof TunnelWallEntity tunnel)
                return tunnel.getCapability(CapabilityTunnel.ITEM, context);
            return null;
        }, Registration.MACHINE_TILE_ENTITY.get(), Tunnels.TUNNEL_BLOCK_ENTITY.get());
        FluidStorage.SIDED.registerForBlockEntities((blockEntity, context) -> {
            if (blockEntity instanceof CompactMachineBlockEntity machine)
                return machine.getTunnelContext(CapabilityTunnel.FLUID, context);
            if (blockEntity instanceof TunnelWallEntity tunnel)
                return tunnel.getCapability(CapabilityTunnel.FLUID, context);
            return null;
        }, Registration.MACHINE_TILE_ENTITY.get(), Tunnels.TUNNEL_BLOCK_ENTITY.get());
        EnergyStorage.SIDED.registerForBlockEntities((blockEntity, context) -> {
            if (blockEntity instanceof CompactMachineBlockEntity machine)
                return machine.getTunnelContext(CapabilityTunnel.ENERGY, context);
            if (blockEntity instanceof TunnelWallEntity tunnel)
                return tunnel.getCapability(CapabilityTunnel.ENERGY, context);
            return null;
        }, Registration.MACHINE_TILE_ENTITY.get(), Tunnels.TUNNEL_BLOCK_ENTITY.get());
    }

    /**
     * Sets up the deferred registration for usage in package/module setup.
     */
    private static void doRegistration() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        Registries.BLOCKS.register(bus);
        Registries.ITEMS.register(bus);
        Registries.BLOCK_ENTITIES.register(bus);
        Registries.TUNNEL_DEFINITIONS.register(bus);
        Registries.CONTAINERS.register(bus);
        Registries.UPGRADES.register(bus);
        Registries.NODE_TYPES.register(bus);
        Registries.EDGE_TYPES.register(bus);
        Registries.COMMAND_ARGUMENT_TYPES.register(bus);
        Registries.LOOT_FUNCS.register(bus);
    }

    private static void preparePackages() {
        // Package initialization here, this kickstarts the rest of the DR code (classloading)
        Machines.prepare();
        Walls.prepare();
        Tunnels.prepare();
        Shrinking.prepare();

        UIRegistration.prepare();
        Dimension.prepare();
        MachineRoomUpgrades.prepare();
        Graph.prepare();
        Commands.prepare();
        LootFunctions.prepare();
    }
}
