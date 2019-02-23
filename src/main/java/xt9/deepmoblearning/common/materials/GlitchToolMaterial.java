package xt9.deepmoblearning.common.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import xt9.deepmoblearning.common.Registry;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2019-02-20.
 */
public class GlitchToolMaterial implements IItemTier {
    @Override
    public int getHarvestLevel() {
        return 3;
    }

    @Override
    public int getMaxUses() {
        return 2200;
    }

    @Override
    public float getEfficiency() {
        return 3.0f;
    }

    @Override
    public float getAttackDamage() {
        return 9;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromStacks(new ItemStack(Registry.glitchInfusedIngot));
    }
}
