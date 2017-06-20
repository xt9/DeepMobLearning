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
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.SimulationChamberHandler;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.util.Animation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IGuiTile {
    private SimulationChamberHandler inventory = new SimulationChamberHandler();
    // Attach a DeepEnergyStorage, don't let it extract energy (It's not a generator)
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(1000000, 1024 , 0, 0);

    public HashMap<String, Animation> simulationAnimations = new HashMap<>();
    public HashMap<String, String> simulationText = new HashMap<>();
    public boolean isCrafting = false;
    public int energy = 0;
    public int ticks = 0;
    public int craftingCost = 38400;
    public int percentDone = 0;

    @Override
    public void update() {
        ticks++;

        if(!isCrafting) {
            this.resetAnimations();
            if(canStartSimulation()) {
                this.isCrafting = true;
            }
        } else {
            if(!canContinueSimulation()) {
                this.finishSimulation(true);
                return;
            }

            this.updateSimulationText(this.inventory.getChip());

            // Do these on server only
            if(!this.world.isRemote) {
                // Todo get this value from a energy crafting helper
                this.energyStorage.voidEnergy(128);

                if(ticks % 3 == 0) {
                    // This process takes 300 ticks, which is 15seconds
                    this.percentDone++;
                }

                // Notify while crafting every other second, this is done more frequently when the container is open
                if(ticks % 40 ==  0) {
                    IBlockState state = this.world.getBlockState(this.getPos());
                    this.world.notifyBlockUpdate(this.getPos(), state, state, 3);
                }
            }

            if(this.percentDone == 100) {
                this.finishSimulation(false);
                return;
            }
        }

        if(!this.world.isRemote) {
            this.doStaggeredDiskSave(100);
        }

    }


    private void finishSimulation(boolean abort) {
        this.resetAnimations();
        this.percentDone = 0;
        this.isCrafting = false;
        // Only decrease input and increase output if not aborted, and only if on the server's TE
        if(!abort && !this.world.isRemote) {
            IBlockState state = this.world.getBlockState(this.getPos());
            this.world.notifyBlockUpdate(this.getPos(), state, state, 3);

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

    private void updateSimulationText(ItemStack chip) {
        String[] lines = new String[] {
                "Launching runtime v1.4",
                "Iteration #" + (ItemMobChip.getTotalSimulationCount(chip) + 1) + " started",
                "Loading model from chip memory",
                "Assessing threat level",
                "Engaged enemy",
                "Processing results",
                "..."
        };

        Animation a1 = this.getAnimation("simulationProgress1");
        Animation a2 = this.getAnimation("simulationProgress2");
        Animation a3 = this.getAnimation("simulationProgress3");
        Animation a4 = this.getAnimation("simulationProgress4");
        Animation a5 = this.getAnimation("simulationProgress5");
        Animation a6 = this.getAnimation("simulationProgress6");
        Animation a7 = this.getAnimation("blinkingDots1");

        this.simulationText.put("simulationProgress1", this.animate(lines[0], a1, null, 2, false));
        this.simulationText.put("simulationProgress2", this.animate(lines[1], a2, a1, 2, false));
        this.simulationText.put("simulationProgress3", this.animate(lines[2], a3, a2, 2, false));
        this.simulationText.put("simulationProgress4", this.animate(lines[3], a4, a3, 2, false));
        this.simulationText.put("simulationProgress5", this.animate(lines[4], a5, a4, 2, false));
        this.simulationText.put("simulationProgress6", this.animate(lines[5], a6, a5, 2, false));
        this.simulationText.put("blinkingDots1", this.animate(lines[6], a7, a6, 8, true));
    }

    public void resetAnimations() {
        this.simulationAnimations = new HashMap<>();
        this.simulationText = new HashMap<>();
    }

    private String animate(String string, Animation anim, @Nullable Animation precedingAnim, int delayInTicks, boolean loop) {
        if(precedingAnim != null) {
            if (precedingAnim.hasFinished()) {
                return anim.animate(string, delayInTicks, loop);
            } else {
                return "";
            }
        }
        return  anim.animate(string, delayInTicks, loop);
    }

    private Animation getAnimation(String key) {
        if(this.simulationAnimations.containsKey(key)) {
            return this.simulationAnimations.get(key);
        } else {
            this.simulationAnimations.put(key, new Animation());
            return this.simulationAnimations.get(key);
        }
    }

    public String getSimulationText(String key) {
        if(this.simulationText.containsKey(key)) {
            return this.simulationText.get(key);
        } else {
            this.simulationText.put(key, "");
            return this.simulationText.get(key);
        }
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return this.getNetworkTag(super.getUpdateTag());
    }

    private NBTTagCompound getNetworkTag(NBTTagCompound tag) {
        tag.setInteger("simulationProgress", this.percentDone);
        tag.setBoolean("isCrafting", this.isCrafting);
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
        compound.setBoolean("isCrafting", this.isCrafting);
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        energyStorage.readEnergy(compound);
        this.percentDone = compound.hasKey("simulationProgress", Constants.NBT.TAG_INT) ? compound.getInteger("simulationProgress") : 0;
        this.isCrafting = compound.hasKey("isCrafting", Constants.NBT.TAG_BYTE) ? compound.getBoolean("isCrafting") : this.isCrafting;
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
