package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityBlaze;

/**
 * Created by xt9 on 2017-06-09.
 */
public class BlazeMeta extends MobMetaData {
    private String[] mobTrivia = {"Bring buckets, and watch in despair", "as it evaporates, and everything is on fire", "You are on fire"};

    BlazeMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityBlaze getEntity() {
        return new EntityBlaze(this.world);
    }

}
