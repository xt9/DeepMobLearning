package xt9.deepmoblearning.common.mobs;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import twilightforest.entity.*;
import twilightforest.entity.boss.EntityTFKnightPhantom;
import twilightforest.entity.boss.EntityTFUrGhast;

/**
 * Created by xt9 on 2018-01-21.
 */
public class TwilightDarkwoodMeta extends MobMetaData {
    static String[] mobTrivia = {"Spooky scary strongholds send shivers down", "your spine, the Ur-ghast will shock your", "soul and seal your doom tonight"};

    TwilightDarkwoodMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public EntityZombie getCombatEntity(World world) {
        EntityZombie entity = new EntityZombie(world);
        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
        return entity;
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityTFRedcap ||
            entityLiving instanceof EntityTFBlockGoblin ||
            entityLiving instanceof EntityTFKobold ||
            entityLiving instanceof EntityTFGoblinKnightLower ||
            entityLiving instanceof EntityTFGoblinKnightUpper ||
            entityLiving instanceof EntityTFHelmetCrab ||
            entityLiving instanceof EntityTFKnightPhantom ||
            entityLiving instanceof EntityTFTowerGhast ||
            entityLiving instanceof EntityTFTowerBroodling ||
            entityLiving instanceof EntityTFTowerGolem ||
            entityLiving instanceof EntityTFTowerTermite;
    }

    public EntityTFUrGhast getEntity(World world) {
        return new EntityTFUrGhast(world);
    }

    @Override
    public String getExtraTooltip() {
        return "Gain data by defeating non-vanilla mobs in the Goblin Knight Stronghold and Dark tower";
    }
}
