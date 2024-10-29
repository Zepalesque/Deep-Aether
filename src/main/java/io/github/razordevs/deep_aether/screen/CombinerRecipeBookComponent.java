package io.github.razordevs.deep_aether.screen;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class CombinerRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.deep_aether.recipebook.toggleRecipes.combinable");
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted"));

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    public void slotClicked(@Nullable Slot pSlot) {
        super.slotClicked(pSlot);
        if (pSlot != null && pSlot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipeHolder, List<Slot> slots) {
        ItemStack itemstack = recipeHolder.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipeHolder);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), slots.get(3).x, slots.get(3).y);
        NonNullList<Ingredient> nonnulllist = recipeHolder.value().getIngredients();
        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for(int i = 0; i < 3; i++) {
            if (!iterator.hasNext()) {
                return;
            }
            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }
    }
}
