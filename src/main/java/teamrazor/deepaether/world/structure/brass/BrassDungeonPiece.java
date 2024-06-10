package teamrazor.deepaether.world.structure.brass;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structurepiece.AetherTemplateStructurePiece;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import teamrazor.deepaether.DeepAether;
import teamrazor.deepaether.init.DABlocks;

import java.util.function.Function;

public class BrassDungeonPiece extends AetherTemplateStructurePiece {
    protected static final RuleProcessor LOCKED_NIMBUS_STONE = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(DABlocks.LOCKED_NIMBUS_STONE.get(), 0.1F), AlwaysTrueTest.INSTANCE, DABlocks.LOCKED_LIGHT_NIMBUS_STONE.get().defaultBlockState())
    ));

    public BrassDungeonPiece(StructurePieceType type, StructureTemplateManager manager, String name, StructurePlaceSettings settings, BlockPos pos) {
        super(type, manager, makeLocation(name), settings, pos);
    }

    public BrassDungeonPiece(StructurePieceType type, CompoundTag tag, StructureTemplateManager manager, Function<ResourceLocation, StructurePlaceSettings> settingsFactory) {
        super(type, tag, manager, settingsFactory);
    }

    protected static ResourceLocation makeLocation(String name) {
        return new ResourceLocation(DeepAether.MODID, "brass_dungeon/" + name);
    }
}