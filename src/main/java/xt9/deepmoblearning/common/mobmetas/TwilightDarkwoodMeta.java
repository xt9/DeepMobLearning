package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.item.Item;
import twilightforest.entity.boss.EntityTFUrGhast;

/**
 * Created by xt9 on 2018-01-21.
 */
public class TwilightDarkwoodMeta extends MobMetaData {
    static String[] mobTrivia = {"Spooky scary strongholds send shivers down", "your spine, the Ur-ghast will shock your", "soul and seal your doom tonight"};

    TwilightDarkwoodMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityTFUrGhast getEntity(World world) {
        return new EntityTFUrGhast(world);
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Goblin Knight Stronghold and Dark tower";
    }
}
