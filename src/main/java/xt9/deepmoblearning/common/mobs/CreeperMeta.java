package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityCreeper;

/**
 * Created by xt9 on 2017-06-12.
 */
public class CreeperMeta extends MobMetaData {
    private EntityCreeper entity;
    private int interfaceScale = 42;
    private int interfaceOffsetX = 5;
    private int interfaceOffsetY = 5;
    private int numberOfHearts = 10;
    private String mobName = "The Creeper";
    private String[] mobTrivia = {"Will blow up your base if", "left unattended."};

    public CreeperMeta() {
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

    public EntityCreeper getEntity() {
        this.entity = new EntityCreeper(this.world);
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

    public int getSimulationTickCost() { return 80; }
}
