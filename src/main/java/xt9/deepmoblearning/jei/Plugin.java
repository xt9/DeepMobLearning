package xt9.deepmoblearning.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;

@JEIPlugin
public class Plugin implements IModPlugin {
    public static IJeiHelpers jeiHelpers;


    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        ItemStack lmOverworldian = new ItemStack(Registry.livingMatter, 1, 0);
        ItemStack lmHellish = new ItemStack(Registry.livingMatter, 1, 1);
        ItemStack lmExtraterrestrial = new ItemStack(Registry.livingMatter, 1, 2);

        NonNullList<ItemStack> dataModels = NonNullList.create();
        for (int i = 0; i < DeepConstants.ITEM_MOB_CHIP_KEYS.length; i++) {
            ItemStack is = new ItemStack(Registry.mobChip);
            is.setItemDamage(i);
            dataModels.add(is);
        }

        registry.addIngredientInfo(lmOverworldian, ItemStack.class, "deepmoblearning.jei.description.living_matter.overworldian");
        registry.addIngredientInfo(lmHellish, ItemStack.class, "deepmoblearning.jei.description.living_matter.hellish");
        registry.addIngredientInfo(lmExtraterrestrial, ItemStack.class, "deepmoblearning.jei.description.living_matter.extraterrestrial");

        registry.addIngredientInfo(dataModels, ItemStack.class, "deepmoblearning.jei.description.mob_chip");
        registry.addIngredientInfo(new ItemStack(Registry.simulationChamber), ItemStack.class, "deepmoblearning.jei.description.simulation_chamber");
        registry.addIngredientInfo(new ItemStack(Registry.deepLearner), ItemStack.class, "deepmoblearning.jei.description.deep_learner");

    }
}
