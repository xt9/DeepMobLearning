package xt9.deepmoblearning.plugins.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;

import java.util.Collections;
import java.util.List;

/**
 * Created by xt9 on 2018-05-12.
 */
public class TrialKeystoneRecipeCategory implements IRecipeCategory {
    private ItemStack catalyst;
    private IDrawable background;

    public TrialKeystoneRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/jei/trial_keystone.png");
        this.catalyst = new ItemStack(Registry.trialKeystoneItem);

        background = guiHelper.createDrawable(base, 0, 0, 100, 18, 18, 0, 36, 18);
    }

    @SuppressWarnings("NullableProblems")
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 36, 18);
        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));

        guiItemStacks.init(1, false, 64, 18);
        guiItemStacks.init(2, false, 82, 18);
        guiItemStacks.init(3, false, 100, 18);
        guiItemStacks.init(4, false, 118, 18);

        for (int i = 0; i < Math.min(ingredients.getOutputs(ItemStack.class).size(), 4); i++) {
            guiItemStacks.set(i + 1, ingredients.getOutputs(ItemStack.class).get(i));
        }
    }

    public void addCatalysts(IModRegistry registry) {
        registry.addRecipeCatalyst(catalyst, getUid());
    }

    @Override
    public String getUid() {
        return DeepConstants.MODID + ".trial_keystone";
    }

    @Override
    public String getTitle() {
        return catalyst.getDisplayName();
    }

    @Override
    public String getModName() {
        return DeepConstants.MODID;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }


}
