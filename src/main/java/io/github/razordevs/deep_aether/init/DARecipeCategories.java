package io.github.razordevs.deep_aether.init;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import io.github.razordevs.deep_aether.recipe.DABookCategory;
import io.github.razordevs.deep_aether.recipe.DARecipeTypes;
import io.github.razordevs.deep_aether.recipe.combiner.CombinerRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.ArrayList;
import java.util.List;

public class DARecipeCategories {

//    public static final EnumProxy<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_SEARCH = new EnumProxy<>(
//            RecipeBookCategories.class, (Supplier<List<ItemStack>>) ()->new ArrayList<>() {{
//        new ItemStack(Items.COMPASS);
//    }});

    //    public static final EnumProxy<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_FODDER = new EnumProxy<>(
//            RecipeBookCategories.class, (Supplier<List<ItemStack>>) ()->new ArrayList<>() {{
//        new ItemStack(DAItems.MOA_FODDER.get());
//    }});

//    public static final EnumProxy<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_MISC = new EnumProxy<>(
//            RecipeBookCategories.class, (Supplier<List<ItemStack>>) ()->new ArrayList<>() {{
//        new ItemStack(DAItems.ANTIDOTE.get());
//    }});

    public static final Supplier<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.valueOf("DEEP_AETHER_COMBINEABLE_SEARCH"));

    public static final Supplier<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_FODDER = Suppliers.memoize(() -> RecipeBookCategories.valueOf("DEEP_AETHER_COMBINEABLE_FODDER"));

    public static final Supplier<RecipeBookCategories> DEEP_AETHER_COMBINEABLE_MISC = Suppliers.memoize(() -> RecipeBookCategories.valueOf("DEEP_AETHER_COMBINEABLE_MISC"));

    /**
     * Registers the mod's categories to be used in-game, along with functions to sort items.
     * To add sub-categories to be used by the search, use addAggregateCategories with the
     * search category as the first parameter.
     */
    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        // Combination
        event.registerBookCategories(DARecipeBookTypes.COMBINER, ImmutableList.of(DEEP_AETHER_COMBINEABLE_SEARCH.get(), DEEP_AETHER_COMBINEABLE_FODDER.get(), DEEP_AETHER_COMBINEABLE_MISC.get()));
        event.registerAggregateCategory(DEEP_AETHER_COMBINEABLE_SEARCH.get(), ImmutableList.of(DEEP_AETHER_COMBINEABLE_FODDER.get(), DEEP_AETHER_COMBINEABLE_MISC.get()));

        event.registerRecipeCategoryFinder(DARecipeTypes.COMBINING.get(), recipe -> {
                    if(recipe.value() instanceof CombinerRecipe value){
                        return value.daCategory() == DABookCategory.COMBINEABLE_FODDER ?
                                DEEP_AETHER_COMBINEABLE_FODDER.get() :
                                DEEP_AETHER_COMBINEABLE_MISC.get();
                    }
                    return DEEP_AETHER_COMBINEABLE_MISC.get();
                });
    }
}
