package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-15.
 */
public class IllagerTrial extends Trial {
    public IllagerTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        EntityMob e = getRandomIllager(world);

        if(e instanceof EntityVindicator) {
            ItemStack ironAxe = new ItemStack(Items.IRON_AXE);
            e.setHeldItem(EnumHand.MAIN_HAND, ironAxe);
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
            case 3: pristine.setCount(7); break;
            case 4: pristine.setCount(10); break;
        }

        rewards.add(pristine);
        return rewards;
    }

    private EntityMob getRandomIllager(World world) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);

        if (rand < 18) {
            return new EntityEvoker(world);
        } else {
            return new EntityVindicator(world);
        }
    }
}
