package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-15.
 */
public class WitherSkeletonMeta extends MobMetaData {
    private EntityWitherSkeleton entity;
    private int interfaceScale = 33;
    private int interfaceOffsetX = 5;
    private int interfaceOffsetY = 10;
    private int numberOfHearts = 10;
    private String mobName = "The Wither Skeleton";
    private String[] mobTrivia = {"Inflicts the wither effect", "Bring milk"};

    public WitherSkeletonMeta() {
        super();
        this.entity = new EntityWitherSkeleton(this.world);
        this.entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
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

    public EntityWitherSkeleton getEntity() {
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
