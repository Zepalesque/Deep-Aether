package io.github.razordevs.deep_aether.entity;

import net.minecraft.sounds.SoundSource;

import javax.annotation.Nullable;

public interface AerwhaleSaddleable {
    boolean isSaddleable();

    void equipSaddle(@Nullable SoundSource source);
    boolean isSaddled();
}
