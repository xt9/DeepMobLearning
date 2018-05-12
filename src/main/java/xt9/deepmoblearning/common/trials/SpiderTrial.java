package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.entity.EntityTrialCaveSpider;
import xt9.deepmoblearning.common.entity.EntityTrialSpider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-02.
 */
public class SpiderTrial extends Trial {
    public SpiderTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        return getRandomSpider(world);
    }

    private EntitySpider getRandomSpider(World world) {
        int rand = ThreadLocalRandom.current().nextInt(1, 100);
        if(rand > 88) {
            return new EntityTrialCaveSpider(world);
        } else {
            return new EntityTrialSpider(world);
        }
    }

    @Override
    public NonNullList<ItemStack> getTrialRewards(int tier) {
        NonNullList<ItemStack> rewards = NonNullList.create();
        ItemStack pristine = data.getPristineMatterStack(1);

        switch(tier) {
            case 0: pristine.setCount(3); break;
            case 1: pristine.setCount(5); break;
            case 2: pristine.setCount(9); break;
            case 3: pristine.setCount(12); break;
            case 4: pristine.setCount(18); break;
        }

        rewards.add(pristine);
        return rewards;
    }
}
