package dev.compactmods.machines.api.room;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import javax.annotation.Nonnull;

public interface IRoomInformation extends Component {

    @Nonnull
    ChunkPos chunk();

    @Nonnull
    ServerLevel level();
}
