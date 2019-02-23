package xt9.deepmoblearning.common.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.network.messages.UpdatePlayerTrialCapabilityMessage;

/**
 * Created by xt9 on 2018-04-07.
 */
@Mod.EventBusSubscriber
public class PlayerTrial {
    private int currentWave = 0;
    private int lastWave = 0;
    private int mobsDefeated = 0;
    private int waveMobTotal = 0;
    private boolean isActive = false;
    private long tilePos;

    public PlayerTrial() {

    }

    public PlayerTrial(int currentWave, int lastWave, int mobsDefeated, int waveMobTotal, long pos, boolean isActive) {
        this.currentWave = currentWave;
        this.lastWave = lastWave;
        this.mobsDefeated = mobsDefeated;
        this.waveMobTotal = waveMobTotal;
        this.tilePos = pos;
        this.isActive = isActive;
    }


    public NBTTagCompound write(NBTTagCompound compound) {
        compound.setInt("currentWave", getCurrentWave());
        compound.setInt("lastWave", getLastWave());
        compound.setInt("mobsDefeated", getDefated());
        compound.setInt("waveMobTotal", getWaveMobTotal());
        compound.setBoolean("isActive", isTrialActive());
        compound.setLong("tilePos", getTilePos());
        return compound;
    }

    public void read(NBTTagCompound compound) {
        setCurrentWave(compound.getInt("currentWave"));
        setLastWave(compound.getInt("lastWave"));
        setDefeated(compound.getInt("mobsDefeated"));
        setWaveMobTotal(compound.getInt("waveMobTotal"));
        setIsActive(compound.getBoolean("isActive"));
        setTilePos(compound.getLong("tilePos"));
    }

    @SuppressWarnings("ConstantConditions")
    public void sync(EntityPlayerMP player) {
        PlayerTrial cap = DeepMobLearning.proxy.getTrialCapability(player);
        NBTTagCompound compound = cap.write(new NBTTagCompound());
        Network.channel.sendTo(new UpdatePlayerTrialCapabilityMessage(compound), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public void setCurrentWave(int wave) {
        currentWave = wave;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setLastWave(int wave) {
        lastWave = wave;
    }

    public int getLastWave() {
        return lastWave;
    }

    public void setDefeated(int count) {
        mobsDefeated = count;
    }

    public int getDefated() {
        return mobsDefeated;
    }

    public void setWaveMobTotal(int total) {
        waveMobTotal = total;
    }

    public int getWaveMobTotal() {
        return waveMobTotal;
    }

    public void setTilePos(long pos) {
        tilePos = pos;
    }

    public long getTilePos() {
        return tilePos;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isTrialActive() {
        return isActive;
    }

    public void copyFrom(PlayerTrial cap) {
        setDefeated(cap.getDefated());
        setCurrentWave(cap.getCurrentWave());
        setLastWave(cap.getLastWave());
        setWaveMobTotal(cap.getWaveMobTotal());
        setTilePos(cap.getTilePos());
    }
}
