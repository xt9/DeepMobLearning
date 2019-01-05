package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.item.Item;
import net.minecraft.world.World;
import slimeknights.tconstruct.world.entity.EntityBlueSlime;

/**
 * Created by xt9 on 2018-03-21.
 */
public class TinkerSlimeMeta extends MobMetaData {
    static String[] mobTrivia = {"The elusive blue slime. Seemingly a", "part of some sort of power hierarchy", "since there's a bunch of \"King slimes\" around."};

    TinkerSlimeMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityBlueSlime getEntity(World world) {
        return new EntityBlueSlime(world);
    }
}
