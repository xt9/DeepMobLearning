package xt9.deepmoblearning.plugins.patchouli;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariableProvider;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * Created by xt9 on 2019-02-26.
 */
@SuppressWarnings("unused")
public class ProcessorLookupRecipe implements IComponentProcessor {
    private ItemStack output;
    private ItemStack output2;
    private IRecipe recipe;
    private IRecipe recipe2;
    private String title;
    private String title2;
    private boolean error = false;
    private boolean error2 = false;

    @Override
    public void setup(IVariableProvider<String> provider) {
        title = provider.has("title") ? provider.get("title") : null;
        title2 = provider.has("title2") ? provider.get("title2") : null;

        String recipeOutput = provider.has("recipe") ? provider.get("recipe") : null;
        if(recipeOutput != null) {
            output = PatchouliAPI.instance.deserializeItemStack(recipeOutput);
            if (output != null) {
                for (IRecipe r : Module.dmlItemRecipes) {
                    if (r.getRecipeOutput().getItem().getRegistryName().equals(output.getItem().getRegistryName())) {
                        recipe = r;
                    }
                }
                error = recipe == null;
            }
        }

        String recipeOutput2 = provider.has("recipe2") ? provider.get("recipe2") : null;
        if(recipeOutput2 != null) {
            output2 = PatchouliAPI.instance.deserializeItemStack(recipeOutput2);
            if(output2 != null) {
                for (IRecipe r : Module.dmlItemRecipes) {
                    if (r.getRecipeOutput().getItem().getRegistryName().equals(output2.getItem().getRegistryName())) {
                        recipe2 = r;
                    }
                }
                error2 = recipe2 == null;
            }
        }
    }

    @Override
    public String process(String s) {
        int i = 1;
        if(recipe != null && !recipe.getIngredients().isEmpty()) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if(s.equals("ingredient" + i)) {
                    return PatchouliAPI.instance.serializeIngredient(ingredient);
                }
                i++;
            }
        }

        // Set this to 10 so it's always 10 even if the first recipe didn't have 9 ingredients
        i = 10;
        if(recipe2 != null && !recipe2.getIngredients().isEmpty()) {
            for (Ingredient ingredient : recipe2.getIngredients()) {
                if(s.equals("ingredient" + i)) {
                    return PatchouliAPI.instance.serializeIngredient(ingredient);
                }
                i++;
            }
        }

        if(recipe != null && s.equals("output")) {
            return PatchouliAPI.instance.serializeItemStack(recipe.getRecipeOutput());
        } else if(recipe == null && s.equals("output") && output != null) {
            return PatchouliAPI.instance.serializeItemStack(output);
        }

        if(recipe2 != null && s.equals("output2")) {
            return PatchouliAPI.instance.serializeItemStack(recipe2.getRecipeOutput());
        } else if(recipe2 == null && s.equals("output2") && output2 != null) {
            return PatchouliAPI.instance.serializeItemStack(output2);
        }

        if(error && s.equals("errortext")) {
            return "Could not find a recipe for " + output.getDisplayName() + ". Please consult JEI.";
        }

        if(error2 && s.equals("errortext2")) {
            return "Could not find a recipe for " + output2.getDisplayName() + ". Please consult JEI.";
        }

        switch (s) {
            case "title": return title != null ? title :
                recipe != null ? recipe.getRecipeOutput().getDisplayName() :
                    error && output != null ? output.getDisplayName() : "";
            case "title2": return title2 != null ? title2 :
                recipe2 != null ? recipe2.getRecipeOutput().getDisplayName() :
                    error2 && output2 != null ? output2.getDisplayName() : "";
        }
        return null;
    }

    @Override
    public boolean allowRender(String group) {
        switch (group) {
            case "error": return error;
            case "error2": return error2;
            case "recipeBG": return recipe != null;
            case "recipe2BG": return recipe2 != null;
            case "text": return recipe2 == null;
        }
        return false;
    }
}
