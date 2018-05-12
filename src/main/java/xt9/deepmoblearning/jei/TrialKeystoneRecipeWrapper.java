package xt9.deepmoblearning.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2018-05-12.
 */
public class TrialKeystoneRecipeWrapper implements IRecipeWrapper {
    private final ItemStack input;
    private final NonNullList<ItemStack> outputs;

    public TrialKeystoneRecipeWrapper(TrialKeystoneRecipe recipe) {
        this.input = recipe.input;
        this.outputs = recipe.outputs;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, outputs);
    }
}
