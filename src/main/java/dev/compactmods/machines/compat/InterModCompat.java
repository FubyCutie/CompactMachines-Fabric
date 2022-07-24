//package dev.compactmods.machines.compat;
//
//import dev.compactmods.machines.CompactMachines;
//import dev.compactmods.machines.compat.carryon.CarryOnCompat;
//import dev.compactmods.machines.compat.theoneprobe.TheOneProbeCompat;
//import net.fabricmc.loader.api.FabricLoader;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.ModList;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
//
//@Mod.EventBusSubscriber(modid = CompactMachines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class InterModCompat {
//
//    @SubscribeEvent
//    public static void enqueueCompatMessages(final InterModEnqueueEvent evt) {
//        if(FabricLoader.getInstance().isModLoaded("theoneprobe"))
//            TheOneProbeCompat.sendIMC();
//
//        if(FabricLoader.getInstance().isModLoaded("carryon"))
//            CarryOnCompat.sendIMC();
//    }
//}
