package dev.compactmods.machines;

import dev.compactmods.machines.command.argument.RoomPositionArgument;
import dev.compactmods.machines.config.CommonConfig;
import dev.compactmods.machines.config.EnableVanillaRecipesConfigCondition;
import dev.compactmods.machines.config.ServerConfig;
import dev.compactmods.machines.core.ModBusEvents;
import dev.compactmods.machines.core.Registration;
import dev.compactmods.machines.core.ServerEventHandler;
import dev.compactmods.machines.core.Tunnels;
import dev.compactmods.machines.core.UIRegistration;
import dev.compactmods.machines.graph.CMGraphRegistration;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import dev.compactmods.machines.room.TeleportationEventHandler;
import io.github.fabricators_of_create.porting_lib.util.LazyItemGroup;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

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
        Registration.init(eb);
        UIRegistration.init(eb);
        Tunnels.init(eb);
        CMGraphRegistration.init(eb);

        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, CommonConfig.CONFIG);
        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.SERVER, ServerConfig.CONFIG);
        ModConfigEvent.LOADING.register(CommonConfig::onLoaded);
        CommandRegistrationCallback.EVENT.register(ServerEventHandler::onCommandsRegister);
        ModBusEvents.setup();
        TeleportationEventHandler.init();

        EnableVanillaRecipesConfigCondition.register();

        ArgumentTypes.register("room_pos", RoomPositionArgument.class, new EmptyArgumentSerializer<>(RoomPositionArgument::room));
    }
}
