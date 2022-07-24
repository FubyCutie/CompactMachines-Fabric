package dev.compactmods.machines.util;

import net.minecraft.core.Direction;
import team.reborn.energy.api.EnergyStorage;

import javax.annotation.Nullable;

public interface EnergyTransferable {
    @Nullable
    EnergyStorage getEnergyStorage(@Nullable Direction face);
}
