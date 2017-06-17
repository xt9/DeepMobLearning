package xt9.deepmoblearning.common.energy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.EnergyStorage;

/**
 * Created by xt9 on 2017-06-16.
 */
public class DeepEnergyStorage extends EnergyStorage {
    public DeepEnergyStorage(int capacity, int maxIn, int maxOut, int energy) {
        super(capacity, maxIn, maxOut, energy);
    }

    public void writeEnergy(NBTTagCompound compound) {
        compound.setInteger("energy", this.getEnergyStored());
    }

    public void readEnergy(NBTTagCompound compound) {
        this.energy = compound.hasKey("energy", Constants.NBT.TAG_INT) ? compound.getInteger("energy") : 0;
    }
}
