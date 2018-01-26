package xt9.deepmoblearning.common.mobs;

import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-12.
 */
public class CreeperMeta extends MobMetaData {
    static String[] mobTrivia = {"Will blow up your base if", "left unattended."};

    CreeperMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityCreeper;
    }

    public EntityCreeper getEntity(World world) {
        return new EntityCreeper(world);
    }
}
