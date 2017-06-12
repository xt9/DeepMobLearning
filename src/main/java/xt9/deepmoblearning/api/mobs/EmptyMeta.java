package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;

/**
 * Created by xt9 on 2017-06-12.
 */
public class EmptyMeta extends MobMetaData {
    private int interfaceScale = 0;
    private int interfaceOffsetX = 0;
    private int interfaceOffsetY = 0;
    private int numberOfHearts = 0;
    private String mobName = "";
    private String[] mobTrivia = {""};

    public EmptyMeta() {
        // Resembles a empty meta class, if instanceof is emptymeta, don't try to render meta data.
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

    public Entity getEntity() {
        return new EntityVillager(this.world);
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
