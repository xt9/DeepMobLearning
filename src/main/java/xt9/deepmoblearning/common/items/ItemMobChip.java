package xt9.deepmoblearning.common.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.common.util.NBTHelper;

import java.util.List;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ItemMobChip extends ItemBase {
    public ItemMobChip() {
        super("mob_chip", 1, "default", "zombie", "skeleton", "blaze", "enderman", "wither", "witch", "spider", "creeper");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv)
    {
        if(stack.getItemDamage()!=0) {
            list.add("Num killed: " + NBTHelper.getInt(stack, "mobsKilled", 0));
        }
    }

    public static void killedMob(ItemStack stack) {
        int mobsKilled = NBTHelper.getInt(stack, "mobsKilled", 0);
        mobsKilled = mobsKilled + 1;
        NBTHelper.setInt(stack, "mobsKilled", mobsKilled);
    }

    public static String getSubName(ItemStack stack) {
        Item item = stack.getItem();
        return ((ItemMobChip) item).getSubNames()[stack.getItemDamage()];
    }

    public static NonNullList<ItemStack> getValidFromList(NonNullList<ItemStack> list) {
        NonNullList<ItemStack> filteredList = NonNullList.create();

        for(ItemStack stack : list) {
            Item item = stack.getItem();

            if(item instanceof ItemMobChip) {
                String subName = ((ItemMobChip) item).getSubNames()[stack.getItemDamage()];
                if(!subName.equals("default")) {
                    filteredList.add(stack);
                }
            }
        }

        return filteredList;
    }

    public static boolean entityLivingMatchesType(EntityLivingBase entityLiving, ItemStack stack) {
        String subName = getSubName(stack);

        return (entityLiving instanceof EntityZombie && subName.equals("zombie")) ||
                (entityLiving instanceof EntitySkeleton && subName.equals("skeleton")) ||
                (entityLiving instanceof EntityBlaze && subName.equals("blaze")) ||
                (entityLiving instanceof EntityEnderman && subName.equals("enderman")) ||
                (entityLiving instanceof EntityWither && subName.equals("wither")) ||
                (entityLiving instanceof EntityWitch && subName.equals("witch")) ||
                (entityLiving instanceof  EntitySpider && subName.equals("spider")) ||
                (entityLiving instanceof EntityCreeper && subName.equals("creeper"));
    }
}
