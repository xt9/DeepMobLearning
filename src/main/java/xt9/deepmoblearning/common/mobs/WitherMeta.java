package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.boss.EntityWither;

/**
 * Created by xt9 on 2017-06-10.
 */
public class WitherMeta extends MobMetaData {
    private EntityWither entity;
    private int interfaceScale = 22;
    private int interfaceOffsetX = 3;
    private int interfaceOffsetY = 18;
    private int numberOfHearts = 150;
    private String mobName = "The Wither";
    private String[] mobTrivia = {"Do not approach this enemy. Run!", "I mean it has 3 heads, what could", "possibly go wrong?"};

    public WitherMeta() {
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

    public EntityWither getEntity() {
        this.entity = new EntityWither(this.world);
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

    public int getSimulationTickCost() { return 2048; }
}
