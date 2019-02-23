package xt9.deepmoblearning.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
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
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemPolymerClay;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.MobMetaFactory;
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
public class TileEntitySimulationChamber extends TileEntity implements ITickable, IInteractionObject {
    private BaseItemHandler dataModel= new DataModelHandler();
    private BaseItemHandler polymer = new PolymerHandler();
    private BaseItemHandler lOutput = new OutputHandler();
    private BaseItemHandler pOutput = new OutputHandler();
    private DeepEnergyStorage energyStorage = new DeepEnergyStorage(2000000, 25600 , 0, 0);

    private HashMap<String, Animation> simulationAnimations = new HashMap<>();
    private HashMap<String, String> simulationText = new HashMap<>();

    private boolean isCrafting = false;
    private boolean byproductSuccess = false;
    public int energy = 0;
    public int ticks = 0;
    public int percentDone = 0;
    private String currentDataModelType = "";
    private MobMetaData mobMetaData;

    public TileEntitySimulationChamber() {
        super(Registry.tileSimulationChamber);
    }

    @Override
    public void tick() {
        ticks++;

        if(!world.isRemote) {
            energyStorage.receiveEnergy(520, false);
            if(!isCrafting()) {
                if(canStartSimulation()) {
                    startSimulation();
                }
            } else {
                if (!canContinueSimulation() || dataModelTypeChanged()) {
                    finishSimulation(true);
                    return;
                }

                updateSimulationText(getDataModel());

                if (percentDone == 0) {
                    Random rand = new Random();
                    int num = rand.nextInt(100);
                    int chance = DataModel.getPristineChance(getDataModel());
                    byproductSuccess = num <= MathHelper.ensureRange(chance, 1, 100);
                }

                int rfTickCost = mobMetaData.getSimulationTickCost();
                energyStorage.voidEnergy(rfTickCost);

                if (ticks % ((DeepConstants.TICKS_TO_SECOND * 15) / 100) == 0) {
                    percentDone++;
                }

                // Notify while crafting every other second, this is done more frequently when the container is open
                if (ticks % (DeepConstants.TICKS_TO_SECOND * 2) == 0) {
                    updateState();
                }
            }

            if(percentDone == 100) {
                finishSimulation(false);
                return;
            }

            doStaggeredDiskSave(100);
        }
    }

    private void startSimulation() {
        isCrafting = true;
        currentDataModelType = DataModel.getMobMetaData(getDataModel()).getKey();
        mobMetaData = MobMetaFactory.createMobMetaData(currentDataModelType);
        ItemStack oldInput = getPolymerClay();
        ItemStack newInput = new ItemStack(Registry.polymerClay, oldInput.getCount() - 1);
        polymer.setStackInSlot(0, newInput);
        resetAnimations();
    }

    private void finishSimulation(boolean abort) {
        resetAnimations();
        percentDone = 0;
        isCrafting = false;
        // Only decrease input and increase output if not aborted, and only if on the server's TE
        if(!abort && !world.isRemote) {
            DataModel.increaseSimulationCount(getDataModel());

            ItemStack oldOutput = getLiving();
            ItemStack newOutput = mobMetaData.getLivingMatterStack(oldOutput.getCount() + 1);
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
        return hasEnergyForSimulation() && canContinueSimulation() && !outputIsFull() && !pristineIsFull() && hasPolymerClay();
    }

    private boolean canContinueSimulation() {
        return hasDataModel() && DataModel.getTier(getDataModel()) != 0;
    }

    private boolean dataModelTypeChanged() {
        return !currentDataModelType.equals(DataModel.getMobMetaData(getDataModel()).getKey());
    }

    public boolean hasEnergyForSimulation() {
        if(hasDataModel()) {
            int ticksPerSimulation = 300;
            return energyStorage.getEnergyStored() > (ticksPerSimulation * DataModel.getSimulationTickCost(getDataModel()));
        } else {
            return false;
        }
    }

    public ItemStack getDataModel() {
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

    public boolean hasDataModel() {
        return getDataModel().getItem() instanceof ItemDataModel;
    }

    public boolean outputIsFull() {
        ItemStack stack = getLiving();
        if(stack.isEmpty()) {
            return false;
        }

        boolean stackLimitReached = stack.getCount() == lOutput.getSlotLimit(0);
        boolean outputMatches = dataModelMatchesOutput(getDataModel(), getLiving());

        return stackLimitReached || !outputMatches;
    }

    public boolean isCrafting() {
        return isCrafting;
    }

    public boolean pristineIsFull() {
        ItemStack stack = getPristine();
        if(stack.isEmpty()) {
            return false;
        }

        boolean stackLimitReached = stack.getCount() == pOutput.getSlotLimit(0);
        boolean outputMatches = dataModelMatchesPristine(getDataModel(), getPristine());

        return stackLimitReached || !outputMatches;
    }

    private static boolean dataModelMatchesOutput(ItemStack stack, ItemStack output) {
        Item livingMatter = DataModel.getMobMetaData(stack).getLivingMatter();
        return livingMatter.getClass().equals(output.getItem().getClass());
    }

    private static boolean dataModelMatchesPristine(ItemStack stack, ItemStack pristine) {
        Item pristineMatter = DataModel.getMobMetaData(stack).getPristineMatter();
        return pristineMatter.getClass().equals(pristine.getItem().getClass());
    }

    private void updateSimulationText(ItemStack stack) {
        String[] lines = new String[] {
                "> Launching runtime",
                "v1.4.7",
                "> Iteration #" + (DataModel.getTotalSimulationCount(stack) + 1) + " started",
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
                return anim.animate(string, delayInTicks, world.getWorldInfo().getGameTime(), loop);
            } else {
                return "";
            }
        }
        return  anim.animate(string, delayInTicks, world.getWorldInfo().getGameTime(), loop);
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
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 3, write(new NBTTagCompound()));
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return this.write(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        read(packet.getNbtCompound());
    }

    @Nonnull
    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        compound.setInt("simulationProgress", percentDone);
        compound.setBoolean("isCrafting", isCrafting);
        compound.setBoolean("craftSuccess", byproductSuccess);
        compound.setTag("dataModel", dataModel.serializeNBT());
        compound.setTag("polymer", polymer.serializeNBT());
        compound.setTag("lOutput", lOutput.serializeNBT());
        compound.setTag("pOutput", pOutput.serializeNBT());
        compound.setTag("dataModel", dataModel.serializeNBT());
        compound.setTag("simulationText", getNBTForSimulationText());
        energyStorage.writeEnergy(compound);
        return super.write(compound);
    }


    @Override
    public void read(NBTTagCompound compound) {
        dataModel.deserializeNBT(compound.getCompound("dataModel"));
        polymer.deserializeNBT(compound.getCompound("polymer"));
        lOutput.deserializeNBT(compound.getCompound("lOutput"));
        pOutput.deserializeNBT(compound.getCompound("pOutput"));
        dataModel.deserializeNBT(compound.getCompound("dataModel"));
        setSimulationTextFromNBT(compound.getCompound("simulationText"));
        energyStorage.readEnergy(compound);
        percentDone = compound.hasKey("simulationProgress") ? compound.getInt("simulationProgress") : 0;
        isCrafting = compound.hasKey("isCrafting") ? compound.getBoolean("isCrafting") : isCrafting;
        byproductSuccess = compound.hasKey("craftSuccess") ? compound.getBoolean("craftSuccess") : isCrafting;
        super.read(compound);
    }

    private NBTTagCompound getNBTForSimulationText() {
        NBTTagCompound tag = new NBTTagCompound();
        simulationText.forEach(tag::setString);
        return tag;
    }

    private void setSimulationTextFromNBT(NBTTagCompound tag) {
        simulationText.forEach((key, text) -> simulationText.put(key, tag.getString(key)));
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

    @Nullable
    @Override
    @SuppressWarnings({"unchecked", "NullableProblems"})
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(facing == null) {
                return LazyOptional.of(() -> (T) new CombinedInvWrapper(dataModel, polymer, lOutput, pOutput));
            } else if(facing == EnumFacing.UP) { // Inputs from the top
                return LazyOptional.of(() -> (T) new CombinedInvWrapper(dataModel, polymer));
            } else { // Output for all other sides
                return LazyOptional.of(() -> (T) new CombinedInvWrapper(lOutput, pOutput));
            }
        } else if(capability == CapabilityEnergy.ENERGY) {
            return LazyOptional.of(() -> (T) energyStorage);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
        return new ContainerSimulationChamber(this, inventory, this.world);
    }

    @Override
    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    public String getGuiID() {
        return new ResourceLocation(DeepConstants.MODID, "tile/simulation_chamber").toString();
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public ITextComponent getName() {
        return new TextComponentString("Simulation Chamber");
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }
}
