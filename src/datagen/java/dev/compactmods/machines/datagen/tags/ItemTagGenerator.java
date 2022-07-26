package dev.compactmods.machines.datagen.tags;

import dev.compactmods.machines.api.core.CMTags;
import dev.compactmods.machines.upgrade.MachineRoomUpgrades;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataGenerator gen, FabricTagProvider.BlockTagProvider blockTags) {
        super(gen, blockTags);
    }

    @Override
    protected void generateTags() {
        var upgradeTag = tag(CMTags.ROOM_UPGRADE_ITEM);

        upgradeTag.add(MachineRoomUpgrades.CHUNKLOADER.get());
    }
}
