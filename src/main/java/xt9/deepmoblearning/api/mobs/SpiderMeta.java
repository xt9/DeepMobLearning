package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;

/**
 * Created by xt9 on 2017-06-12.
 */
public class SpiderMeta extends MobMetaData {
    private EntitySpider entity;
    private EntityCaveSpider caveSpiderEntity;
    private int interfaceScale = 30;
    private int interfaceOffsetX = 5;
    private int interfaceOffsetY = 0;
    private int caveSpiderOffsetX = 5;
    private int caveSpiderOffsetY = -25;
    private int numberOfHearts = 8;
    private String mobName = "The Spider";
    private String[] mobTrivia = {"Nocturnal, beware", "Drops cobwebs for some reason.."};

    public SpiderMeta() {
        super();
        this.entity = new EntitySpider(this.world);
        this.caveSpiderEntity = new EntityCaveSpider(this.world);
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

    public EntitySpider getEntity() {
        return this.entity;
    }

    public EntitySpider getExtraEntity() {
        return this.caveSpiderEntity;
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

    public int getExtraInterfaceOffsetX() {
        return this.caveSpiderOffsetX;
    }

    public int getExtraInterfaceOffsetY() {
        return this.caveSpiderOffsetY;
    }
}
