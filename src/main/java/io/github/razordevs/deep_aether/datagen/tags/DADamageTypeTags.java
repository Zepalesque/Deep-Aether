package io.github.razordevs.deep_aether.datagen.tags;

import io.github.razordevs.deep_aether.DeepAether;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class DADamageTypeTags extends DamageTypeTagsProvider {

    public DADamageTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, DeepAether.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Deep Aether Damage Type Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DATags.DamageTypes.EOTS_IMMUNE).add(
                DamageTypes.LIGHTNING_BOLT
        );
    }
}
