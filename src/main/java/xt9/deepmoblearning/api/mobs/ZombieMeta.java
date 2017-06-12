package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityZombie;

/**
 * Created by xt9 on 2017-06-09.
 */
public class ZombieMeta extends MobMetaData {
    private EntityZombie entity;
    private EntityZombie childEntity;
    private int interfaceScale = 35;
    private int interfaceOffsetX = -2;
    private int interfaceOffsetY = 6;
    private int childInterfaceOffsetX = 21;
    private int childInterfaceOffsetY = 6;
    private int numberOfHearts = 10;
    private String mobName = "The Zombie";
    private String[] mobTrivia = {"This is ONE angry zombie."};

    public ZombieMeta() {
        super();
        this.entity = new EntityZombie(this.world);

        EntityZombie childEntity = new EntityZombie(this.world);
        childEntity.setChild(true);

        this.childEntity = childEntity;
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

    public EntityZombie getEntity() {
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

    public EntityZombie getChildEntity() {
        return this.childEntity;
    }

    public int getChildInterfaceOffsetX() {
        return this.childInterfaceOffsetX;
    }

    public int getChildInterfaceOffsetY() {
        return this.childInterfaceOffsetY;
    }
}
