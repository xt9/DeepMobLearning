package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-10.
 */
public class WitherMeta extends MobMetaData {
    static String[] mobTrivia = {"Do not approach this enemy. Run!", "I mean it has 3 heads, what could", "possibly go wrong?"};

    WitherMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityWither getEntity(World world) {
        return new EntityWither(world);
    }
}
