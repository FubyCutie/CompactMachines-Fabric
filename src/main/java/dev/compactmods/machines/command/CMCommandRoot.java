package dev.compactmods.machines.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.command.data.CMDataSubcommand;
import dev.compactmods.machines.command.subcommand.*;
import dev.compactmods.machines.upgrade.command.CMUpgradeRoomCommand;
import net.minecraft.commands.CommandSourceStack;

public class CMCommandRoot {

    // TODO: /cm create <size:RoomSize> <owner:Player> <giveMachine:true|false>
    // TODO: /cm spawn set <room> <pos>

    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
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
}
