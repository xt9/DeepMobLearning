package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityCreeper;

/**
 * Created by xt9 on 2017-06-12.
 */
public class CreeperMeta extends MobMetaData {
    private EntityCreeper entity;
    private int interfaceScale = 38;
    private int interfaceOffsetX = 5;
    private int interfaceOffsetY = 0;
    private int numberOfHearts = 10;
    private String mobName = "The Creeper";
    private String[] mobTrivia = {"Will blow up your base if", "left unattended."};

    public CreeperMeta() {
        super();
        this.entity = new EntityCreeper(this.world);
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
