package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import twilightforest.entity.boss.EntityTFMinoshroom;


/**
 * Created by xt9 on 2018-01-21.
 */
public class TwilightSwampMeta extends MobMetaData {
    static String[] mobTrivia = {"This realm sure could use building regulations", "How are you even allowed to build a huge maze", "in your basement!?"};

    TwilightSwampMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    public EntityTFMinoshroom getEntity(World world) {
        EntityTFMinoshroom entity = new EntityTFMinoshroom(world);
        Item diamondAxe = Item.getByNameOrId("twilightforest:minotaur_axe") != null ? Item.getByNameOrId("twilightforest:minotaur_axe") : Items.DIAMOND_AXE;
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(diamondAxe));
        return entity;
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Swamp Labyrinth or the Hydra lair.";
    }
}
