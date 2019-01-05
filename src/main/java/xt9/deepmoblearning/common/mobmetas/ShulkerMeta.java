package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-05.
 */
public class ShulkerMeta extends MobMetaData {
    static String[] mobTrivia = {"Found in End cities", "Sneaky little buggers"};

    ShulkerMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityShulker getEntity(World world) {
        return new EntityShulker(world);
    }

}
