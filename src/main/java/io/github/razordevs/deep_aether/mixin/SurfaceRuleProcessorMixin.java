package io.github.razordevs.deep_aether.mixin;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.ChunkAccessAccessor;
import com.aetherteam.aether.world.BlockLogicUtil;
import com.aetherteam.aether.world.processor.SurfaceRuleProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

/**
 * TODO: REMOVE ONCE RESOLVED ON AETHER'S SIDE
 */
@Mixin(SurfaceRuleProcessor.class)
public class SurfaceRuleProcessorMixin {

    @Overwrite
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {
            if (worldGenLevel instanceof WorldGenRegion region) {
                if (BlockLogicUtil.isOutOfBounds(modifiedBlockInfo.pos(), region.getCenter())) {
                    return modifiedBlockInfo;
                }
            }

            ChunkSource var10 = worldGenLevel.getChunkSource();
            if (var10 instanceof ServerChunkCache serverChunkCache) {
                ChunkGenerator var11 = serverChunkCache.getGenerator();
                if (var11 instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
                    NoiseGeneratorSettings settingsHolder = noiseBasedChunkGenerator.generatorSettings().value();
                    SurfaceRules.RuleSource surfaceRule = settingsHolder.surfaceRule();
                    ChunkAccess chunkAccess = worldGenLevel.getChunk(modifiedBlockInfo.pos());
                    NoiseChunk noisechunk = ((ChunkAccessAccessor)chunkAccess).aether$getNoiseChunk();
                    if (noisechunk != null) {
                        CarvingContext carvingcontext = new CarvingContext(noiseBasedChunkGenerator, worldGenLevel.registryAccess(), chunkAccess.getHeightAccessorForGeneration(), noisechunk, serverChunkCache.randomState(), surfaceRule);
                        BiomeManager var10001 = worldGenLevel.getBiomeManager();
                        Optional<BlockState> state = carvingcontext.topMaterial(var10001::getNoiseBiomeAtPosition, chunkAccess, modifiedBlockInfo.pos(), false);
                        if (state.isPresent() && modifiedBlockInfo.state().is(AetherTags.Blocks.AETHER_DIRT) && !modifiedBlockInfo.state().is( AetherBlocks.AETHER_DIRT.get()) && ((BlockState)state.get()).is(AetherTags.Blocks.AETHER_DIRT)) {
                            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), state.get(), null);
                        }
                    }
                }
            }
        }

        return modifiedBlockInfo;
    }
}
