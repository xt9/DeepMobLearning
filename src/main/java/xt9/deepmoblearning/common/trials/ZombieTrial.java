package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-03-27.
 */
public class ZombieTrial extends Trial {

    public ZombieTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        EntityMob e = getRandomZombie(world);

        int rand = ThreadLocalRandom.current().nextInt(0, 3);
        for (int i = 0; i < rand; i++) {
            setRandomArmorPiece(e);
        }

        if(!(e instanceof EntityHusk)) {
            // Husks don't burn in the sun, and without weapons they apply hunger
            setRandomHeadArmor(e);
            setRandomWeapon(e);
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

    private EntityMob getRandomZombie(World world) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);
        if(rand < 25) {
            return new EntityZombieVillager(world);
        } else if(rand < 55) {
            return new EntityHusk(world);
        } else if(rand < 95) {
            return new EntityZombie(world);
        } else {
            EntityZombie e = new EntityZombie(world);
            e.setChild(true);
            return e;
        }
    }

    private void setRandomWeapon(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 2) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.DIAMOND_AXE));
        } else if(rand < 12) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
        } else if(rand < 33) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_AXE));
        } else if(rand < 67) {
            e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SHOVEL));
        }
    }

    private void setRandomArmorPiece(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(0, 2);

        switch (rand) {
            case 0: setRandomChestArmor(e); break;
            case 1: setRandomLegArmor(e); break;
            case 2: setRandomFeetArmor(e); break;
        }
    }

    private void setRandomHeadArmor(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 2) {
            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        } else if(rand < 12) {
            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        } else if(rand < 42) {
            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        } else {
            e.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        }
    }

    private void setRandomChestArmor(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 1) {
            e.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
        } else if(rand < 10) {
            e.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        } else if(rand < 30) {
            e.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
        }
    }

    private void setRandomLegArmor(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 2) {
            e.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        } else if(rand < 12) {
            e.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        } else if(rand < 22) {
            e.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        } else if(rand < 36) {
            e.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
        }
    }

    private void setRandomFeetArmor(EntityMob e) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if(rand < 3) {
            e.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
        } else if(rand < 16) {
            e.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
        } else if(rand < 53) {
            e.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));
        }
    }

}
