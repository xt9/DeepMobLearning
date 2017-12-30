package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.boss.EntityDragon;

/**
 * Created by xt9 on 2017-06-15.
 */
public class DragonMeta extends MobMetaData {
    private String[] mobTrivia = {"Resides in the end, does not harbor treasure", "Destroy it's crystals, break the cycle."};

    DragonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }
    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityDragon getEntity() {
        return new EntityDragon(this.world);
    }
}
