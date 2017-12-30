package xt9.deepmoblearning.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2017-12-20.
 */
public class ItemPristineMatter extends ItemBase {
    public ItemPristineMatter() {
        super("pristine_matter", 64, DeepConstants.ITEM_PRISTINE_MATTER_KEYS);
    }

    public static String getSubName(ItemStack stack) {
        Item item = stack.getItem();
        return ((ItemPristineMatter) item).getSubNames()[stack.getItemDamage()];
    }

    public static int getItemDamageFromKey(String key) {
        String[] subNames = DeepConstants.ITEM_PRISTINE_MATTER_KEYS;
        for (int i = 0; i < subNames.length; i++) {
            if(key == subNames[i]) {
                return i;
            }
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
