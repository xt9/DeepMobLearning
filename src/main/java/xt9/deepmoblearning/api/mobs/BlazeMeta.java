package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.world.World;

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
    private String[] mobTrivia = {"Bring buckets.", "No really, bring buckets"};

    public BlazeMeta() {
        super();
        this.entity = new EntityBlaze(this.world);
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
