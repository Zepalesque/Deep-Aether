package io.github.razordevs.deep_aether.world.structure.brass;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.datagen.loot.DALoot;
import io.github.razordevs.deep_aether.world.structure.DAStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTable;

public class BrassRoom extends BrassDungeonPiece {
    public BrassRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(DAStructurePieceTypes.BRASS_ROOM.get(), manager, name,
                makeSettingsWithPivot(makeSettings(), rotation), pos, processors);
    }

    public BrassRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(DAStructurePieceTypes.BRASS_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation
                -> makeSettings());
    }

    protected static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings();
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        switch (name) {
            case "Brass Chest", "Library Chest" -> createChestLoot(level, pos, random, DALoot.BRASS_DUNGEON_LOOT);
            case "Combinder Chest" -> createChestLoot(level, pos, random, DALoot.BRASS_DUNGEON_COMBINDER_LOOT);
            case "Infested Chest Up" -> {
                BlockPos chest = pos.above();
                BlockEntity entity = level.getBlockEntity(chest);

                if (entity instanceof RandomizableContainerBlockEntity container) {
                    container.setLootTable(DALoot.BRASS_DUNGEON_LOOT, random.nextLong());
                }

                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    protected static void createChestLoot(ServerLevelAccessor level, BlockPos pos, RandomSource random, ResourceKey<LootTable> lootTable) {
        BlockPos chest = pos.below();
        BlockEntity entity = level.getBlockEntity(chest);

        if (entity instanceof RandomizableContainerBlockEntity container) {
            container.setLootTable(lootTable, random.nextLong());
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
    }

    public static StructurePlaceSettings makeSettingsWithPivot(StructurePlaceSettings settings, Rotation rotation) {
        settings.setRotationPivot(new BlockPos(0,0,0).relative(Direction.EAST, 31));
        settings.setRotation(rotation);
        return settings;
    }


    public static class BossRoom extends BrassDungeonPiece {
        public BossRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
            super(DAStructurePieceTypes.BRASS_BOSS_ROOM.get(), manager, name,
                    BrassRoom.makeSettingsWithPivot(makeSettings(), rotation), pos, processors);
        }

        public BossRoom(StructurePieceSerializationContext context, CompoundTag tag) {
            super(DAStructurePieceTypes.BRASS_BOSS_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation
                    -> makeSettings());
        }

        protected static StructurePlaceSettings makeSettings() {
            return new StructurePlaceSettings().setFinalizeEntities(true);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
            switch (name) {
                case "Brass Chest", "Library Chest" -> createChestLoot(level, pos, random, DALoot.BRASS_DUNGEON_LOOT);
                case "Combinder Chest" -> createChestLoot(level, pos, random, DALoot.BRASS_DUNGEON_COMBINDER_LOOT);
                case "Infested Chest Up" -> {
                    BlockPos chest = pos.above();
                    BlockEntity entity = level.getBlockEntity(chest);

                    if (entity instanceof RandomizableContainerBlockEntity container) {
                        container.setLootTable(DALoot.BRASS_DUNGEON_LOOT, random.nextLong());
                    }

                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                }
                case "Treasure Chest" -> {
                    BlockPos chest = pos.below();
                    BlockEntity entity = level.getBlockEntity(chest);

                    if (entity instanceof RandomizableContainerBlockEntity container) {
                        container.setLootTable(DALoot.BRASS_DUNGEON_REWARD, random.nextLong());
                    }
                    TreasureChestBlockEntity.setDungeonType(level, chest, ResourceLocation.fromNamespaceAndPath(DeepAether.MODID, "brass"));

                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
    }
}