package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-02.
 */
public class SlimeTrial extends Trial {
    public SlimeTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world, EntityPlayerMP player) {
        return null;
    }

    @Override
    public NonNullList<ItemStack> getTrialRewards(int tier) {
        return null;
    }
}
