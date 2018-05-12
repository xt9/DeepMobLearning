package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.entity.EntityTrialEnderman;

/**
 * Created by xt9 on 2018-04-05.
 */
public class EndermanTrial extends Trial {
    public EndermanTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public double getSpawnDelay() {
        return 2.6;
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        return new EntityTrialEnderman(world);
    }

    @Override
    public NonNullList<ItemStack> getTrialRewards(int tier) {
        NonNullList<ItemStack> rewards = NonNullList.create();
        ItemStack pristine = data.getPristineMatterStack(1);

        switch(tier) {
            case 0: pristine.setCount(2); break;
            case 1: pristine.setCount(3); break;
            case 2: pristine.setCount(4); break;
            case 3: pristine.setCount(7); break;
            case 4: pristine.setCount(12); break;
        }

        rewards.add(pristine);
        return rewards;
    }
}