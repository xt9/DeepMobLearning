package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.items.ItemSimulationSummary;

/**
 * Created by xt9 on 2017-06-22.
 */
public class CraftingHelper {
    public ItemStack createSimulationManifestFromMobChip(ItemStack chip, int amount) {
        String type = ItemMobChip.toHumdanReadable(chip);
        ItemStack manifest = new ItemStack(Registry.simulationSummary, amount);
        ItemSimulationSummary.setMobType(manifest, type);
        return manifest;
    }

    public static boolean chipMatchesOutput(ItemStack chip, ItemStack stack) {
        String chipType = ItemMobChip.toHumdanReadable(chip);
        String type = ItemSimulationSummary.getMobType(stack);
        return chipType.equals(type);
    }
}
