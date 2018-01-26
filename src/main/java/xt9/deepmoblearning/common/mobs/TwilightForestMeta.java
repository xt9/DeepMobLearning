package xt9.deepmoblearning.common.mobs;

import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import twilightforest.entity.EntityTFDeathTome;
import twilightforest.entity.EntityTFSwarmSpider;
import twilightforest.entity.boss.EntityTFLich;
import twilightforest.entity.boss.EntityTFLichMinion;
import twilightforest.entity.boss.EntityTFNaga;

/**
 * Created by xt9 on 2018-01-18.
 */
public class TwilightForestMeta extends MobMetaData {
    static String[] mobTrivia = {"Nagas, Liches and flying books", "What the hell have you walked into?"};

    TwilightForestMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityTFNaga ||
            entityLiving instanceof EntityTFLichMinion ||
            entityLiving instanceof EntityTFLich ||
            entityLiving instanceof EntityTFDeathTome ||
            entityLiving instanceof EntityTFSwarmSpider;
    }

    public EntityTFLich getEntity(World world) {
        return new EntityTFLich(world);
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Naga courtyard & Lich tower";
    }
}
