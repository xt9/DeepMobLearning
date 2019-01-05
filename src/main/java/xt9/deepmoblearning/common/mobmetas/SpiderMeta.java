package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-12.
 */
public class SpiderMeta extends MobMetaData {
    static String[] mobTrivia = {"Nocturnal douchebags, beware", "Drops strands of string for some reason.."};

    SpiderMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntitySpider getEntity(World world) {
        return new EntitySpider(world);
    }

    public EntitySpider getExtraEntity(World world) {
        return new EntityCaveSpider(world);
    }

    public int getExtraInterfaceOffsetX() {
        return 5;
    }

    public int getExtraInterfaceOffsetY() {
        return -25;
    }
}
