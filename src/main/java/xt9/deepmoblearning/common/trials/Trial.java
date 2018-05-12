package xt9.deepmoblearning.common.trials;


import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.MobMetaFactory;

/**
 * Created by xt9 on 2018-03-27.
 */
public abstract class Trial {
    protected String mobKey;
    protected MobMetaData data;
    protected int[] mobsPerWave;

    public Trial(String mobKey, int[] mobsPerWave) {
        this.mobKey = mobKey;
        this.data = MobMetaFactory.createMobMetaData(mobKey);
        this.mobsPerWave = mobsPerWave;
    }

    public String getMobKey() {
        return mobKey;
    }

    public int getMobCountForWave(int wave) {
        if(wave > mobsPerWave.length - 1) {
            return 0;
        } else {
            return mobsPerWave[wave];
        }
    }

    public MobMetaData getData() {
        return data;
    }

    public abstract EntityLiving getTrialPrimaryEntity(World world);
    public abstract NonNullList<ItemStack> getTrialRewards(int tier);

    public double getSpawnDelay() {
        return 2;
    }
}
