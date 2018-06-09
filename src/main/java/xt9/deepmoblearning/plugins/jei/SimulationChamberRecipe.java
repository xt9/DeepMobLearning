package xt9.deepmoblearning.plugins.jei;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.common.Registry;

import java.util.ArrayList;

/**
 * Created by xt9 on 2018-05-19.
 */
public class SimulationChamberRecipe {
    public static ArrayList<SimulationChamberRecipe> recipes = new ArrayList<>();
    public final ItemStack dataModel;
    public final ItemStack input = new ItemStack(Registry.polymerClay);
    public final ItemStack livingOutput;
    public final ItemStack pristineOutput;


    private SimulationChamberRecipe(ItemStack dataModel, ItemStack livingOutput, ItemStack pristineOutput) {
        this.dataModel = dataModel;
        this.livingOutput = livingOutput;
        this.pristineOutput = pristineOutput;
    }

    public static void addRecipe(ItemStack dataModel, ItemStack livingOutput, ItemStack pristineOutput) {
        recipes.add(new SimulationChamberRecipe(dataModel, livingOutput, pristineOutput));
    }
}
