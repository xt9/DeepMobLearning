package xt9.deepmoblearning.common.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.network.UpdatePlayerTrialCapabilityMessage;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-07.
 */
@Mod.EventBusSubscriber
public class PlayerTrial implements IPlayerTrial, Capability.IStorage<IPlayerTrial> {
    private int currentWave = 0;
    private int lastWave = 0;
    private int mobsDefeated = 0;
    private int waveMobTotal = 0;
    private boolean isActive = false;
    private long tilePos;

    public static void init() {
        // Enable field injection for capabilities
        CapabilityManager.INSTANCE.register(IPlayerTrial.class, new PlayerTrial(), PlayerTrial::new);
    }

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


    @Nullable
    @Override
    public NBTTagCompound writeNBT(Capability<IPlayerTrial> capability, IPlayerTrial instance, EnumFacing enumFacing) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("currentWave", instance.getCurrentWave());
        compound.setInteger("lastWave", instance.getLastWave());
        compound.setInteger("mobsDefeated", instance.getDefated());
        compound.setInteger("waveMobTotal", instance.getWaveMobTotal());
        compound.setBoolean("isActive", instance.isTrialActive());
        compound.setLong("tilePos", instance.getTilePos());
        return compound;
    }

    @Override
    public void readNBT(Capability<IPlayerTrial> capability, IPlayerTrial instance, EnumFacing enumFacing, NBTBase nbt) {
        instance.setCurrentWave(((NBTTagCompound) nbt).getInteger("currentWave"));
        instance.setLastWave(((NBTTagCompound) nbt).getInteger("lastWave"));
        instance.setDefeated(((NBTTagCompound) nbt).getInteger("mobsDefeated"));
        instance.setWaveMobTotal(((NBTTagCompound) nbt).getInteger("waveMobTotal"));
        instance.setIsActive(((NBTTagCompound) nbt).getBoolean("isActive"));
        instance.setTilePos(((NBTTagCompound) nbt).getLong("tilePos"));
    }


    @Override
    @SuppressWarnings("ConstantConditions")
    public void sync(EntityPlayerMP player) {
        DeepMobLearning.network.sendTo(new UpdatePlayerTrialCapabilityMessage((PlayerTrial)player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null)), player);
    }

    @Override
    public void setCurrentWave(int wave) {
        currentWave = wave;
    }

    @Override
    public int getCurrentWave() {
        return currentWave;
    }

    @Override
    public void setLastWave(int wave) {
        lastWave = wave;
    }

    @Override
    public int getLastWave() {
        return lastWave;
    }

    @Override
    public void setDefeated(int count) {
        mobsDefeated = count;
    }

    @Override
    public int getDefated() {
        return mobsDefeated;
    }

    @Override
    public void setWaveMobTotal(int total) {
        waveMobTotal = total;
    }

    @Override
    public int getWaveMobTotal() {
        return waveMobTotal;
    }

    @Override
    public void setTilePos(long pos) {
        tilePos = pos;
    }

    @Override
    public long getTilePos() {
        return tilePos;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean isTrialActive() {
        return isActive;
    }
}
