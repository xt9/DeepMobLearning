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
 * Created by xt9 on 2018-01-14.
 */
public class ExtractionChamberRecipeCategory implements IRecipeCategory {
    private ItemStack catalyst;
    private IDrawable background;
    private IDrawableAnimated progress;

    public ExtractionChamberRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/jei/extraction_chamber.png");
        this.catalyst = new ItemStack(Registry.extractionChamberItem);

        background = guiHelper.createDrawable(base, 0, 0, 103, 30, 0, 0, 0, 0);
        IDrawableStatic progress = guiHelper.createDrawable(base, 0, 30, 35, 6);
        this.progress = guiHelper.createAnimatedDrawable(progress, 120, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 8, 6);
        guiItemStacks.init(1, false, 76, 6);
        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));
        guiItemStacks.set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

    public void addCatalysts(IModRegistry registry) {
        registry.addRecipeCatalyst(catalyst, getUid());
    }

    @Override
    public String getUid() {
        return DeepConstants.MODID + ".extraction_chamber";
    }

    @Override
    public String getTitle() {
        return catalyst.getDisplayName();
    }


    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progress.draw(minecraft, 34, 12);
    }


    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }


    @Override
    public String getModName() {
        return DeepConstants.MODID;
    }
}
