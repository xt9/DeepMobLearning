package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.items.ItemLivingMatter;
import xt9.deepmoblearning.common.items.ItemPristineMatter;

/**
 * Created by xt9 on 2018-05-05.
 */
public class ShulkerMeta extends MobMetaData {
    static String[] mobTrivia = {"Found in End cities", "Sneaky little buggers"};

    ShulkerMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityShulker;
    }

    public EntityShulker getEntity(World world) {
        return new EntityShulker(world);
    }

}
