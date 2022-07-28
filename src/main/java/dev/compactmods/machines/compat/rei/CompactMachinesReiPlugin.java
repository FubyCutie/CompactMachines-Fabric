package dev.compactmods.machines.compat.rei;

import dev.compactmods.machines.api.core.JeiInfo;
import dev.compactmods.machines.core.Registration;
import dev.compactmods.machines.core.Tunnels;
import dev.compactmods.machines.i18n.TranslationUtil;
import dev.compactmods.machines.machine.CompactMachineItem;
import dev.compactmods.machines.room.RoomSize;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class CompactMachinesReiPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        Arrays.stream(RoomSize.values())
                .map(CompactMachineItem::getItemBySize)
                .forEach(i -> registry.add(DefaultInformationDisplay.createFromEntry(
                        EntryStacks.of(i),
                        Component.empty())
                                .lines(TranslationUtil.jeiInfo(JeiInfo.MACHINE))));


        registry.add(DefaultInformationDisplay.createFromEntry(
                EntryStacks.of(Registration.PERSONAL_SHRINKING_DEVICE.get()),
                Component.empty())
                        .lines(TranslationUtil.jeiInfo(JeiInfo.SHRINKING_DEVICE)));

    }

    @Override
    public void registerItemComparators(ItemComparatorRegistry registry) {
       registry.registerNbt(Tunnels.ITEM_TUNNEL.get());
    }
}
