package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.api.mobs.EndermanMeta;
import xt9.deepmoblearning.api.mobs.*;

/**
 * Created by xt9 on 2017-06-12.
 */
public class MetaUtil {
    public static MobMetaData getMetaFromItemStack(ItemStack stack) {
        MobMetaData meta;
        String unlocName = stack.getUnlocalizedName();

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
            case "item.deepmoblearning.mob_chip.enderman":
                meta = new EndermanMeta();
                break;
            case "item.deepmoblearning.mob_chip.wither":
                meta = new WitherMeta();
                break;
            case "item.deepmoblearning.mob_chip.witch":
                meta = new WitchMeta();
                break;
            default:
                meta = new ZombieMeta();
                break;
        }
        return meta;
    }
}
