package xt9.deepmoblearning.plugins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


/**
 * Created by xt9 on 2018-01-14.
 */
public class ExtractionChamberRecipeWrapper implements IRecipeWrapper {
    private final ItemStack input;
    private final ItemStack output;

    public ExtractionChamberRecipeWrapper(ExtractionChamberRecipe recipe) {
        this.input = recipe.input;
        this.output = recipe.output;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }
}
