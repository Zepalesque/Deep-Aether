package teamrazor.deepaether.world.noise;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import teamrazor.deepaether.DeepAetherMod;

public class DANoises {



    public static final ResourceKey<NormalNoise.NoiseParameters> GOLDEN_HEIGHTS = createKey("golden_heights");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(DeepAetherMod.MODID, name));
    }
}
