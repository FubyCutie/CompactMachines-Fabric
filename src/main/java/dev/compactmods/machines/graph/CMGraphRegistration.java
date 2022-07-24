package dev.compactmods.machines.graph;

import dev.compactmods.machines.CompactMachines;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class CMGraphRegistration {

    public static final ResourceLocation NODES_RL = new ResourceLocation(CompactMachines.MOD_ID, "graph_nodes");
    public static final Registry<IGraphNodeType> NODE_TYPE_REG = FabricRegistryBuilder.createSimple(IGraphNodeType.class,
            NODES_RL).buildAndRegister();
    public static final LazyRegistrar<IGraphNodeType> NODE_TYPES = LazyRegistrar.create(NODE_TYPE_REG, CompactMachines.MOD_ID);

    public static final ResourceLocation EDGES_RL = new ResourceLocation(CompactMachines.MOD_ID, "graph_edges");
    public static final Registry<IGraphEdgeType> EDGE_TYPE_REG = FabricRegistryBuilder.createSimple(IGraphEdgeType.class,
            EDGES_RL).buildAndRegister();
    public static final LazyRegistrar<IGraphEdgeType> EDGE_TYPES = LazyRegistrar.create(EDGE_TYPE_REG, CompactMachines.MOD_ID);

    public static final RegistryObject<IGraphNodeType> MACH_NODE = NODE_TYPES.register("machine", () -> GraphNodeType.MACHINE);
    public static final RegistryObject<IGraphNodeType> DIM_NODE = NODE_TYPES.register("dimension", () -> GraphNodeType.DIMENSION);
    public static final RegistryObject<IGraphNodeType> ROOM_NODE = NODE_TYPES.register("room", () -> GraphNodeType.ROOM);

    public static final RegistryObject<IGraphNodeType> TUNNEL_NODE = NODE_TYPES.register("tunnel", () -> GraphNodeType.TUNNEL);
    public static final RegistryObject<IGraphNodeType> TUNNEL_TYPE_NODE = NODE_TYPES.register("tunnel_type", () -> GraphNodeType.TUNNEL_TYPE);

    public static final RegistryObject<IGraphNodeType> ROOM_UPGRADE_NODE = NODE_TYPES.register("room_upgrade", () -> GraphNodeType.ROOM_UPGRADE);

    public static final RegistryObject<IGraphEdgeType> MACHINE_LINK = EDGE_TYPES.register("machine_link", () -> GraphEdgeType.MACHINE_LINK);

    // Tunnel edges
    public static final RegistryObject<IGraphEdgeType> TUNNEL_TYPE = EDGE_TYPES.register("tunnel_type", () -> GraphEdgeType.TUNNEL_TYPE);
    public static final RegistryObject<IGraphEdgeType> TUNNEL_MACHINE_LINK = EDGE_TYPES.register("tunnel_machine", () -> GraphEdgeType.TUNNEL_MACHINE);

    public static void init() {
        NODE_TYPES.register();
        EDGE_TYPES.register();
    }
}
