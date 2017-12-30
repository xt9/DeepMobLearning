package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-15.
 */
public class WitherSkeletonMeta extends MobMetaData {
    private String[] mobTrivia = {"Inflicts the wither effect", "Bring milk"};

    WitherSkeletonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }
    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityWitherSkeleton getEntity() {
        EntityWitherSkeleton entity = new EntityWitherSkeleton(this.world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
        return entity;
    }
}
