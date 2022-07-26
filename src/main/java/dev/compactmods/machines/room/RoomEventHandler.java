package dev.compactmods.machines.room;

import dev.compactmods.machines.api.core.Messages;
import dev.compactmods.machines.core.Registration;
import dev.compactmods.machines.i18n.TranslationUtil;
import dev.compactmods.machines.room.data.CompactRoomData;
import dev.compactmods.machines.room.exceptions.NonexistentRoomException;
import io.github.fabricators_of_create.porting_lib.event.common.LivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import io.github.fabricators_of_create.porting_lib.event.common.EntityEvent;
import io.github.fabricators_of_create.porting_lib.event.common.EntityEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RoomEventHandler {

    public static boolean entityJoined(Entity ent, Level world, boolean loadedFromDisk) {
        // Early exit if spawning in non-CM dimensions
        if ((ent instanceof Player) || !ent.level.dimension().equals(Registration.COMPACT_DIMENSION)) return true;

        // no-op clients, we only care about blocking server spawns
        if(ent.level.isClientSide) return true;

        if (!positionInsideRoom(ent, ent.position())) {
            return false;
        }
        return true;
    }

    public static boolean onCheckSpawn(Mob ent, LevelAccessor world, double x, double y, double z, @Nullable BaseSpawner spawner, MobSpawnType spawnReason) {
        Vec3 target = new Vec3(x, y, z);

        // Early exit if spawning in non-CM dimensions
        if (!ent.level.dimension().equals(Registration.COMPACT_DIMENSION)) return true;

        if (!positionInsideRoom(ent, target)) return false;
        return true;
    }

    public static void onEntityTeleport(final EntityEvents.Teleport.EntityTeleportEvent evt) {
        // Allow teleport commands, we don't want to trap people anywhere
//        if (evt instanceof EntityTeleportEvent.TeleportCommand) return;
        if(!evt.getEntity().level.dimension().equals(Registration.COMPACT_DIMENSION)) return;

        Entity ent = evt.getEntity();
        doEntityTeleportHandle(evt, evt.getTarget(), ent);
    }


    /**
     * Helper to determine if an event should be canceled, by determining if a target is outside
     * a machine's bounds.
     *
     * @param entity Entity trying to teleport.
     * @param target Teleportation target location.
     * @return True if position is inside a room; false otherwise.
     */
    private static boolean positionInsideRoom(Entity entity, Vec3 target) {
        final var level = entity.level;
        if (!level.dimension().equals(Registration.COMPACT_DIMENSION)) return false;

        if (level instanceof ServerLevel compactDim) {
            ChunkPos machineChunk = new ChunkPos(entity.chunkPosition().x, entity.chunkPosition().z);

            try {
                final CompactRoomData intern = CompactRoomData.get(compactDim);
                return intern.getBounds(machineChunk).contains(target);
            } catch (NonexistentRoomException e) {
                return false;
            }
        }

        return false;
    }

    private static void doEntityTeleportHandle(EntityEvent evt, Vec3 target, Entity ent) {
        if (!positionInsideRoom(ent, target)) {
            if (ent instanceof ServerPlayer) {
                ((ServerPlayer) ent).displayClientMessage(TranslationUtil.message(Messages.TELEPORT_OUT_OF_BOUNDS, ent.getName()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC), true);
            }
            evt.setCanceled(true);
        }
    }

    public static void init() {
        EntityEvents.TELEPORT.register(RoomEventHandler::onEntityTeleport);
        EntityEvents.ON_JOIN_WORLD.register(RoomEventHandler::entityJoined);
        LivingEntityEvents.CHECK_SPAWN.register(RoomEventHandler::onCheckSpawn);
    }
}
