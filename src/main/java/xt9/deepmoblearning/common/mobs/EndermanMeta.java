package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityEnderman;

/**
 * Created by xt9 on 2017-06-12.
 */
public class EndermanMeta extends MobMetaData {
    private EntityEnderman entity;
    private int interfaceScale = 30;
    private int interfaceOffsetX = 5;
    private int interfaceOffsetY = 11;
    private int numberOfHearts = 20;
    private String mobName = "The Enderman";
    private String[] mobTrivia = {"Friendly unless provoked, dislikes rain.", "Teleports short distances"};

    public EndermanMeta() {
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

    public EntityEnderman getEntity() {
        this.entity = new EntityEnderman(this.world);
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

    public int getSimulationTickCost() { return 512; }
}
