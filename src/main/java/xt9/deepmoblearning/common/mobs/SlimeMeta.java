package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntitySlime;

/**
 * Created by xt9 on 2017-06-15.
 */
public class SlimeMeta extends MobMetaData {
    private String[] mobTrivia = {"The bounce", "bounce his bounce", "squish - \"A slime haiku\""};

    SlimeMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }
    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntitySlime getEntity() {
        return new EntitySlime(this.world);
    }
}
