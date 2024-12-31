package io.github.razordevs.deep_aether.datagen.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import io.github.razordevs.deep_aether.DeepAether;
import io.github.razordevs.deep_aether.init.DABlocks;
import io.github.razordevs.deep_aether.init.DAItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.unity.data.UnityTags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DAItemTagData extends ItemTagsProvider {

    public DAItemTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, DeepAether.MODID, helper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Deep Aether Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.copy(DATags.Blocks.ROSEROOT_LOGS, DATags.Items.ROSEROOT_LOGS);
        this.copy(DATags.Blocks.YAGROOT_LOGS, DATags.Items.YAGROOT_LOGS);
        this.copy(DATags.Blocks.CRUDEROOT_LOGS, DATags.Items.CRUDEROOT_LOGS);
        this.copy(DATags.Blocks.CONBERRY_LOGS, DATags.Items.CONBERRY_LOGS);
        this.copy(DATags.Blocks.SUNROOT_LOGS, DATags.Items.SUNROOT_LOGS);
        this.copy(DATags.Blocks.NIMBUS_BLOCKS, DATags.Items.NIMBUS_BLOCKS);

        Collection<DeferredHolder<Item, ? extends Item>> items = DAItems.ITEMS.getEntries();

        IntrinsicTagAppender<Item> tag = this.tag(AetherTags.Items.TREATED_AS_AETHER_ITEM);
        items.forEach(item -> tag.add(item.get()));

        tag(AetherTags.Items.PLANKS_CRAFTING).add(
                DABlocks.ROSEROOT_PLANKS.get().asItem(),
                DABlocks.YAGROOT_PLANKS.get().asItem(),
                DABlocks.CRUDEROOT_PLANKS.get().asItem(),
                DABlocks.CONBERRY_PLANKS.get().asItem(),
                DABlocks.SUNROOT_PLANKS.get().asItem()
        );

        tag(DATags.Items.CRAFTS_ROSEROOT_PLANKS).add(
                DABlocks.ROSEROOT_LOG.get().asItem(),
                DABlocks.ROSEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_WOOD.get().asItem()
        );
        tag(DATags.Items.CRAFTS_YAGROOT_PLANKS).add(
                DABlocks.YAGROOT_LOG.get().asItem(),
                DABlocks.YAGROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_WOOD.get().asItem()
        );
        tag(DATags.Items.CRAFTS_CRUDEROOT_PLANKS).add(
                DABlocks.CRUDEROOT_LOG.get().asItem(),
                DABlocks.CRUDEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_WOOD.get().asItem()
        );
        tag(DATags.Items.CRAFTS_CONBERRY_PLANKS).add(
                DABlocks.CONBERRY_LOG.get().asItem(),
                DABlocks.CONBERRY_WOOD.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_LOG.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_WOOD.get().asItem()
        );
        tag(DATags.Items.CRAFTS_SUNROOT_PLANKS).add(
                DABlocks.SUNROOT_LOG.get().asItem(),
                DABlocks.SUNROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_WOOD.get().asItem()
        );

        tag(UnityTags.Items.PACKED_AETHER_MUD_CRAFTING).add(
                DAItems.AERGLOW_BLOSSOM.get()
        );

        tag(AetherTags.Items.SKYROOT_STICK_CRAFTING).add(
                DABlocks.ROSEROOT_PLANKS.get().asItem(),
                DABlocks.YAGROOT_PLANKS.get().asItem(),
                DABlocks.CRUDEROOT_PLANKS.get().asItem(),
                DABlocks.CONBERRY_PLANKS.get().asItem(),
                DABlocks.SUNROOT_PLANKS.get().asItem()
        );
        tag(AetherTags.Items.SKYROOT_TOOL_CRAFTING).add(
                DABlocks.ROSEROOT_PLANKS.get().asItem(),
                DABlocks.YAGROOT_PLANKS.get().asItem(),
                DABlocks.CRUDEROOT_PLANKS.get().asItem(),
                DABlocks.CONBERRY_PLANKS.get().asItem(),
                DABlocks.SUNROOT_PLANKS.get().asItem()
        );
        tag(AetherTags.Items.SKYROOT_REPAIRING).add(
                DABlocks.ROSEROOT_PLANKS.get().asItem(),
                DABlocks.YAGROOT_PLANKS.get().asItem(),
                DABlocks.CRUDEROOT_PLANKS.get().asItem(),
                DABlocks.CONBERRY_PLANKS.get().asItem(),
                DABlocks.SUNROOT_PLANKS.get().asItem()
        );
        tag(AetherTags.Items.SLIDER_DAMAGING_ITEMS).add(
                DAItems.SKYJADE_TOOLS_PICKAXE.get().asItem(),
                DAItems.STRATUS_PICKAXE.get().asItem()
        );
        tag(ItemTags.SMALL_FLOWERS).add(
                DABlocks.AERLAVENDER.get().asItem(),
                DABlocks.AETHER_CATTAILS.get().asItem(),
                DABlocks.GOLDEN_FLOWER.get().asItem(),
                DABlocks.RADIANT_ORCHID.get().asItem(),
                DABlocks.ENCHANTED_BLOSSOM.get().asItem(),
                DABlocks.SKY_TULIPS.get().asItem(),
                DABlocks.IASPOVE.get().asItem(),
                DABlocks.GOLDEN_ASPESS.get().asItem(),
                DABlocks.ECHAISY.get().asItem()
        );

        tag(ItemTags.LOGS).add(
                DABlocks.ROSEROOT_LOG.get().asItem(),
                DABlocks.ROSEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_WOOD.get().asItem(),
                DABlocks.YAGROOT_LOG.get().asItem(),
                DABlocks.YAGROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_WOOD.get().asItem(),
                DABlocks.CRUDEROOT_LOG.get().asItem(),
                DABlocks.CRUDEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_WOOD.get().asItem(),
                DABlocks.CONBERRY_LOG.get().asItem(),
                DABlocks.CONBERRY_WOOD.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_LOG.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_WOOD.get().asItem(),
                DABlocks.SUNROOT_LOG.get().asItem(),
                DABlocks.SUNROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_WOOD.get().asItem()
        );
        tag(ItemTags.LOGS_THAT_BURN).add(
                DABlocks.ROSEROOT_LOG.get().asItem(),
                DABlocks.ROSEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_WOOD.get().asItem(),
                DABlocks.YAGROOT_LOG.get().asItem(),
                DABlocks.YAGROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_WOOD.get().asItem(),
                DABlocks.CRUDEROOT_LOG.get().asItem(),
                DABlocks.CRUDEROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_WOOD.get().asItem(),
                DABlocks.CONBERRY_LOG.get().asItem(),
                DABlocks.CONBERRY_WOOD.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_LOG.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_WOOD.get().asItem(),
                DABlocks.SUNROOT_LOG.get().asItem(),
                DABlocks.SUNROOT_WOOD.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_LOG.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_WOOD.get().asItem()
        );
        tag(ItemTags.SIGNS).add(
                DABlocks.ROSEROOT_SIGN.get().asItem(),
                DABlocks.YAGROOT_SIGN.get().asItem(),
                DABlocks.CRUDEROOT_SIGN.get().asItem(),
                DABlocks.CONBERRY_SIGN.get().asItem(),
                DABlocks.SUNROOT_SIGN.get().asItem()
        );

        tag(ItemTags.STAIRS).add(
                DABlocks.ROSEROOT_STAIRS.get().asItem(),
                DABlocks.YAGROOT_STAIRS.get().asItem(),
                DABlocks.CRUDEROOT_STAIRS.get().asItem(),
                DABlocks.CONBERRY_STAIRS.get().asItem(),
                DABlocks.SUNROOT_STAIRS.get().asItem(),
                DABlocks.CLORITE_STAIRS.get().asItem(),
                DABlocks.ASETERITE_STAIRS.get().asItem(),
                DABlocks.COBBLED_ASETERITE_STAIRS.get().asItem(),
                DABlocks.ASETERITE_BRICKS_STAIRS.get().asItem(),
                DABlocks.BIG_HOLYSTONE_BRICKS_STAIRS.get().asItem(),
                DABlocks.HOLYSTONE_TILE_STAIRS.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_BRICK_STAIRS.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_TILE_STAIRS.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_BRICK_STAIRS.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_TILE_STAIRS.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_BRICK_STAIRS.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_TILE_STAIRS.get().asItem(),
                DABlocks.NIMBUS_STAIRS.get().asItem()
        );

        tag(ItemTags.SLABS).add(
                DABlocks.ROSEROOT_SLAB.get().asItem(),
                DABlocks.YAGROOT_SLAB.get().asItem(),
                DABlocks.CRUDEROOT_SLAB.get().asItem(),
                DABlocks.CONBERRY_SLAB.get().asItem(),
                DABlocks.SUNROOT_SLAB.get().asItem(),
                DABlocks.CLORITE_SLAB.get().asItem(),
                DABlocks.ASETERITE_SLAB.get().asItem(),
                DABlocks.COBBLED_ASETERITE_SLAB.get().asItem(),
                DABlocks.ASETERITE_BRICKS_SLAB.get().asItem(),
                DABlocks.BIG_HOLYSTONE_BRICKS_SLAB.get().asItem(),
                DABlocks.HOLYSTONE_TILE_SLAB.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_BRICK_SLAB.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_TILE_SLAB.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_BRICK_SLAB.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_TILE_SLAB.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_BRICK_SLAB.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_TILE_SLAB.get().asItem(),
                DABlocks.NIMBUS_SLAB.get().asItem()
        );

        tag(ItemTags.WALLS).add(
                DABlocks.ROSEROOT_WALL.get().asItem(),
                DABlocks.STRIPPED_ROSEROOT_WALL.get().asItem(),
                DABlocks.YAGROOT_WALL.get().asItem(),
                DABlocks.STRIPPED_YAGROOT_WALL.get().asItem(),
                DABlocks.CRUDEROOT_WALL.get().asItem(),
                DABlocks.STRIPPED_CRUDEROOT_WALL.get().asItem(),
                DABlocks.CONBERRY_WALL.get().asItem(),
                DABlocks.STRIPPED_CONBERRY_WALL.get().asItem(),
                DABlocks.SUNROOT_WALL.get().asItem(),
                DABlocks.STRIPPED_SUNROOT_WALL.get().asItem(),
                DABlocks.CLORITE_WALL.get().asItem(),
                DABlocks.ASETERITE_WALL.get().asItem(),
                DABlocks.COBBLED_ASETERITE_WALL.get().asItem(),
                DABlocks.ASETERITE_BRICKS_WALL.get().asItem(),
                DABlocks.BIG_HOLYSTONE_BRICKS_WALL.get().asItem(),
                DABlocks.HOLYSTONE_TILE_WALL.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_BRICK_WALL.get().asItem(),
                DABlocks.MOSSY_HOLYSTONE_TILE_WALL.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_BRICK_WALL.get().asItem(),
                DABlocks.GILDED_HOLYSTONE_TILE_WALL.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_BRICK_WALL.get().asItem(),
                DABlocks.BLIGHTMOSS_HOLYSTONE_TILE_WALL.get().asItem(),
                DABlocks.NIMBUS_WALL.get().asItem()
        );
        tag(ItemTags.FENCE_GATES).add(
                DABlocks.ROSEROOT_FENCE_GATE.get().asItem(),
                DABlocks.YAGROOT_FENCE_GATE.get().asItem(),
                DABlocks.CRUDEROOT_FENCE_GATE.get().asItem(),
                DABlocks.CONBERRY_FENCE_GATE.get().asItem(),
                DABlocks.SUNROOT_FENCE_GATE.get().asItem()
        );
        tag(ItemTags.LEAVES).add(
                DABlocks.ROSEROOT_LEAVES.get().asItem(),
                DABlocks.BLUE_ROSEROOT_LEAVES.get().asItem(),
                DABlocks.FLOWERING_ROSEROOT_LEAVES.get().asItem(),
                DABlocks.FLOWERING_BLUE_ROSEROOT_LEAVES.get().asItem(),
                DABlocks.YAGROOT_LEAVES.get().asItem(),
                DABlocks.CRUDEROOT_LEAVES.get().asItem(),
                DABlocks.CONBERRY_LEAVES.get().asItem(),
                DABlocks.SUNROOT_LEAVES.get().asItem()
        );
        tag(DATags.Items.EGGS).add(
                DAItems.QUAIL_EGG.get(),
                Items.EGG
        );
        tag(DATags.Items.MILK_BUCKETS).add(
                AetherItems.SKYROOT_MILK_BUCKET.get(),
                Items.MILK_BUCKET
        );
        tag(DATags.Items.STRATUS_REPAIRING).add(
                DAItems.STRATUS_INGOT.get()
        );
        tag(DATags.Items.SKYJADE_REPAIRING).add(
                DAItems.SKYJADE.get()
        );
        tag(DATags.Items.SKYJADE_ARMOR).add(
                DAItems.SKYJADE_HELMET.get(),
                DAItems.SKYJADE_CHESTPLATE.get(),
                DAItems.SKYJADE_LEGGINGS.get(),
                DAItems.SKYJADE_BOOTS.get(),
                DAItems.SKYJADE_GLOVES.get()

        );
        tag(DATags.Items.IS_GOLDEN_SWET_BALL).add(
                DAItems.GOLDEN_SWET_BALL.get()
        ).addOptional(
                ResourceLocation.fromNamespaceAndPath(DeepAether.AETHER_REDUX, "golden_swet_ball")
        ).addOptional(
                ResourceLocation.fromNamespaceAndPath(DeepAether.AETHER_GENESIS,"golden_swet_ball")
        );

        tag(DATags.Items.BRASS_DUNGEON_LOOT).add(
                DAItems.STORMFORGED_HELMET.get(),
                DAItems.STORMFORGED_CHESTPLATE.get(),
                DAItems.STORMFORGED_LEGGINGS.get(),
                DAItems.STORMFORGED_BOOTS.get(),
                DAItems.STORMFORGED_GLOVES.get(),
                DAItems.CLOUD_CAPE.get(),
                DAItems.WIND_SHIELD.get(),
                DAItems.AERCLOUD_NECKLACE.get(),
                DAItems.STORM_SWORD.get(),
                DAItems.STORM_BOW.get(),
                DAItems.BLADE_OF_LUCK.get(),
                DAItems.MUSIC_DISC_CYCLONE.get()
        );

        tag(AetherTags.Items.BRONZE_DUNGEON_LOOT).add(
                DAItems.MUSIC_DISC_ATTA.get()
        );

        tag(AetherTags.Items.SILVER_DUNGEON_LOOT).add(
                DAItems.MUSIC_DISC_FAENT.get()
        );

        tag(AetherTags.Items.GOLD_DUNGEON_LOOT).add(
                DAItems.MUSIC_DISC_HIMININN.get()
        );

        /*tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(DeepAether.LOST_AETHER_CONTENT, "aether_shields"))).add(
                DAItems.SKYJADE_SHIELD.get(),
                DAItems.STRATUS_SHIELD.get()
        );*/

        tag(Tags.Items.MUSIC_DISCS).add(
                DAItems.MUSIC_DISC_A_MORNING_WISH.get(),
                DAItems.MUSIC_DISC_NABOORU.get(),
                DAItems.MUSIC_DISC_CYCLONE.get()
        );

        tag(ItemTags.SAPLINGS).add(
                DABlocks.SUNROOT_SAPLING.get().asItem(),
                DABlocks.BLUE_ROSEROOT_SAPLING.get().asItem(),
                DABlocks.CONBERRY_SAPLING.get().asItem(),
                DABlocks.CRUDEROOT_SAPLING.get().asItem(),
                DABlocks.ROSEROOT_SAPLING.get().asItem(),
                DABlocks.YAGROOT_SAPLING.get().asItem()
        );

        tag(ItemTags.TRIM_MATERIALS).add(
                DAItems.SKYJADE.get(),
                DAItems.STRATUS_INGOT.get()
        );

        tag(ItemTags.TRIMMABLE_ARMOR).add(
                DAItems.SKYJADE_HELMET.get(),
                DAItems.SKYJADE_CHESTPLATE.get(),
                DAItems.SKYJADE_LEGGINGS.get(),
                DAItems.SKYJADE_BOOTS.get(),
                DAItems.STORMFORGED_HELMET.get(),
                DAItems.STORMFORGED_CHESTPLATE.get(),
                DAItems.STORMFORGED_LEGGINGS.get(),
                DAItems.STORMFORGED_BOOTS.get(),
                DAItems.SKYJADE_GLOVES.get(),
                DAItems.STRATUS_HELMET.get(),
                DAItems.STRATUS_CHESTPLATE.get(),
                DAItems.STRATUS_LEGGINGS.get(),
                DAItems.STRATUS_BOOTS.get(),
                DAItems.STRATUS_GLOVES.get()
        );

        tag(AetherTags.Items.ACCESSORIES_GLOVES).add(
                DAItems.SKYJADE_GLOVES.get(),
                DAItems.STORMFORGED_GLOVES.get(),
                DAItems.STRATUS_GLOVES.get()
        );

        tag(AetherTags.Items.ACCESSORIES_CAPES).add(
                DAItems.CLOUD_CAPE.get()
        );

        tag(AetherTags.Items.ACCESSORIES_RINGS).add(
                DAItems.SKYJADE_RING.get(),
                DAItems.STRATUS_RING.get(),
                DAItems.SPOOKY_RING.get(),
                DAItems.GRAVITITE_RING.get()
        );

        tag(AetherTags.Items.ACCESSORIES_MISCELLANEOUS).add(
                DAItems.SLIDER_EYE.get()
        );

        tag(AetherTags.Items.ACCESSORIES_SHIELDS).add(
                DAItems.WIND_SHIELD.asItem()
        );

        tag(AetherTags.Items.ACCESSORIES_PENDANTS).add(
                DAItems.MEDAL_OF_HONOR.get(),
                DAItems.AERCLOUD_NECKLACE.get(),
                DAItems.FLOATY_SCARF.get()
        );

        tag(ItemTags.HANGING_SIGNS).add(
                DAItems.CONBERRY_HANGING_SIGN.get(),
                DAItems.CRUDEROOT_HANGING_SIGN.get(),
                DAItems.ROSEROOT_HANGING_SIGN.get(),
                DAItems.YAGROOT_HANGING_SIGN.get(),
                DAItems.SUNROOT_HANGING_SIGN.get()
        );

        tag(ItemTags.TRIM_TEMPLATES).add(
                DAItems.STRATUS_SMITHING_TEMPLATE.get()
        );

        tag(ItemTags.COMPASSES).add(
                DAItems.BRONZE_COMPASS.get(),
                DAItems.SILVER_COMPASS.get(),
                DAItems.GOLD_COMPASS.get()
        );

        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
                DAItems.SKYJADE.get(),
                DAItems.STRATUS_INGOT.get()
        );

        tag(Tags.Items.INGOTS).add(
                DAItems.STRATUS_INGOT.get()
        );
    }
}
