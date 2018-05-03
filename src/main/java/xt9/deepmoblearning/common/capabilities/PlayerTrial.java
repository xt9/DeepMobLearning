package xt9.deepmoblearning.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-07.
 */
public class PlayerTrialHandler implements IPlayerTrial, Capability.IStorage<PlayerTrialHandler> {
    private int currentWave;
    private int lastWave;
    private int mobsDefeated;
    private int waveMobTotal;

    public static Capability.IStorage<PlayerTrialHandler> handler = new Capability.IStorage<PlayerTrialHandler>() {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<PlayerTrialHandler> capability, PlayerTrialHandler instance, EnumFacing enumFacing) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("currentWave", instance.getCurrentWave());
            compound.setInteger("lastWave", instance.getLastWave());
            compound.setInteger("mobsDefeated", instance.getDefated());
            compound.setInteger("waveMobTotal", instance.getWaveMobTotal());
            return compound;
        }

        @Override
        public void readNBT(Capability<PlayerTrialHandler> capability, PlayerTrialHandler instance, EnumFacing enumFacing, NBTBase nbt) {
            instance.setCurrentWave(((NBTTagCompound) nbt).getInteger("currentWave"));
            instance.setLastWave(((NBTTagCompound) nbt).getInteger("lastWave"));
            instance.setDefeated(((NBTTagCompound) nbt).getInteger("mobsDefeated"));
            instance.setWaveMobTotal(((NBTTagCompound) nbt).getInteger("waveMobTotal"));
        }
    };

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<PlayerTrialHandler> capability, PlayerTrialHandler instance, EnumFacing enumFacing) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("currentWave", instance.getCurrentWave());
        compound.setInteger("lastWave", instance.getLastWave());
        compound.setInteger("mobsDefeated", instance.getDefated());
        compound.setInteger("waveMobTotal", instance.getWaveMobTotal());
        return compound;
    }

    @Override
    public void readNBT(Capability<PlayerTrialHandler> capability, PlayerTrialHandler instance, EnumFacing enumFacing, NBTBase nbt) {
        instance.setCurrentWave(((NBTTagCompound) nbt).getInteger("currentWave"));
        instance.setLastWave(((NBTTagCompound) nbt).getInteger("lastWave"));
        instance.setDefeated(((NBTTagCompound) nbt).getInteger("mobsDefeated"));
        instance.setWaveMobTotal(((NBTTagCompound) nbt).getInteger("waveMobTotal"));
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
}
