package xt9.deepmoblearning.common.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-07.
 */
@SuppressWarnings("unchecked")
public class PlayerTrialProvider implements ICapabilitySerializable<NBTTagCompound> {
    private PlayerTrial playerTrial = new PlayerTrial();

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == PlayerProperties.PLAYER_TRIAL_CAP) {
            return LazyOptional.of(() -> (T) playerTrial);
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        return getCapability(capability);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        playerTrial.write(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        playerTrial.read(compound);
    }
}
