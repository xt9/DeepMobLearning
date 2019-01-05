package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-15.
 */
public class GhastMeta extends MobMetaData {
    static String[] mobTrivia = {"If you hear something that sounds like", "a crying llama, you're probably hearing a ghast"};

    GhastMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityGhast getEntity(World world) {
        return new EntityGhast(world);
    }

}
