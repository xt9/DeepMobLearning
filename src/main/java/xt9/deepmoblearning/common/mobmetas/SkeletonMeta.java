package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-09.
 */
public class SkeletonMeta extends MobMetaData {
    static String[] mobTrivia = {"A formidable archer, which seem to be running", "some sort of cheat engine", "A shield could prove useful"};

    SkeletonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntitySkeleton getEntity(World world) {
        EntitySkeleton entity = new EntitySkeleton(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
        return entity;
    }
}
