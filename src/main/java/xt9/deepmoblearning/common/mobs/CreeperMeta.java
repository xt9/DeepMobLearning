package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityCreeper;

/**
 * Created by xt9 on 2017-06-12.
 */
public class CreeperMeta extends MobMetaData {
    private String[] mobTrivia = {"Will blow up your base if", "left unattended."};

    CreeperMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityCreeper getEntity() {
        return new EntityCreeper(this.world);
    }
}
