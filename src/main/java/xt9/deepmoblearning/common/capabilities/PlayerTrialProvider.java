package xt9.deepmoblearning.common.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-04-07.
 */
public class PlayerTrialProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    @CapabilityInject(IPlayerTrial.class)
    public static final Capability<IPlayerTrial> PLAYER_TRIAL_CAP = null;


    @SuppressWarnings("ConstantConditions")
    private IPlayerTrial instance = PLAYER_TRIAL_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        //noinspection ConstantConditions
        return capability == PLAYER_TRIAL_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        //noinspection ConstantConditions
        return capability == PLAYER_TRIAL_CAP ? PLAYER_TRIAL_CAP.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        //noinspection ConstantConditions
        return (NBTTagCompound) PLAYER_TRIAL_CAP.getStorage().writeNBT(PLAYER_TRIAL_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        //noinspection ConstantConditions
        PLAYER_TRIAL_CAP.getStorage().readNBT(PLAYER_TRIAL_CAP, instance, null, compound);
    }
}
