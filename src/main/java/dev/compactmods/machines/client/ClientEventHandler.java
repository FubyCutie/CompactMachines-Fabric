package dev.compactmods.machines.client;

import dev.compactmods.machines.client.shader.CM4Shaders;
import dev.compactmods.machines.tunnel.Tunnels;
import dev.compactmods.machines.core.UIRegistration;
import dev.compactmods.machines.room.client.MachineRoomScreen;
import dev.compactmods.machines.tunnel.client.TunnelColors;
import dev.compactmods.machines.tunnel.client.TunnelItemColor;
import io.github.fabricators_of_create.porting_lib.event.client.RegisterShadersCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class ClientEventHandler implements ClientModInitializer {

    @SubscribeEvent
    public static void onItemColors() {
        ColorProviderRegistry.ITEM.register(new TunnelItemColor(), Tunnels.ITEM_TUNNEL.get());
    }

    @SubscribeEvent
    public static void onBlockColors() {
        ColorProviderRegistry.BLOCK.register(new TunnelColors(), Tunnels.BLOCK_TUNNEL_WALL.get());
    }

    @Override
    public void onInitializeClient() {
        RenderType cutout = RenderType.cutoutMipped();
        BlockRenderLayerMap.INSTANCE.putBlock(Tunnels.BLOCK_TUNNEL_WALL.get(), cutout);

        MenuScreens.register(UIRegistration.MACHINE_MENU.get(), MachineRoomScreen::new);

        onItemColors();
        onBlockColors();
        RegisterShadersCallback.EVENT.register(CM4Shaders::registerShaders);
    }
}
