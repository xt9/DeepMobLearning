package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-15.
 */
public class DragonMeta extends MobMetaData {
    static String[] mobTrivia = {"Resides in the end, does not harbor treasure", "Destroy it's crystals, break the cycle."};

    DragonMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityDragon;
    }

    public EntityDragon getEntity(World world) {
        return new EntityDragon(world);
    }
}
