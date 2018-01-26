package xt9.deepmoblearning.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
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
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.BaseItemHandler;
import xt9.deepmoblearning.common.handlers.DataModelHandler;
import xt9.deepmoblearning.common.handlers.OutputHandler;
import xt9.deepmoblearning.common.handlers.PolymerHandler;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemPolymerClay;
import xt9.deepmoblearning.common.mobs.MobMetaData;
import xt9.deepmoblearning.common.mobs.MobMetaFactory;
import xt9.deepmoblearning.common.util.Animation;
import xt9.deepmoblearning.common.util.DataModel;
import xt9.deepmoblearning.common.util.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by xt9 on 2017-06-15.
 */
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IGuiTile {
    private BaseItemHandler dataModel= new DataModelHandler();
    private BaseItemHandler polymer = new PolymerHandler();
    private BaseItemHandler lOutput = new OutputHandler();
    private BaseItemHandler pOutput = new OutputHandler();
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(2000000, 25600 , 0, 0);

    private HashMap<String, Animation> simulationAnimations = new HashMap<>();
    private HashMap<String, String> simulationText = new HashMap<>();
    public boolean isCrafting = false;
    private boolean byproductSuccess = false;
    public int energy = 0;
    public int ticks = 0;
    public int percentDone = 0;
    private String currentChipType = "";
    private MobMetaData mobMetaData;

    @Override
    public void update() {
        ticks++;

        if(!isCrafting) {
            resetAnimations();
            if(canStartSimulation()) {
                isCrafting = true;
                currentChipType = DataModel.getMobMetaData(getChip()).getKey();
                mobMetaData = MobMetaFactory.createMobMetaData(getChip());

            }
        } else {
            if(!canContinueSimulation() || chipTypeChanged()) {
                finishSimulation(true);
                return;
            }

            updateSimulationText(getChip());

            // Do these on server only
            if(!world.isRemote) {
                if(percentDone == 0) {
                    Random rand = new Random();
                    int num = rand.nextInt(100);
                    int chance = DataModel.getPristineChance(getChip());
                    byproductSuccess = num <= MathHelper.ensureRange(chance, 1, 100);
                }

                int rfTickCost = mobMetaData.getSimulationTickCost();
                energyStorage.voidEnergy(rfTickCost);

                if(ticks % 3 == 0) {
                    // This process takes 300 ticks, which is 15seconds
                    percentDone++;
                }

                // Notify while crafting every other second, this is done more frequently when the container is open
                if(ticks % 40 ==  0) {
                    updateState();
                }
            }

            if(percentDone == 100) {
                finishSimulation(false);
                return;
            }
        }

        if(!world.isRemote) {
            doStaggeredDiskSave(100);
        }

    }

    private void finishSimulation(boolean abort) {
        resetAnimations();
        percentDone = 0;
        isCrafting = false;
        // Only decrease input and increase output if not aborted, and only if on the server's TE
        if(!abort && !world.isRemote) {
            DataModel.increaseSimulationCount(getChip());

            ItemStack oldInput = getPolymerClay();
            ItemStack oldOutput = getLiving();

            ItemStack newInput = new ItemStack(Registry.polymerClay, oldInput.getCount() - 1);
            ItemStack newOutput = mobMetaData.getLivingMatterStack(oldOutput.getCount() + 1);

            polymer.setStackInSlot(0, newInput);
            lOutput.setStackInSlot(0, newOutput);

            if(byproductSuccess) {
                // If Byproduct roll was successful
                byproductSuccess = false;
                ItemStack oldPristine = getPristine();
                ItemStack newPristine = mobMetaData.getPristineMatterStack(oldPristine.getCount() + 1);

                pOutput.setStackInSlot(0, newPristine);
            }

            updateState();
        }
    }

    public void updateState() {
        IBlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    private boolean canStartSimulation() {
        return hasEnergyForSimulation() && canContinueSimulation() && !outputIsFull() && !pristineIsFull();
    }

    private boolean canContinueSimulation() {
        return hasChip() && DataModel.getTier(getChip()) != 0
                && hasPolymerClay();
    }

    private boolean chipTypeChanged() {
        return !currentChipType.equals(DataModel.getMobMetaData(getChip()).getKey());
    }

    public boolean hasEnergyForSimulation() {
        if(hasChip()) {
            int ticksPerSimulation = 300;
            return energyStorage.getEnergyStored() > (ticksPerSimulation * DataModel.getSimulationTickCost(getChip()));
        } else {
            return false;
        }
    }

    public ItemStack getChip() {
        return dataModel.getStackInSlot(0);
    }

    private ItemStack getPolymerClay() {
        return polymer.getStackInSlot(0);
    }

    private ItemStack getLiving() {
        return lOutput.getStackInSlot(0);
    }

    private ItemStack getPristine() {
        return pOutput.getStackInSlot(0);
    }

    public boolean hasPolymerClay() {
        ItemStack stack = getPolymerClay();
        return stack.getItem() instanceof ItemPolymerClay && stack.getCount() > 0;
    }

    public boolean hasChip() {
        return getChip().getItem() instanceof ItemDataModel;
    }

    public boolean outputIsFull() {
        ItemStack stack = getLiving();
        if(stack.isEmpty()) {
            return false;
        }

        boolean stackLimitReached = stack.getCount() == lOutput.getSlotLimit(0);
        boolean outputMatches = chipMatchesOutput(getChip(), getLiving());

        return stackLimitReached || !outputMatches;
    }

    public boolean pristineIsFull() {
        ItemStack stack = getPristine();
        if(stack.isEmpty()) {
            return false;
        }

        boolean stackLimitReached = stack.getCount() == pOutput.getSlotLimit(0);
        boolean outputMatches = chipMatchesPristine(getChip(), getPristine());

        return stackLimitReached || !outputMatches;
    }

    private static boolean chipMatchesOutput(ItemStack chip, ItemStack stack) {
        Item livingMatter = DataModel.getMobMetaData(chip).getLivingMatter();
        return livingMatter.getClass().equals(stack.getItem().getClass());
    }

    private static boolean chipMatchesPristine(ItemStack chip, ItemStack stack) {
        Item pristineMatter = DataModel.getMobMetaData(chip).getPristineMatter();
        return pristineMatter.getClass().equals(stack.getItem().getClass());
    }

    private void updateSimulationText(ItemStack chip) {
        String[] lines = new String[] {
                "> Launching runtime",
                "v1.4.7",
                "> Iteration #" + (DataModel.getTotalSimulationCount(chip) + 1) + " started",
                "> Loading model from chip memory",
                "> Assessing threat level",
                "> Engaged enemy",
                "> Pristine procurement",
                byproductSuccess ? "succeeded" : "failed",
                "> Processing results",
                "..."
        };

        String resultPrefix = byproductSuccess ? "§a" : "§c";

        Animation aLine1 = getAnimation("simulationProgressLine1");
        Animation aLine1Version = getAnimation("simulationProgressLine1Version");

        Animation aLine2 = getAnimation("simulationProgressLine2");

        Animation aLine3 = getAnimation("simulationProgressLine3");
        Animation aLine4 = getAnimation("simulationProgressLine4");
        Animation aLine5 = getAnimation("simulationProgressLine5");

        Animation aLine6 = getAnimation("simulationProgressLine6");
        Animation aLine6Result = getAnimation("simulationProgressLine6Result");

        Animation aLine7 = getAnimation("simulationProgressLine7");
        Animation aLine8 = getAnimation("blinkingDots1");

        simulationText.put("simulationProgressLine1", animate(lines[0], aLine1, null, 1, false));
        simulationText.put("simulationProgressLine1Version", "§6" + animate(lines[1], aLine1Version, aLine1, 1, false) + "§r");

        simulationText.put("simulationProgressLine2", animate(lines[2], aLine2, aLine1Version, 1, false));

        simulationText.put("simulationProgressLine3", animate(lines[3], aLine3, aLine2, 2, false));
        simulationText.put("simulationProgressLine4", animate(lines[4], aLine4, aLine3, 1, false));
        simulationText.put("simulationProgressLine5", animate(lines[5], aLine5, aLine4, 2, false));

        simulationText.put("simulationProgressLine6", animate(lines[6], aLine6, aLine5, 2, false));
        simulationText.put("simulationProgressLine6Result", resultPrefix + animate(lines[7], aLine6Result, aLine6, 2, false) + "§r");

        simulationText.put("simulationProgressLine7", animate(lines[8], aLine7, aLine6Result, 1, false));
        simulationText.put("blinkingDots1", animate(lines[9], aLine8, aLine7, 8, true));
    }

    public void resetAnimations() {
        simulationAnimations = new HashMap<>();
        simulationText = new HashMap<>();
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
        if(simulationAnimations.containsKey(key)) {
            return simulationAnimations.get(key);
        } else {
            simulationAnimations.put(key, new Animation());
            return simulationAnimations.get(key);
        }
    }

    public String getSimulationText(String key) {
        if(simulationText.containsKey(key)) {
            return simulationText.get(key);
        } else {
            simulationText.put(key, "");
            return simulationText.get(key);
        }
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        return getNetworkTag(super.getUpdateTag());
    }

    private NBTTagCompound getNetworkTag(NBTTagCompound tag) {
        tag.setInteger("simulationProgress", percentDone);
        tag.setBoolean("isCrafting", isCrafting);
        tag.setBoolean("craftSuccess", byproductSuccess);
        return energyStorage.writeEnergy(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("dataModel", dataModel.serializeNBT());
        compound.setTag("polymer", polymer.serializeNBT());
        compound.setTag("lOutput", lOutput.serializeNBT());
        compound.setTag("dataModel", dataModel.serializeNBT());
        compound.setInteger("simulationProgress", percentDone);
        compound.setBoolean("isCrafting", isCrafting);
        compound.setBoolean("craftSuccess", byproductSuccess);
        energyStorage.writeEnergy(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        dataModel.deserializeNBT(compound.getCompoundTag("dataModel"));
        polymer.deserializeNBT(compound.getCompoundTag("polymer"));
        lOutput.deserializeNBT(compound.getCompoundTag("lOutput"));
        dataModel.deserializeNBT(compound.getCompoundTag("dataModel"));
        energyStorage.readEnergy(compound);
        percentDone = compound.hasKey("simulationProgress", Constants.NBT.TAG_INT) ? compound.getInteger("simulationProgress") : 0;
        isCrafting = compound.hasKey("isCrafting", Constants.NBT.TAG_BYTE) ? compound.getBoolean("isCrafting") : isCrafting;
        byproductSuccess = compound.hasKey("craftSuccess", Constants.NBT.TAG_BYTE) ? compound.getBoolean("craftSuccess") : isCrafting;
        super.readFromNBT(compound);
    }

    private void doStaggeredDiskSave(int divisor) {
        if(ticks % divisor == 0) {
            if(energy != energyStorage.getEnergyStored()) {
                // Save to disk every 5 seconds if energy changed
                energy = energyStorage.getEnergyStored();
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
            if(facing == null) {
                return (T) new CombinedInvWrapper(dataModel, polymer, lOutput, pOutput);
            } else if(facing == EnumFacing.UP) {
                // Input/Extract for Data models and Polymer from the top
                return (T) new CombinedInvWrapper(dataModel, polymer);
            } else if(facing != null && facing != EnumFacing.UP) {
                // Output for all other sides
                return (T) new CombinedInvWrapper(lOutput, pOutput);
            }
        } else if(capability == CapabilityEnergy.ENERGY) {
            return (T) energyStorage;
        }

        return super.getCapability(capability, facing);
    }

    public int getGuiID() {
        return DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID;
    }
}
