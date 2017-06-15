package xt9.deepmoblearning.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity {
    private int count;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("count", count);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        count = compound.getInteger("count");
        super.readFromNBT(compound);
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
        markDirty();
    }

    public void decrementCount() {
        count--;
        markDirty();
    }
}
