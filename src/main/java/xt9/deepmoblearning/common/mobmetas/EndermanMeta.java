package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.Item;

/**
 * Created by xt9 on 2017-06-12.
 */
public class EndermanMeta extends MobMetaData {
    static String[] mobTrivia = {"Friendly unless provoked, dislikes rain.", "Teleports short distances"};

    EndermanMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityEnderman getEntity(World world) {
        return new EntityEnderman(world);
    }

}
