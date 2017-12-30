package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.boss.EntityWither;

/**
 * Created by xt9 on 2017-06-10.
 */
public class WitherMeta extends MobMetaData {
    private String[] mobTrivia = {"Do not approach this enemy. Run!", "I mean it has 3 heads, what could", "possibly go wrong?"};

    WitherMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityWither getEntity() {
        return new EntityWither(this.world);
    }
}
