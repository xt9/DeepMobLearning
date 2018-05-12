package xt9.deepmoblearning.common.trials;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-15.
 */
public class SkeletonTrial extends Trial {
    public SkeletonTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        EntityMob e = getRandomSkeleton(world);

        // Default equipment
        e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        e.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));

        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 2) {
            ItemStack pewpewBow = new ItemStack(Items.BOW);
            pewpewBow.addEnchantment(Enchantment.getEnchantmentByID(49), 1);
            pewpewBow.addEnchantment(Enchantment.getEnchantmentByID(48), 2);
            e.setHeldItem(EnumHand.MAIN_HAND, pewpewBow);
            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        } else if(rand < 25) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
        } else if(rand < 50) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BONE));
        } else {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
        }
        return e;
    }

    @Override
    public NonNullList<ItemStack> getTrialRewards(int tier) {
        NonNullList<ItemStack> rewards = NonNullList.create();
        ItemStack pristine = data.getPristineMatterStack(1);

        switch(tier) {
            case 0: pristine.setCount(2); break;
            case 1: pristine.setCount(3); break;
            case 2: pristine.setCount(5); break;
            case 3: pristine.setCount(12); break;
            case 4: pristine.setCount(18); break;
        }

        rewards.add(pristine);
        return rewards;
    }

    private EntityMob getRandomSkeleton(World world) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 33) {
            return new EntityStray(world);
        } else {
            return new EntitySkeleton(world);
        }
    }
}
