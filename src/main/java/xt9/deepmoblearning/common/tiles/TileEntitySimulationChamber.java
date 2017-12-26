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
import java.util.Random;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IGuiTile {
    private SimulationChamberHandler inventory = new SimulationChamberHandler();
    // Attach a DeepEnergyStorage, don't let it extract energy (It's not a generator)
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(2000000, 25600 , 0, 0);

    public HashMap<String, Animation> simulationAnimations = new HashMap<>();
    public HashMap<String, String> simulationText = new HashMap<>();
    public boolean isCrafting = false;
    public boolean byproductSuccess = false;
    public int energy = 0;
    public int ticks = 0;
    public int ticksPerSimulation= 300;
    public int percentDone = 0;
    public String currentChipType = "";

    @Override
    public void update() {
        ticks++;

        if(!isCrafting) {
            this.resetAnimations();
            if(canStartSimulation()) {
                this.isCrafting = true;
                this.currentChipType = ItemMobChip.toHumdanReadable(this.inventory.getChip());
            }
        } else {
            if(!canContinueSimulation() || chipTypeChanged()) {
                this.finishSimulation(true);
                return;
            }

            this.updateSimulationText(this.inventory.getChip());

            // Do these on server only
            if(!this.world.isRemote) {
                if(this.percentDone == 0) {
                    Random rand = new Random();
                    int num = rand.nextInt(100);
                    this.byproductSuccess = num <= ItemMobChip.getPristineChance(this.inventory.getChip());
                }

                int rfTickCost = ItemMobChip.getMobMetaData(this.inventory.getChip()).getSimulationTickCost();
                this.energyStorage.voidEnergy(rfTickCost);

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

    private boolean chipTypeChanged() {
        return !this.currentChipType.equals(ItemMobChip.toHumdanReadable(this.inventory.getChip()));
    }


    private void finishSimulation(boolean abort) {
        this.resetAnimations();
        this.percentDone = 0;
        this.isCrafting = false;
        // Only decrease input and increase output if not aborted, and only if on the server's TE
        if(!abort && !this.world.isRemote) {
            ItemMobChip.increaseSimulationCount(this.inventory.getChip());

            ItemStack oldInput = this.inventory.getInput();
            ItemStack oldOutput = this.inventory.getOutput();

            ItemStack newInput = new ItemStack(Registry.polymerClay, oldInput.getCount() - 1);
            ItemStack newOutput = this.inventory.createMatterFromMobChip(this.inventory.getChip(), oldOutput.getCount() + 1);

            this.inventory.setStackInSlot(DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT, newInput);
            this.inventory.setStackInSlot(DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT, newOutput);

            if(this.byproductSuccess) {
                // If Byproduct roll was successful
                this.byproductSuccess = false;
                ItemStack oldPristine = this.inventory.getPristine();
                ItemStack newPristine = ItemMobChip.getMobMetaData(this.inventory.getChip()).getPristineMatter(this.inventory.getChip(), oldPristine.getCount() + 1);
                this.inventory.setStackInSlot(DeepConstants.SIMULATION_CHAMBER_PRISTINE_SLOT, newPristine);
            }

            IBlockState state = this.world.getBlockState(this.getPos());
            this.world.notifyBlockUpdate(this.getPos(), state, state, 3);
        }
    }

    private boolean canStartSimulation() {
        return this.hasEnergyForSimulation() && this.canContinueSimulation() && !this.inventory.outputIsFull() && !this.inventory.pristineIsFull();
    }

    private boolean canContinueSimulation() {
        return this.inventory.hasChip() && ItemMobChip.getTier(this.inventory.getChip()) != 0
                && this.inventory.hasPolymerClay();
    }


    public boolean hasEnergyForSimulation() {
        if(this.inventory.hasChip()) {
            return this.energyStorage.getEnergyStored() > this.ticksPerSimulation * ItemMobChip.getMobMetaData(this.inventory.getChip()).getSimulationTickCost();
        } else {
            return false;
        }
    }

    private void updateSimulationText(ItemStack chip) {
        String[] lines = new String[] {
                "> Launching runtime",
                "v1.4.7",
                "> Iteration #" + (ItemMobChip.getTotalSimulationCount(chip) + 1) + " started",
                "> Loading model from chip memory",
                "> Assessing threat level",
                "> Engaged enemy",
                "> Pristine procurement",
                this.byproductSuccess ? "succeeded" : "failed",
                "> Processing results",
                "..."
        };

        String resultPrefix = this.byproductSuccess ? "§a" : "§c";

        Animation aLine1 = this.getAnimation("simulationProgressLine1");
        Animation aLine1Version = this.getAnimation("simulationProgressLine1Version");

        Animation aLine2 = this.getAnimation("simulationProgressLine2");

        Animation aLine3 = this.getAnimation("simulationProgressLine3");
        Animation aLine4 = this.getAnimation("simulationProgressLine4");
        Animation aLine5 = this.getAnimation("simulationProgressLine5");

        Animation aLine6 = this.getAnimation("simulationProgressLine6");
        Animation aLine6Result = this.getAnimation("simulationProgressLine6Result");

        Animation aLine7 = this.getAnimation("simulationProgressLine7");
        Animation aLine8 = this.getAnimation("blinkingDots1");

        this.simulationText.put("simulationProgressLine1", this.animate(lines[0], aLine1, null, 1, false));
        this.simulationText.put("simulationProgressLine1Version", "§6" + this.animate(lines[1], aLine1Version, aLine1, 1, false) + "§r");

        this.simulationText.put("simulationProgressLine2", this.animate(lines[2], aLine2, aLine1Version, 1, false));

        this.simulationText.put("simulationProgressLine3", this.animate(lines[3], aLine3, aLine2, 2, false));
        this.simulationText.put("simulationProgressLine4", this.animate(lines[4], aLine4, aLine3, 1, false));
        this.simulationText.put("simulationProgressLine5", this.animate(lines[5], aLine5, aLine4, 2, false));

        this.simulationText.put("simulationProgressLine6", this.animate(lines[6], aLine6, aLine5, 2, false));
        this.simulationText.put("simulationProgressLine6Result", resultPrefix + this.animate(lines[7], aLine6Result, aLine6, 2, false) + "§r");

        this.simulationText.put("simulationProgressLine7", this.animate(lines[8], aLine7, aLine6Result, 1, false));
        this.simulationText.put("blinkingDots1", this.animate(lines[9], aLine8, aLine7, 8, true));
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
        tag.setBoolean("craftSuccess", this.byproductSuccess);
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
        compound.setBoolean("craftSuccess", this.byproductSuccess);
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        energyStorage.readEnergy(compound);
        this.percentDone = compound.hasKey("simulationProgress", Constants.NBT.TAG_INT) ? compound.getInteger("simulationProgress") : 0;
        this.isCrafting = compound.hasKey("isCrafting", Constants.NBT.TAG_BYTE) ? compound.getBoolean("isCrafting") : this.isCrafting;
        this.byproductSuccess = compound.hasKey("craftSuccess", Constants.NBT.TAG_BYTE) ? compound.getBoolean("craftSuccess") : this.isCrafting;
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
