package xt9.deepmoblearning.api.mobs;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2017-06-09.
 */
public class SkeletonMeta extends MobMetaData {
    private EntitySkeleton entity;
    private int interfaceScale = 35;
    private int interfaceOffsetX = 2;
    private int interfaceOffsetY = 6;
    private int numberOfHearts = 10;
    private String mobName = "The Skeleton";
    private String[] mobTrivia = {"A formidable archer.", "A shield could prove useful"};

    public SkeletonMeta() {
        super();
        this.entity = new EntitySkeleton(this.world);
        this.entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
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

    public EntitySkeleton getEntity() {
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
