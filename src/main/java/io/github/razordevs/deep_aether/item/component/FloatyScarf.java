package io.github.razordevs.deep_aether.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FloatyScarf(int uuid, int color0, int color1, int color2, int color3) {
    public static final Codec<FloatyScarf> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            Codec.INT.fieldOf("id").forGetter(FloatyScarf::uuid),
                            Codec.INT.fieldOf("color_0").forGetter(FloatyScarf::color0),
                            Codec.INT.fieldOf("color_1").forGetter(FloatyScarf::color1),
                            Codec.INT.fieldOf("color_2").forGetter(FloatyScarf::color2),
                            Codec.INT.fieldOf("color_3").forGetter(FloatyScarf::color3)
                    )
                    .apply(codec, FloatyScarf::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FloatyScarf> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, FloatyScarf::uuid,
            ByteBufCodecs.INT, FloatyScarf::color0,
            ByteBufCodecs.INT, FloatyScarf::color1,
            ByteBufCodecs.INT, FloatyScarf::color2,
            ByteBufCodecs.INT, FloatyScarf::color3,
            FloatyScarf::new
    );
}
