package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-09.
 */
public class SkeletonMeta extends MobMetaData {
    private String[] mobTrivia = {"A formidable archer, which seem to be running", "some sort of cheat engine", "A shield could prove useful"};

    SkeletonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntitySkeleton getEntity() {
        EntitySkeleton entity = new EntitySkeleton(this.world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
        return entity;
    }
}
