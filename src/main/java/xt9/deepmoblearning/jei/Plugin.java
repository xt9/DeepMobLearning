package xt9.deepmoblearning.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.client.gui.ExtractionChamberGui;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JEIPlugin
public class Plugin implements IModPlugin {
    private static IJeiHelpers jeiHelpers;
    private static ExtractionChamberRecipeCategory exChamberCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        exChamberCategory = new ExtractionChamberRecipeCategory(guiHelper);
        registry.addRecipeCategories(exChamberCategory);
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        // Register recipe handlers
        registry.handleRecipes(exChamberCategory.getRecipeClass(), ExtractionChamberRecipeWrapper::new, exChamberCategory.getUid());

        Map<ItemStack, NonNullList<ItemStack>> pristineTables = new HashMap<>();

        Registry.pristineMatter.forEach(
            pristineItem -> pristineTables.put(new ItemStack(pristineItem, 1), Config.LootParser.getLootEntries(pristineItem.getMobKey()))
        );

        pristineTables.forEach(
            (input, outputs) -> outputs.forEach(
                (output) -> ExtractionChamberRecipe.addRecipe(input, output)
            )
        );

        // Add recipes
        registry.addRecipes(new ArrayList(ExtractionChamberRecipe.recipes), exChamberCategory.getUid());
        registry.addRecipeClickArea(ExtractionChamberGui.class, 84 , 23, 6, 35, exChamberCategory.getUid());
        exChamberCategory.addCatalysts(registry);

        NonNullList<ItemStack> matter = NonNullList.create();
        for (int i = 0; i < Registry.livingMatter.size(); i++) {
            matter.add(new ItemStack(Registry.livingMatter.get(i)));
        }

        NonNullList<ItemStack> dataModels = NonNullList.create();
        dataModels.add(new ItemStack(Registry.dataModelBlank));
        for (int i = 0; i < Registry.dataModels.size(); i++) {
            dataModels.add(new ItemStack(Registry.dataModels.get(i)));
        }

        NonNullList<ItemStack> pristines = NonNullList.create();
        for (int i = 0; i < Registry.pristineMatter.size(); i++) {
            pristines.add(new ItemStack(Registry.pristineMatter.get(i)));
        }

        registry.addIngredientInfo(new ItemStack(Registry.simulationChamber), ItemStack.class, "deepmoblearning.jei.description.simulation_chamber");
        registry.addIngredientInfo(new ItemStack(Registry.deepLearner), ItemStack.class, "deepmoblearning.jei.description.deep_learner");

        registry.addIngredientInfo(matter, ItemStack.class, "deepmoblearning.jei.description.living_matter");
        registry.addIngredientInfo(pristines, ItemStack.class, "deepmoblearning.jei.description.pristine_matter");


        registry.addIngredientInfo(dataModels, ItemStack.class, "# of monsters defeated to reach the next tier",
                Config.modelExperience.get("killsToTier1").getInt() + " <- §3§oFaulty to Basic§r",
                Config.modelExperience.get("killsToTier2").getInt() + " <- §3§oBasic to Advanced§r",
                Config.modelExperience.get("killsToTier3").getInt() + " <- §3§oAdvanced to Superior§r",
                Config.modelExperience.get("killsToTier4").getInt() + " <- §3§oSuperior to Self Aware§r",
                "\nHigher tiers get more data from defeating foes."
        );

    }


}
