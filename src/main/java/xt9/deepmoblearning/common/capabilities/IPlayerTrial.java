package xt9.deepmoblearning.common.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by xt9 on 2018-04-07.
 */
public interface IPlayerTrial {
    void setCurrentWave(int wave);
    int getCurrentWave();

    void setLastWave(int wave);
    int getLastWave();

    void setDefeated(int count);
    int getDefated();

    void setWaveMobTotal(int total);
    int getWaveMobTotal();

    void setTilePos(long pos);
    long getTilePos();

    void setIsActive(boolean b);
    boolean isTrialActive();

    void sync(EntityPlayerMP player);
}
