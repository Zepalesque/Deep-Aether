package io.github.razordevs.deep_aether.block.misc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class BurnableBlockItem extends BlockItem {
    private final int burnTime;

    public BurnableBlockItem(int burnTime, Block block, Item.Properties properties) {
        super(block, properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }
}
