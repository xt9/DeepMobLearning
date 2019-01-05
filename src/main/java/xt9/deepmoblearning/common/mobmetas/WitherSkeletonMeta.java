package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-15.
 */
public class WitherSkeletonMeta extends MobMetaData {
    static String[] mobTrivia = {"Inflicts the wither effect", "Bring milk"};

    WitherSkeletonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityWitherSkeleton getEntity(World world) {
        EntityWitherSkeleton entity = new EntityWitherSkeleton(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
        return entity;
    }
}
