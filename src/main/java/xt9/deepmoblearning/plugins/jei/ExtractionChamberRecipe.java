package xt9.deepmoblearning.plugins.jei;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by xt9 on 2018-01-14.
 */
public class ExtractionChamberRecipe {
    public static ArrayList<ExtractionChamberRecipe> recipes = new ArrayList<>();
    public final ItemStack input;
    public final ItemStack output;


    public ExtractionChamberRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public static void addRecipe(ItemStack input, ItemStack output) {
        recipes.add(new ExtractionChamberRecipe(input, output));
    }
}
