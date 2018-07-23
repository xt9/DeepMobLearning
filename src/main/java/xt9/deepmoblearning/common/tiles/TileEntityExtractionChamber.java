package xt9.deepmoblearning.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.BaseItemHandler;
import xt9.deepmoblearning.common.handlers.OutputHandler;
import xt9.deepmoblearning.common.handlers.PristineHandler;
import xt9.deepmoblearning.common.items.ItemPristineMatter;
import xt9.deepmoblearning.common.util.MathHelper;
import xt9.deepmoblearning.common.util.Pagination;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntityExtractionChamber extends TileEntity implements ITickable, IGuiTile {
    private BaseItemHandler pristine = new PristineHandler();
    private BaseItemHandler output = new OutputHandler(16);
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(1000000, 25600 , 0, 0);

    public boolean isCrafting = false;
    public int energy = 0;
    public int ticks = 0;
    public int percentDone = 0;
    public Pagination pageHandler = new Pagination(0, getLootFromPristine().size(), 9);
    private String currentPristineMatter = "";
    public ItemStack resultingItem = ItemStack.EMPTY;
    public int energyCost = MathHelper.ensureRange(Config.rfCostExtractionChamber.getInt(), 1, 18000);

    @Override
    public void update() {
        ticks++;

        if(!world.isRemote) {
            if(pristineChanged()) {
                finishCraft(true);
                updatePageHandler(0);

                currentPristineMatter = ((ItemPristineMatter) getPristine().getItem()).getMobKey();
                resultingItem = ItemStack.EMPTY;
                updateState();
                return;
            }

            if (!isCrafting) {
                if (canStartCraft()) {
                    isCrafting = true;
                }
            } else {
                if (!canContinueCraft()) {
                    finishCraft(true);
                    return;
                }

                if(hasEnergyForNextTick()) {
                    energyStorage.voidEnergy(energyCost);
                    percentDone++;
                }

                // Notify while crafting every 5sec, this is done more frequently when the container is open
                if (ticks % (DeepConstants.TICKS_TO_SECOND * 5) == 0) {
                    updateState();
                }

                if (percentDone == 50) {
                    finishCraft(false);
                }
            }

            // Save to disk every 5 seconds if energy changed
            doStaggeredDiskSave(100);
        }
    }

    public void finishCraft(boolean abort) {
        isCrafting = false;
        percentDone = 0;
        if(!abort) {
            ItemStack remainder = output.setInFirstAvailableSlot(resultingItem);
            while (!remainder.isEmpty()) {
                remainder = output.setInFirstAvailableSlot(remainder);
            }

            getPristine().shrink(1);
        }
        markDirty();
        updateState();
    }


    public void updatePageHandler(int currentPage) {
        pageHandler.update(currentPage, getLootFromPristine().size());
    }

    public void updateState() {
        IBlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    private boolean canStartCraft() {
        return canContinueCraft() && canInsertItem();
    }

    private boolean canContinueCraft() {
        return !resultingItem.isEmpty() && getPristine().getItem() instanceof ItemPristineMatter;
    }

    public boolean pristineChanged() {
        return !getPristine().isEmpty() && !currentPristineMatter.equals(((ItemPristineMatter) getPristine().getItem()).getMobKey());
    }


    private boolean hasEnergyForNextTick() {
        return energyStorage.getEnergyStored() >= energyCost;
    }


    private boolean canInsertItem() {
        return output.canInsertItem(resultingItem);
    }


    public ItemStack getItemFromList(int index) {
        if(index >= 0 && index < getLootFromPristine().size()) {
            return getLootFromPristine().get(index);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public NonNullList<ItemStack> getLootFromPristine() {
        ItemStack stack = pristine.getStackInSlot(0);

        if(stack.getItem() instanceof ItemPristineMatter) {
            return ((ItemPristineMatter) stack.getItem()).getLootTable();
        } else {
           return NonNullList.create();
        }
    }

    public ItemStack getPristine() {
        return pristine.getStackInSlot(0);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 3, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("pristine", pristine.serializeNBT());
        compound.setTag("output", output.serializeNBT());
        compound.setTag("pageHandler", pageHandler.serializeNBT());
        compound.setTag("resultingItem", resultingItem.serializeNBT());
        compound.setBoolean("isCrafting", isCrafting);
        compound.setInteger("crafingProgress", percentDone);
        compound.setString("currentPristine", currentPristineMatter);
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        pristine.deserializeNBT(compound.getCompoundTag("pristine"));
        output.deserializeNBT(compound.getCompoundTag("output"));
        pageHandler.deserializeNBT(compound.getCompoundTag("pageHandler"));
        resultingItem = new ItemStack(compound.getCompoundTag("resultingItem"));
        isCrafting = compound.hasKey("isCrafting", Constants.NBT.TAG_BYTE) ? compound.getBoolean("isCrafting") : isCrafting;
        percentDone = compound.hasKey("crafingProgress", Constants.NBT.TAG_INT) ? compound.getInteger("crafingProgress") : 0;
        currentPristineMatter = compound.hasKey("currentPristine", Constants.NBT.TAG_STRING) ? compound.getString("currentPristine") : "";
        energyStorage.readEnergy(compound);
        super.readFromNBT(compound);
    }

    private void doStaggeredDiskSave(int divisor) {
        if(ticks % divisor == 0) {
            if(energy != energyStorage.getEnergyStored()) {
                this.energy = energyStorage.getEnergyStored();
                markDirty();
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
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            // If facing is null its interacting with a player or some fake player
            if(facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new CombinedInvWrapper(pristine, output));
            } else if(facing == EnumFacing.UP) {
                // Input
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(pristine);
            } else {
                // Output
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(output);
            }
        } else if(capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }

        return super.getCapability(capability, facing);
    }

    public int getGuiID() {
        return DeepConstants.TILE_EXTRACTION_CHAMBER_GUI_ID;
    }
}
