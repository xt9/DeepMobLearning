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

/**
 * Created by xt9 on 2018-05-19.
 */
public class SimulationChamberRecipeCategory implements IRecipeCategory {
    private ItemStack catalyst;
    private IDrawable background;
    private IDrawableAnimated progress;

    public SimulationChamberRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/jei/simulation_chamber.png");
        this.catalyst = new ItemStack(Registry.simulationChamberItem);

        background = guiHelper.createDrawable(base, 0, 0, 116, 43, 0, 0, 0, 0);
        IDrawableStatic progress = guiHelper.createDrawable(base, 0, 43, 35, 6);
        this.progress = guiHelper.createAnimatedDrawable(progress, 120, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();

        guiItemStacks.init(0, true, 3, 3);
        guiItemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));

        guiItemStacks.init(1, true, 27, 3);
        guiItemStacks.set(1, ingredients.getInputs(ItemStack.class).get(1));

        guiItemStacks.init(2, false, 95, 3);
        guiItemStacks.set(2, ingredients.getOutputs(ItemStack.class).get(0));

        guiItemStacks.init(3, false, 75 , 25);
        guiItemStacks.set(3, ingredients.getOutputs(ItemStack.class).get(1));
    }

    public void addCatalysts(IModRegistry registry) {
        registry.addRecipeCatalyst(catalyst, getUid());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progress.draw(minecraft, 52, 9);
    }

    @Override
    public String getUid() {
        return DeepConstants.MODID + ".simulation_chamber";
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
    public IDrawable getBackground() {
        return background;
    }
}
