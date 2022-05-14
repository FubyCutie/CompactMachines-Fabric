package dev.compactmods.machines.datagen;

import dev.compactmods.machines.core.Registration;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;

import java.util.Set;

public class TagGenerator extends BlockTagsProvider {

    public TagGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void addTags() {
        var machines = Set.of(Registration.MACHINE_BLOCK_TINY.get(),
                Registration.MACHINE_BLOCK_SMALL.get(),
                Registration.MACHINE_BLOCK_NORMAL.get(),
                Registration.MACHINE_BLOCK_LARGE.get(),
                Registration.MACHINE_BLOCK_GIANT.get(),
                Registration.MACHINE_BLOCK_MAXIMUM.get());

        var pickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var ironTool = tag(BlockTags.NEEDS_IRON_TOOL);

        var breakableWall = Registration.BLOCK_BREAKABLE_WALL.get();
        pickaxe.add(breakableWall);
        ironTool.add(breakableWall);

        machines.forEach(mach -> {
            pickaxe.add(mach);
            ironTool.add(mach);
        });

    }
}
