package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import twilightforest.entity.boss.EntityTFSnowQueen;

/**
 * Created by xt9 on 2018-01-21.
 */
public class TwilightGlacierMeta extends MobMetaData {
    static String[] mobTrivia = {"Here you'll find caves with ancient beasts", "and Elsas wicked distant cousin Aurora", "(Elsa might \"let it go\", but aurora sure won't)"};

    TwilightGlacierMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityTFSnowQueen getEntity(World world) {
        EntityTFSnowQueen entity = new EntityTFSnowQueen(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.SNOWBALL));
        return entity;
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Yeti lair and Ice Tower";
    }
}
