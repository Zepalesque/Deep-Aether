package io.github.razordevs.deep_aether.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Supplier;


public record MoaFodder(MobEffectInstance effect) {
    public static final Codec<MoaFodder> CODEC = RecordCodecBuilder.create(
            codec -> codec.group(
                            MobEffectInstance.CODEC.fieldOf("effect").forGetter(MoaFodder::effect)
                    )
                    .apply(codec, MoaFodder::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MoaFodder> STREAM_CODEC = StreamCodec.composite(
            MobEffectInstance.STREAM_CODEC, MoaFodder::effect, MoaFodder::new
    );
}
