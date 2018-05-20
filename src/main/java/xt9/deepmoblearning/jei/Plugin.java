package xt9.deepmoblearning.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.client.gui.ExtractionChamberGui;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.util.DataModel;
import xt9.deepmoblearning.common.util.Tier;
import xt9.deepmoblearning.common.util.TrialKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JEIPlugin
public class Plugin implements IModPlugin {
    private static IJeiHelpers jeiHelpers;
    private static SimulationChamberRecipeCategory simChamberCategory;
    private static ExtractionChamberRecipeCategory exChamberCategory;
    private static TrialKeystoneRecipeCategory trialKeystoneCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        simChamberCategory = new SimulationChamberRecipeCategory(guiHelper);
        registry.addRecipeCategories(simChamberCategory);

        exChamberCategory = new ExtractionChamberRecipeCategory(guiHelper);
        registry.addRecipeCategories(exChamberCategory);

        trialKeystoneCategory = new TrialKeystoneRecipeCategory(guiHelper);
        registry.addRecipeCategories(trialKeystoneCategory);
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        // Register recipe handlers
        registry.handleRecipes(SimulationChamberRecipe.class, SimulationChamberRecipeWrapper::new, simChamberCategory.getUid());
        registry.handleRecipes(ExtractionChamberRecipe.class, ExtractionChamberRecipeWrapper::new, exChamberCategory.getUid());
        registry.handleRecipes(TrialKeystoneRecipe.class, TrialKeystoneRecipeWrapper::new, trialKeystoneCategory.getUid());

        addSimulationChamberRecipes(registry);
        addExtractionChamberRecipes(registry);
        addTrialKeystoneRecipes(registry);

        addItemDescriptions(registry);
    }

    private void addSimulationChamberRecipes(IModRegistry registry) {
        Registry.dataModels.forEach(itemDataModel -> {
            ItemStack dataModel = DataModel.getModelFromMobKey(itemDataModel.getMobKey());
            ItemStack livingMatter = DataModel.getMobMetaData(dataModel).getLivingMatterStack(1);
            ItemStack pristineMatterStack = DataModel.getMobMetaData(dataModel).getPristineMatterStack(1);

            // Init as tier 1 since faulty models don't work in the simulation chamber
            DataModel.setTier(dataModel, 1);
            SimulationChamberRecipe.addRecipe(dataModel, livingMatter, pristineMatterStack);
        });

        registry.addRecipes(new ArrayList(SimulationChamberRecipe.recipes), simChamberCategory.getUid());
        simChamberCategory.addCatalysts(registry);
    }

    @SuppressWarnings("unchecked")
    private void addExtractionChamberRecipes(IModRegistry registry) {
        Map<ItemStack, NonNullList<ItemStack>> pristineTables = new HashMap<>();

        Registry.pristineMatter.forEach(
            pristineItem -> pristineTables.put(new ItemStack(pristineItem, 1), Config.LootParser.getPristineLootEntries(pristineItem.getMobKey()))
        );

        pristineTables.forEach(
            (input, outputs) -> outputs.forEach(
                (output) -> ExtractionChamberRecipe.addRecipe(input, output)
            )
        );

        registry.addRecipes(new ArrayList(ExtractionChamberRecipe.recipes), exChamberCategory.getUid());
        registry.addRecipeClickArea(ExtractionChamberGui.class, 84 , 23, 6, 35, exChamberCategory.getUid());
        exChamberCategory.addCatalysts(registry);
    }

    @SuppressWarnings("unchecked")
    private void addTrialKeystoneRecipes(IModRegistry registry) {
        TrialFactory.getValidTrials().forEach(mobkey -> {
            // Add each tier and their rewards
            for (int i = 0; i < 5; i++) {
                ItemStack dataModel = DataModel.getModelFromMobKey(mobkey);
                ItemStack trialKey = new ItemStack(Registry.trialKey);
                MobMetaData data = DataModel.getMobMetaData(dataModel);

                TrialKey.setAttunedNBT(trialKey, dataModel);
                TrialKey.setTier(trialKey, i);

                TrialKeystoneRecipe.addRecipe(trialKey, TrialFactory.getRewards(trialKey), data.getName(), Tier.getTierName(i, false));
            }
        });


        registry.addRecipes(new ArrayList(TrialKeystoneRecipe.recipes), trialKeystoneCategory.getUid());
        trialKeystoneCategory.addCatalysts(registry);
    }

    private void addItemDescriptions(IModRegistry registry) {
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


        registry.addIngredientInfo(dataModels, ItemStack.class, "# of monsters defeated to reach the next tier",
            Config.modelExperience.get("killsToTier1").getInt() + " <- §3§oFaulty to Basic§r",
            Config.modelExperience.get("killsToTier2").getInt() + " <- §3§oBasic to Advanced§r",
            Config.modelExperience.get("killsToTier3").getInt() + " <- §3§oAdvanced to Superior§r",
            Config.modelExperience.get("killsToTier4").getInt() + " <- §3§oSuperior to Self Aware§r",
            "\nHigher tiers get more data from defeating foes."
        );

        registry.addIngredientInfo(new ItemStack(Registry.deepLearner), ItemStack.class, "deepmoblearning.jei.description.deep_learner");
        registry.addIngredientInfo(new ItemStack(Registry.glitchInfusedIngot), ItemStack.class, JEIStringBuilder.getInfusedIngotLines());

        registry.addIngredientInfo(new ItemStack(Registry.trialKey), ItemStack.class, JEIStringBuilder.getTrialKeyLines());
        registry.addIngredientInfo(new ItemStack(Registry.trialKeystoneItem), ItemStack.class, JEIStringBuilder.getTrialKeystoneLines());
    }




}
