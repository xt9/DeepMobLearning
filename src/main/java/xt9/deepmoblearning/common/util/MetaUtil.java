package xt9.deepmoblearning.common.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.api.mobs.*;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-12.
 */
public class MetaUtil {
    public static MobMetaData getMetaFromItemStackList(NonNullList<ItemStack> list) {
        MobMetaData meta;
        String unlocName = "";

        // Find
        for(ItemStack stack : list) {
            if(stack.getItem() instanceof ItemMobChip) {
                unlocName = stack.getUnlocalizedName();
                break;
            }
        }

        switch(unlocName) {
            case "item.deepmoblearning.mob_chip.zombie":
                meta = new ZombieMeta();
                break;
            case "item.deepmoblearning.mob_chip.skeleton":
                meta = new SkeletonMeta();
                break;
            case "item.deepmoblearning.mob_chip.blaze":
                meta = new BlazeMeta();
                break;
            default:
                meta = new EmptyMeta();
                break;
        }
        return meta;
    }
}
