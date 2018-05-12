package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-15.
 */
public class WitchTrial extends Trial {
    public WitchTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        return new EntityWitch(world);
    }

    @Override
    public NonNullList<ItemStack> getTrialRewards(int tier) {
        NonNullList<ItemStack> rewards = NonNullList.create();
        ItemStack pristine = data.getPristineMatterStack(1);

        switch(tier) {
            case 0: pristine.setCount(2); break;
            case 1: pristine.setCount(3); break;
            case 2: pristine.setCount(7); break;
            case 3: pristine.setCount(10); break;
            case 4: pristine.setCount(16); break;
        }

        rewards.add(pristine);
        return rewards;
    }
}
