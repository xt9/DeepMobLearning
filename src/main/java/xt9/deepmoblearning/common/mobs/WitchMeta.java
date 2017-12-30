package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-12.
 */
public class WitchMeta extends MobMetaData {
    private String[] mobTrivia = {"Affinity with potions and concoctions", "Beware!"};

    WitchMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, int matterType) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, matterType);
    }

    public String[] getMobTrivia() {
        return this.mobTrivia;
    }

    public EntityWitch getEntity() {
        EntityWitch entity = new EntityWitch(this.world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.POTIONITEM));
        return entity;
    }
}
