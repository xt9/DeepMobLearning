package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.item.Item;
import twilightforest.entity.boss.EntityTFLich;

/**
 * Created by xt9 on 2018-01-18.
 */
public class TwilightForestMeta extends MobMetaData {
    static String[] mobTrivia = {"Nagas, Liches and flying books", "What the hell have you walked into?"};

    TwilightForestMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityTFLich getEntity(World world) {
        return new EntityTFLich(world);
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Naga courtyard & Lich tower";
    }
}
