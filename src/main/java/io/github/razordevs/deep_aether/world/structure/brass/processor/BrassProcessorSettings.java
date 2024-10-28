package io.github.razordevs.deep_aether.world.structure.brass.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public record BrassProcessorSettings(Holder<StructureProcessorList> bossSettings, Holder<StructureProcessorList> roomSettings,
                                     Holder<StructureProcessorList> infestedBossSettings, Holder<StructureProcessorList> infestedRoomSettings,
                                     Holder<StructureProcessorList> gardenBossSettings, Holder<StructureProcessorList> gardenRoomSettings) {
    public static final Codec<BrassProcessorSettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            StructureProcessorType.LIST_CODEC.fieldOf("boss_room_processors").forGetter(BrassProcessorSettings::bossSettings),
            StructureProcessorType.LIST_CODEC.fieldOf("room_processors").forGetter(BrassProcessorSettings::bossSettings),
            StructureProcessorType.LIST_CODEC.fieldOf("infested_boss_room_processors").forGetter(BrassProcessorSettings::infestedRoomSettings),
            StructureProcessorType.LIST_CODEC.fieldOf("infested_room_processors").forGetter(BrassProcessorSettings::infestedRoomSettings),
            StructureProcessorType.LIST_CODEC.fieldOf("garden_boss_room_processors").forGetter(BrassProcessorSettings::gardenBossSettings),
            StructureProcessorType.LIST_CODEC.fieldOf("garden_room_processors").forGetter(BrassProcessorSettings::gardenRoomSettings)
    ).apply(builder, BrassProcessorSettings::new));
}