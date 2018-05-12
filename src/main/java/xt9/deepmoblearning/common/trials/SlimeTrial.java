package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.entity.EntityTrialSlime;

/**
 * Created by xt9 on 2018-05-02.
 */
public class SlimeTrial extends Trial {
    public SlimeTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public double getSpawnDelay() {
        return 1.6;
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        return new EntityTrialSlime(world);
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
