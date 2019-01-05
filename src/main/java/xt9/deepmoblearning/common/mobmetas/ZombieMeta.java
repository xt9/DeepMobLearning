package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-09.
 */
public class ZombieMeta extends MobMetaData {
    static String[] mobTrivia = {"They go moan in the night.", "Does not understand the need for", "personal space"};

    ZombieMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityZombie getEntity(World world) {
        return new EntityZombie(world);
    }

    public EntityZombie getExtraEntity(World world) {
        EntityZombie childEntity = new EntityZombie(world);
        childEntity.setChild(true);

        return childEntity;
    }

    public int getExtraInterfaceOffsetX() {
        return 21;
    }

    public int getExtraInterfaceOffsetY() {
        return 6;
    }
}
