package dev.compactmods.machines.core;

import com.mojang.brigadier.CommandDispatcher;
import dev.compactmods.machines.command.CMCommandRoot;
import dev.compactmods.machines.command.data.CMDataCommand;
import net.minecraft.commands.CommandSourceStack;

public class ServerEventHandler {

    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        CMCommandRoot.register(dispatcher);
        CMDataCommand.register(dispatcher);
    }
}
