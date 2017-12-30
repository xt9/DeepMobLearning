package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityGhast;

/**
 * Created by xt9 on 2017-06-15.
 */
public class GhastMeta extends MobMetaData {
    private String[] mobTrivia = {"If you hear something that sounds like", "a crying llama, you're probably hearing a ghast"};

    GhastMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityGhast getEntity() {
        return new EntityGhast(this.world);
    }

}
