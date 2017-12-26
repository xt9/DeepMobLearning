package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityBlaze;

/**
 * Created by xt9 on 2017-06-09.
 */
public class BlazeMeta extends MobMetaData {
    private EntityBlaze entity;
    private int interfaceScale = 48;
    private int interfaceOffsetX = 10;
    private int interfaceOffsetY = 20;
    private int numberOfHearts = 10;
    private String mobName = "The Blaze";
    private String[] mobTrivia = {"Bring buckets, and watch in despair", "as it evaporates, and everything is on fire", "You are on fire"};

    public BlazeMeta() {
        super();
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

    public EntityBlaze getEntity() {
        this.entity = new EntityBlaze(this.world);
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

    public int getSimulationTickCost() { return 256; }
}
