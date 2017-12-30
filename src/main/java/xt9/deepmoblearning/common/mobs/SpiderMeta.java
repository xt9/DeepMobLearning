package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;

/**
 * Created by xt9 on 2017-06-12.
 */
public class SpiderMeta extends MobMetaData {
    private String[] mobTrivia = {"Nocturnal douchebags, beware", "Drops strands of string for some reason.."};

    SpiderMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntitySpider getEntity() {
        return new EntitySpider(this.world);
    }

    public EntitySpider getExtraEntity() {
        return new EntityCaveSpider(this.world);
    }

    public int getExtraInterfaceOffsetX() {
        return 5;
    }

    public int getExtraInterfaceOffsetY() {
        return -25;
    }
}
