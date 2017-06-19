package xt9.deepmoblearning.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.SimulationChamberHandler;
import xt9.deepmoblearning.common.items.ItemMobChip;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IGuiTile {
    private SimulationChamberHandler inventory = new SimulationChamberHandler();
    // Attach a DeepEnergyStorage, don't let it extract energy (It's not a generator)
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(1000000, 1024 , 0, 0);

    public boolean isCrafting = false;
    public int energy = 0;
    public int ticks = 0;
    public int craftingCost = 38400;
    public int percentDone = 0;


    @Override
    public void update() {
        ticks++;

        if(!isCrafting) {
            this.isCrafting = canStartSimulation();
        } else {
            // Do these on server only, they get updated from the containers intervalUpdate
            if(!this.world.isRemote) {
                this.energyStorage.voidEnergy(128);

                if(ticks % 3 == 0) {
                    // This process takes 300 ticks, which is 15seconds
                    this.percentDone++;
                }
            }

            if(!canContinueSimulation()) {
                this.finishSimulation(true);
            }

            if(this.percentDone == 100) {
                this.finishSimulation(false);
            }
        }

        if(!this.world.isRemote) {
            // Do the crafting logic only on the server side, visual updates will be recieved since ContainerSimulationChamber polls updates when open
            this.doStaggeredDiskSave(100);
        }

    }

    private void finishSimulation(boolean abort) {
        this.percentDone = 0;
        this.isCrafting = false;
        // Only decrease input and increase output if not aborted, and only if on the server TE
        if(!abort && !this.world.isRemote) {
            ItemMobChip.increaseSimulationCount(this.inventory.getChip());

            Random rand = new Random();
            int num = rand.nextInt(100);
            if(num < ItemMobChip.getSuccessChance(this.inventory.getChip())) {
                ItemStack oldInputStack = this.inventory.getInput();
                ItemStack oldOutPutStack = this.inventory.getOutput();
                this.inventory.setStackInSlot(DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT, new ItemStack(Registry.polymerClay, oldInputStack.getCount() - 1));
                this.inventory.setStackInSlot(DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT, new ItemStack(Registry.livingClay, oldOutPutStack.getCount() + 1));
            }

        }
    }

    private boolean canStartSimulation() {
        return this.hasEnergyForSimulation() && this.canContinueSimulation() && !this.inventory.outputIsFull();
    }

    private boolean canContinueSimulation() {
        return this.inventory.hasChip() && ItemMobChip.getTier(this.inventory.getChip()) != 0
                && this.inventory.hasPolymer();
    }


    public boolean hasEnergyForSimulation() {
        // Todo move this to util classes and check the cost on each chip tier
        return this.energyStorage.getEnergyStored() > this.craftingCost;
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return this.getNetworkTag(super.getUpdateTag());
    }

    private NBTTagCompound getNetworkTag(NBTTagCompound tag) {
        tag.setInteger("simulationProgress", this.percentDone);
        return energyStorage.writeEnergy(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("simulationProgress", this.percentDone);
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        energyStorage.readEnergy(compound);
        this.percentDone = compound.hasKey("simulationProgress", Constants.NBT.TAG_INT) ? compound.getInteger("simulationProgress") : 0;
        super.readFromNBT(compound);
    }

    private void doStaggeredDiskSave(int divisor) {
        if(ticks % divisor == 0) {
            if(this.energy != this.energyStorage.getEnergyStored()) {
                // Save to disk every 5 seconds if energy changed
                this.energy = this.energyStorage.getEnergyStored();
                this.markDirty();
            }
        }
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

    public int getGuiID() {
        return DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID;
    }
}
