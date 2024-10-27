package io.github.razordevs.deep_aether.item.misc;

import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import com.aetherteam.aether.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.recipes.BlockStateRecipe;
import io.github.razordevs.deep_aether.recipe.DARecipeTypes;
import io.github.razordevs.deep_aether.recipe.GlowingSporesRecipe;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class GlowingSporeItem extends Item implements ItemUseConversion<GlowingSporesRecipe> {

    public GlowingSporeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        InteractionResult result;
        BlockState state = level.getBlockState(context.getClickedPos());

        if (level.getBlockState(context.getClickedPos()).hasProperty(DoublePlantBlock.HALF)) {
            result = this.convertBlock(DARecipeTypes.GLOWING_SPORES_RECIPE.get(), context);
            if (state.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
                this.convertBlock(DARecipeTypes.GLOWING_SPORES_RECIPE.get(), new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), context.getItemInHand(), new BlockHitResult(context.getClickedPos().above(1).getCenter(), context.getClickedFace(), context.getClickedPos().above(1), context.isInside())));
            } else
                this.convertBlock(DARecipeTypes.GLOWING_SPORES_RECIPE.get(), new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), context.getItemInHand(), new BlockHitResult(context.getClickedPos().below(1).getCenter(), context.getClickedFace(), context.getClickedPos().below(1), context.isInside())));
        } else result = this.convertBlock(DARecipeTypes.GLOWING_SPORES_RECIPE.get(), context);
        return result;
    }

    @Override
    public <T extends GlowingSporesRecipe> InteractionResult convertBlock(RecipeType<T> recipeType, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = level.getBlockState(pos);

        for (RecipeHolder<T> glowingSporesRecipeRecipeHolder : level.getRecipeManager().getAllRecipesFor(recipeType)) {
            if (glowingSporesRecipeRecipeHolder != null) {
                BlockState newState = ((BlockStateRecipe) glowingSporesRecipeRecipeHolder.value()).getResultState(oldBlockState);
                if (((MatchEventRecipe) glowingSporesRecipeRecipeHolder.value()).matches(player, level, pos, heldItem, oldBlockState, newState, recipeType)) {
                    if (!level.isClientSide() && this.convertNoUpdate(level, pos, newState)) {
                        if (player != null && !player.getAbilities().instabuild) {
                            heldItem.shrink(1);
                        }

                        return InteractionResult.CONSUME;
                    }

                    if (level.isClientSide()) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    private boolean convertNoUpdate(Level level, BlockPos pos, BlockState newState) {
        level.setBlock(pos, newState, 18);
        return true;
    }
}
