package dev.compactmods.machines.client;

import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.room.client.MachineRoomScreen;
import dev.compactmods.machines.core.Tunnels;
import dev.compactmods.machines.core.UIRegistration;
import dev.compactmods.machines.tunnel.client.TunnelColors;
import dev.compactmods.machines.tunnel.client.TunnelItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.RenderType;

public class ClientEventHandler implements ClientModInitializer {

    private static void onItemColors() {
        ColorProviderRegistry.ITEM.register(new TunnelItemColor(), Tunnels.ITEM_TUNNEL.get());
    }

    private static void onBlockColors() {
        ColorProviderRegistry.BLOCK.register(new TunnelColors(), Tunnels.BLOCK_TUNNEL_WALL.get());
    }

    @Override
    public void onInitializeClient() {
        RenderType cutout = RenderType.cutoutMipped();
        ItemBlockRenderTypes.setRenderLayer(Tunnels.BLOCK_TUNNEL_WALL.get(), cutout);

        MenuScreens.register(UIRegistration.MACHINE_MENU.get(), MachineRoomScreen::new);

        onItemColors();
        onBlockColors();
    }
}
