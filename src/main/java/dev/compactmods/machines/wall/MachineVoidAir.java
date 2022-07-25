package dev.compactmods.machines.wall;

import dev.compactmods.machines.config.ServerConfig;
import dev.compactmods.machines.util.PlayerUtil;
import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.DamageSourceAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import static dev.compactmods.machines.CompactMachines.MOD_ID;


public class MachineVoidAir extends AirBlock {
    final public static DamageSource DAMAGE_SOURCE = DamageSourceAccessor.port_lib$init(MOD_ID + "_voidair");

    public MachineVoidAir() {
        super(BlockBehaviour.Properties.of(Material.AIR).noCollission().noDrops().air());
    }


    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (ServerConfig.isAllowedOutsideOfMachine()) return;
        if (pLevel.isClientSide) return;

        if (pEntity instanceof ServerPlayer player) {
            if (player.isCreative()) return;

            if (player.getActiveEffectsMap().containsKey(MobEffects.BLINDNESS))
                PlayerUtil.teleportPlayerOutOfMachine((ServerLevel) pLevel, player);


            player.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 20));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 5 * 20));
            player.hurt(DAMAGE_SOURCE, 1);
        }
    }
}
