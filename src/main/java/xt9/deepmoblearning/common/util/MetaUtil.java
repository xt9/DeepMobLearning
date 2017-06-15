package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.api.mobs.EndermanMeta;
import xt9.deepmoblearning.api.mobs.*;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-12.
 */
public class MetaUtil {
    public static MobMetaData getMetaFromItemStack(ItemStack stack) {
        MobMetaData meta;
        String subName = ItemMobChip.getSubName(stack);

        switch(subName) {
            case "zombie": meta = new ZombieMeta(); break;
            case "skeleton": meta = new SkeletonMeta(); break;
            case "blaze": meta = new BlazeMeta(); break;
            case "enderman": meta = new EndermanMeta(); break;
            case "wither": meta = new WitherMeta(); break;
            case "witch": meta = new WitchMeta(); break;
            case "spider": meta = new SpiderMeta(); break;
            case "creeper": meta = new CreeperMeta(); break;
            case "ghast": meta = new GhastMeta(); break;
            case "witherskeleton": meta = new WitherSkeletonMeta(); break;
            default: meta = new ZombieMeta(); break;
        }
        return meta;
    }
}
