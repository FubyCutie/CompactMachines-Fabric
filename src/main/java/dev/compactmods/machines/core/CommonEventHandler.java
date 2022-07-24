package dev.compactmods.machines.core;

import dev.compactmods.machines.wall.ProtectedWallBlock;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CommonEventHandler {

    public static boolean onLeftClickBlock(Level lev, Player player, BlockPos pos, BlockState state, /* Nullable */ BlockEntity blockEntity) {
        if(state.getBlock() instanceof ProtectedWallBlock pwb) {
            if(!pwb.canPlayerBreak(lev, player, pos))
                return false;
        }
        return true;
    }

    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register(CommonEventHandler::onLeftClickBlock);
    }
}
