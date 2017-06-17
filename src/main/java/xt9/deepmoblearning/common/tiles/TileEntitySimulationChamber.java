package xt9.deepmoblearning.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IGuiTile {
    private ItemStackHandler inventory = new ItemStackHandler(DeepConstants.SIMULATION_CHAMBER_INTERNAL_SLOTS);
    // Attach a DeepEnergyStorage, don't let it extract energy (It's not a generator)
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(100000, 1024 , 0, 0);
    private int chipSlot = DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT;
    private int inputSlot = DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT;
    private int outputSlot = DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT;
    private int energy = 0;
    private int ticks;


    @Override
    public void update() {
        ticks++;

        if(!this.world.isRemote) {
            // Check obj every 2 seconds
            if(ticks % 40 == 0) {
                if(this.energy != this.energyStorage.getEnergyStored()) {
                    this.markDirty();
                }
            }
        }

    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        energyStorage.readEnergy(compound);
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
                capability == CapabilityEnergy.ENERGY ||
                super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        } else if(capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public int getTicks() {
        return this.ticks;
    }
    public int getGuiID() {
        return DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID;
    }
}
