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

    public void voidEnergy(int energy) {
        this.energy = getEnergyStored() - energy;
    }

    public NBTTagCompound writeEnergy(NBTTagCompound compound) {
        compound.setInteger("energy", getEnergyStored());
        return compound;
    }

    public void readEnergy(NBTTagCompound compound) {
        this.energy = compound.hasKey("energy", Constants.NBT.TAG_INT) ? compound.getInteger("energy") : 0;
    }
}
