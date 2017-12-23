package xt9.deepmoblearning.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2017-12-20.
 */
public class ItemLivingMatter extends ItemBase {
    public ItemLivingMatter() {
        super("living_matter", 64, DeepConstants.ITEM_LIVING_MATTER_KEYS);
    }

    public static String getSubName(ItemStack stack) {
        Item item = stack.getItem();
        return ((ItemLivingMatter) item).getSubNames()[stack.getItemDamage()];
    }

}
