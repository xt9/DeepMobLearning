package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityEnderman;

/**
 * Created by xt9 on 2017-06-12.
 */
public class EndermanMeta extends MobMetaData {
    private String[] mobTrivia = {"Friendly unless provoked, dislikes rain.", "Teleports short distances"};

    EndermanMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityEnderman getEntity() {
        EntityEnderman entity = new EntityEnderman(this.world);
        return entity;
    }

}
