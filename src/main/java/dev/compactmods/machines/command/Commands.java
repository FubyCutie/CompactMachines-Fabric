package dev.compactmods.machines.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.command.argument.RoomPositionArgument;
import dev.compactmods.machines.command.data.CMDataSubcommand;
import dev.compactmods.machines.command.subcommand.*;
import dev.compactmods.machines.core.Registries;
import dev.compactmods.machines.upgrade.command.CMUpgradeRoomCommand;
import dev.compactmods.machines.upgrade.command.RoomUpgradeArgument;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

public class Commands {

    // TODO: /cm create <size:RoomSize> <owner:Player> <giveMachine:true|false>
    // TODO: /cm spawn set <room> <pos>

    static {
        Registries.COMMAND_ARGUMENT_TYPES.register("room_pos",
                () -> registerByClass(RoomPositionArgument.class, SingletonArgumentInfo.contextFree(RoomPositionArgument::room)));

        Registries.COMMAND_ARGUMENT_TYPES.register("room_upgrade",
                () -> registerByClass(RoomUpgradeArgument.class, SingletonArgumentInfo.contextFree(RoomUpgradeArgument::upgrade)));
    }

    public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> I registerByClass(Class<A> infoClass, I argumentTypeInfo) {
        ArgumentTypeInfos.BY_CLASS.put(infoClass, argumentTypeInfo);
        return argumentTypeInfo;
    }


    public static void init() {
        CommandRegistrationCallback.EVENT.register(CompactMachinesCommands::onCommandsRegister);
    }

    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        final LiteralArgumentBuilder<CommandSourceStack> root = LiteralArgumentBuilder.literal(CompactMachines.MOD_ID);
        root.then(CMEjectSubcommand.make());
        root.then(CMSummarySubcommand.make());
        root.then(CMRebindSubcommand.make());
        root.then(CMUnbindSubcommand.make());
        root.then(CMReaddDimensionSubcommand.make());
        root.then(CMRoomsSubcommand.make());
        root.then(CMDataSubcommand.make());
        root.then(CMGiveMachineSubcommand.make());
        root.then(SpawnSubcommand.make());
        root.then(CMUpgradeRoomCommand.make());

        dispatcher.register(root);
    }

    public static void prepare() {

    }
}
