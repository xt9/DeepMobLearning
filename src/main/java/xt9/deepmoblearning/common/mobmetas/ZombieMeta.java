package xt9.deepmoblearning.common.mobs;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
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

    @Override
    public EntityZombie getCombatEntity(World world) {
        EntityZombie entity = new EntityZombie(world);

        entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        entity.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        entity.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        entity.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));

        entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));

        return entity;
    }

    public EntityZombie getEntity(World world) {
        return new EntityZombie(world);
    }

    public EntityZombie getExtraEntity(World world) {
        EntityZombie childEntity = new EntityZombie(world);
        childEntity.setChild(true);

        return childEntity;
    }

    @Override
    public boolean entityLivingMatchesMob(EntityLivingBase entityLiving) {
        return entityLiving instanceof EntityZombie;
    }

    public int getExtraInterfaceOffsetX() {
        return 21;
    }

    public int getExtraInterfaceOffsetY() {
        return 6;
    }
}
