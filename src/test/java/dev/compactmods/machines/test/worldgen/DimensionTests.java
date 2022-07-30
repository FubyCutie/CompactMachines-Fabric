package dev.compactmods.machines.test.worldgen;

import dev.compactmods.machines.dimension.Dimension;
import dev.compactmods.machines.test.TestBatches;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class DimensionTests {

    @GameTest(template = "empty_5x5", batch = TestBatches.DIMENSION)
    public static void dimensionRegistered(final GameTestHelper test) {
        var level = test.getLevel();
        var server = level.getServer();

        var compact = server.getLevel(Dimension.COMPACT_DIMENSION);

        if (compact == null)
            test.fail("Compact dimension not registered.");

        test.succeed();
    }
}
