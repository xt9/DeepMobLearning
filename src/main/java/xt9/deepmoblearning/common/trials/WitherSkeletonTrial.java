package xt9.deepmoblearning.common.trials;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-03.
 */
public class WitherSkeletonTrial extends Trial {
    public WitherSkeletonTrial(String mobKey, int[] mobsPerWave) {
        super(mobKey, mobsPerWave);
    }

    @Override
    public double getSpawnDelay() {
        return 3.2;
    }

    @Override
    public EntityLiving getTrialPrimaryEntity(World world) {
        EntityWitherSkeleton e = new EntityWitherSkeleton(world);
        e.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
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
}
