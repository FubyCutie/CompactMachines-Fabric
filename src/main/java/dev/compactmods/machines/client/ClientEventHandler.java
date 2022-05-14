package dev.compactmods.machines.client;

import dev.compactmods.machines.core.Tunnels;
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
        BlockRenderLayerMap.INSTANCE.putBlock(Tunnels.BLOCK_TUNNEL_WALL.get(), cutout);
        onItemColors();
        onBlockColors();
    }
}
