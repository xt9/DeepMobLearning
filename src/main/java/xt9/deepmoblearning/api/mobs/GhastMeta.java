package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityGhast;

/**
 * Created by xt9 on 2017-06-15.
 */
public class GhastMeta extends MobMetaData {
    private EntityGhast entity;
    private int interfaceScale = 10;
    private int interfaceOffsetX = 0;
    private int interfaceOffsetY = -20;
    private int numberOfHearts = 5;
    private String mobName = "The Ghast";
    private String[] mobTrivia = {"Quite emo"};

    public GhastMeta() {
        super();
        this.entity = new EntityGhast(this.world);
    }

    public String getMobName() {
        return this.mobName;
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public int getNumberOfHearts() {
        return this.numberOfHearts;
    }

    public EntityGhast getEntity() {
        return this.entity;
    }

    public int getInterfaceScale() {
        return this.interfaceScale;
    }

    public int getInterfaceOffsetX() {
        return this.interfaceOffsetX;
    }

    public int getInterfaceOffsetY() {
        return this.interfaceOffsetY;
    }
}
