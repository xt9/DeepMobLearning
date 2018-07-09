package xt9.deepmoblearning.plugins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.common.util.Color;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2018-05-12.
 */
public class TrialKeystoneRecipeWrapper implements IRecipeWrapper {
    private final ItemStack input;
    private final String keyType;
    private final String keyTier;
    private final NonNullList<ItemStack> outputs;

    public TrialKeystoneRecipeWrapper(TrialKeystoneRecipe recipe) {
        this.input = recipe.input;
        this.outputs = recipe.outputs;
        this.keyType = I18n.format("deepmoblearning.jei.category.trial_keystone.type", recipe.keyType);
        this.keyTier = I18n.format("deepmoblearning.jei.category.trial_keystone.type", recipe.keyTier);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer render = minecraft.fontRenderer;

        render.drawStringWithShadow(keyTier, 52 - render.getStringWidth(keyTier), 4, Color.WHITE);
        render.drawStringWithShadow(keyType, 64, 4, Color.WHITE);
    }
}
