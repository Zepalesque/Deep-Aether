package io.github.razordevs.deep_aether.datagen.tags;


import com.aetherteam.aether.AetherTags;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.init.DASounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DASoundTagData extends TagsProvider<SoundEvent> {
    public DASoundTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.SOUND_EVENT, registries, DeepAether.MODID, existingFileHelper);
    }
    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherTags.SoundEvents.BOSS_MUSIC).add(
                DASounds.MUSIC_BOSS_EOTS.getKey()
        );
    }
}