package dev.compactmods.machines.datagen;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.datagen.lang.EnglishLangGenerator;
import dev.compactmods.machines.datagen.lang.RussianLangGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class DataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var existingData = System.getProperty("com.simibubi.create.existingData").split(";");
        ExistingFileHelper helper = new ExistingFileHelper(Arrays.stream(existingData).map(Paths::get).toList(), Collections.emptySet(),
                true, null, null);
//        if (event.includeServer())
            registerServerProviders(generator);

//        if (event.includeClient())
            registerClientProviders(generator, helper);
    }

    private static void registerServerProviders(FabricDataGenerator generator) {
        generator.addProvider(new LevelBiomeGenerator(generator));
//        generator.addProvider(new BlockLootGenerator(generator));
        generator.addProvider(new RecipeGenerator(generator));
        generator.addProvider(new AdvancementGenerator(generator));
        generator.addProvider(new TagGenerator(generator));

        generator.addProvider(new EnglishLangGenerator(generator));
        generator.addProvider(new RussianLangGenerator(generator));
    }

    private static void registerClientProviders(DataGenerator generator, ExistingFileHelper helper) {
        generator.addProvider(new StateGenerator(generator, helper));
        generator.addProvider(new TunnelWallStateGenerator(generator, helper));
        generator.addProvider(new ItemModelGenerator(generator, helper));

    }
}
