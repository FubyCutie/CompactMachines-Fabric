package dev.compactmods.machines.wall;

import io.github.fabricators_of_create.porting_lib.block.HarvestableBlock;
import io.github.fabricators_of_create.porting_lib.util.EntityDestroyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ProtectedWallBlock extends Block implements HarvestableBlock, EntityDestroyBlock {
    protected ProtectedWallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }
}
