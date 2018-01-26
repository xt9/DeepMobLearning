package xt9.deepmoblearning.common.mobs;

import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-15.
 */
public class SlimeMeta extends MobMetaData {
    static String[] mobTrivia = {"The bounce", "bounce his bounce", "squish - \"A slime haiku\""};

    SlimeMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntitySlime;
    }

    public EntitySlime getEntity(World world) {
        return new EntitySlime(world);
    }
}
