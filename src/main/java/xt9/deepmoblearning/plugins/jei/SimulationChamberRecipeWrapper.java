package xt9.deepmoblearning.plugins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.util.Color;
import xt9.deepmoblearning.common.util.DataModel;
import xt9.deepmoblearning.common.util.Tier;
import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2018-05-19.
 */
public class SimulationChamberRecipeWrapper implements IRecipeWrapper {
    private long ticks = 0;
    private long lastWorldTime;
    private final ItemStack dataModel;
    private final NonNullList<ItemStack> inputs = NonNullList.create();
    private final NonNullList<ItemStack> outputs = NonNullList.create();


    public SimulationChamberRecipeWrapper(SimulationChamberRecipe recipe) {
        dataModel = recipe.dataModel;

        inputs.add(dataModel);
        inputs.add(recipe.input);
        outputs.add(recipe.livingOutput);
        outputs.add(recipe.pristineOutput);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutputs(ItemStack.class, outputs);
    }

    private void cycleTier() {
        int currentTier = DataModel.getTier(dataModel);
        if(Tier.isMaxTier(currentTier)) {
            DataModel.setTier(dataModel, 1);
        } else {
            DataModel.setTier(dataModel,currentTier + 1);
        }
    }


    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        renderPristineChance(minecraft);

        if(lastWorldTime == minecraft.world.getTotalWorldTime()) {
            return;
        } else {
            ticks++;
            lastWorldTime = minecraft.world.getTotalWorldTime();
        }

        if(ticks % (DeepConstants.TICKS_TO_SECOND * 1.5F) == 0)  {
            cycleTier();
        }

    }

    public void renderPristineChance(Minecraft minecraft) {
        FontRenderer render = minecraft.fontRenderer;

        String tierName = Tier.getTierName(DataModel.getTier(dataModel), false);
        render.drawStringWithShadow(tierName, 70 - render.getStringWidth(tierName), 30, Color.WHITE);

        int pristineChance = DataModel.getPristineChance(dataModel);
        String chanceText = pristineChance + "%";
        render.drawStringWithShadow(chanceText, 97, 31, Color.WHITE);
    }
}