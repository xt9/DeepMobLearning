package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by xt9 on 2017-06-12.
 */
public class WitchMeta extends MobMetaData {
    static String[] mobTrivia = {"Affinity with potions and concoctions", "Beware!"};

    WitchMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public EntityZombie getCombatEntity(World world) {
        EntityZombie entity = new EntityZombie(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
        return entity;
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityWitch;
    }

    public EntityWitch getEntity(World world) {
        EntityWitch entity = new EntityWitch(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.POTIONITEM));
        return entity;
    }
}
