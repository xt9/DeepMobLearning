package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityZombie;

/**
 * Created by xt9 on 2017-06-09.
 */
public class ZombieMeta extends MobMetaData {
    private String[] mobTrivia = {"They go moan in the night.", "Does not understand the need for", "personal space"};

    ZombieMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityZombie getEntity() {
        return new EntityZombie(this.world);
    }

    public EntityZombie getExtraEntity() {
        EntityZombie childEntity = new EntityZombie(this.world);
        childEntity.setChild(true);

        return childEntity;
    }

    public int getExtraInterfaceOffsetX() {
        return 21;
    }

    public int getExtraInterfaceOffsetY() {
        return 6;
    }
}
