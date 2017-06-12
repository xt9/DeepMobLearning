package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-12.
 */
public class WitchMeta extends MobMetaData {
    private EntityWitch entity;
    private int interfaceScale = 34;
    private int interfaceOffsetX = 4;
    private int interfaceOffsetY = 11;
    private int numberOfHearts = 13;
    private String mobName = "The Witch";
    private String[] mobTrivia = {"Affinity with potions and concoctions", "Beware!"};

    public WitchMeta() {
        super();
        this.entity = new EntityWitch(this.world);
        this.entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.POTIONITEM));
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

    public EntityWitch getEntity() {
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
