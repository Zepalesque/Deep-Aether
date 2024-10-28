package io.github.razordevs.deep_aether.world.structure;

import com.aetherteam.aether.world.processor.BossRoomProcessor;
import com.aetherteam.aether.world.processor.DoubleDropsProcessor;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.world.structure.brass.BrassDungeonPiece;
import io.github.razordevs.deep_aether.world.structure.brass.processor.BrassDungeonRoomProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class DAStructureProcessorLists {
    public static final ResourceKey<StructureProcessorList> BRASS_ROOM = createKey("bronze_room");

    public static final ResourceKey<StructureProcessorList> BRASS_BOSS_ROOM = createKey("bronze_boss_room");

    public static final ResourceKey<StructureProcessorList> INFESTED_BRASS_ROOM = createKey("infested_bronze_room");

    public static final ResourceKey<StructureProcessorList> INFESTED_BRASS_BOSS_ROOM = createKey("infested_bronze_boss_room");

    public static final ResourceKey<StructureProcessorList> GARDEN_BRASS_ROOM = createKey("garden_bronze_room");

    public static final ResourceKey<StructureProcessorList> GARDEN_BRASS_BOSS_ROOM = createKey("garden_bronze_boss_room");

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, name));
    }

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        register(context, BRASS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_NORMAL));
        register(context, BRASS_BOSS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_NORMAL, BrassDungeonRoomProcessor.INSTANCE));

        register(context, INFESTED_BRASS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_COCKATRICE));
        register(context, INFESTED_BRASS_BOSS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_COCKATRICE, BrassDungeonRoomProcessor.INSTANCE));

        register(context, GARDEN_BRASS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_PLANT));
        register(context, GARDEN_BRASS_BOSS_ROOM, createBrassDungeonList(BrassDungeonPiece.TRAPPED_SKYROOT_PLANKS_PLANT, BrassDungeonRoomProcessor.INSTANCE));
    }

    private static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> key, List<StructureProcessor> processors) {
        context.register(key, new StructureProcessorList(processors));
    }

    private static List<StructureProcessor> createBrassDungeonList(StructureProcessor... ruleProcessor) {
        List<StructureProcessor> list = new ArrayList<>() {{
            add(BrassDungeonPiece.LOCKED_NIMBUS_STONE);
            add(BrassDungeonPiece.TRAPPED_NIMBUS_STONE);
            add(DoubleDropsProcessor.INSTANCE);
        }};
        list.addAll(List.of(ruleProcessor));

        return list;
    }
}
