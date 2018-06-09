package xt9.deepmoblearning.plugins.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2018-05-12.
 */
public class TrialKeystoneRecipe {
    public static ArrayList<TrialKeystoneRecipe> recipes = new ArrayList<>();
    public final ItemStack input;
    public final String keyType;
    public final String keyTier;
    public final NonNullList<ItemStack> outputs;


    private TrialKeystoneRecipe(ItemStack input, NonNullList<ItemStack> outputs, String keyType, String keyTier) {
        this.input = input;
        this.outputs = outputs;
        this.keyType = keyType;
        this.keyTier = keyTier;
    }

    public static void addRecipe(ItemStack input, NonNullList<ItemStack> outputs, String keyType, String keyTier) {
        recipes.add(new TrialKeystoneRecipe(input, outputs, keyType, keyTier));
    }
}