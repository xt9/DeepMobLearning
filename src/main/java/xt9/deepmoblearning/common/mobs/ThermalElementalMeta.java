package xt9.deepmoblearning.common.mobs;

import cofh.thermalfoundation.entity.monster.EntityBasalz;
import cofh.thermalfoundation.entity.monster.EntityBlitz;
import cofh.thermalfoundation.entity.monster.EntityBlizz;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-12.
 */
public class ThermalElementalMeta extends MobMetaData {
    static String[] mobTrivia = {"Blizzes, Basalz(s) & Blitzes", "Siblings with the Blaze", "Their master really liked words starting with B"};

    ThermalElementalMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityBlitz ||
            entityLiving instanceof EntityBasalz ||
            entityLiving instanceof EntityBlizz;
    }

    public EntityBlizz getEntity(World world) {
        return new EntityBlizz(world);
    }
}
