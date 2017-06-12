package xt9.deepmoblearning.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-12.
 */
public class MobChipHelper {
    public static NonNullList<ItemStack> getValidFromList(NonNullList<ItemStack> list) {
        NonNullList<ItemStack> filteredList = NonNullList.create();

        for(ItemStack stack : list) {
            String unloc = stack.getUnlocalizedName();
            Item item = stack.getItem();

            if(item instanceof ItemMobChip && !unloc.equals("item.deepmoblearning.mob_chip.default")) {
                filteredList.add(stack);
            }
        }

        return filteredList;
    }
}
