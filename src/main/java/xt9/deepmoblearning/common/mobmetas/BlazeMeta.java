package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2017-06-09.
 */
public class BlazeMeta extends MobMetaData {
    static String[] mobTrivia = {"Bring buckets, and watch in despair", "as it evaporates, and everything is on fire", "You are on fire"};

    BlazeMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityBlaze getEntity(World world) {
        return new EntityBlaze(world);
    }

}
