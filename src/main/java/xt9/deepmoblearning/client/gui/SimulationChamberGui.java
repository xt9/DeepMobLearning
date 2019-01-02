package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.util.Animation;
import xt9.deepmoblearning.common.util.Color;
import xt9.deepmoblearning.common.util.DataModel;
import xt9.deepmoblearning.common.util.MathHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by xt9 on 2017-06-17.
 */
public class SimulationChamberGui extends GuiContainer {
    private static final int WIDTH =  232;
    private static final int HEIGHT = 230;

    private HashMap<String, Animation> animationList;
    private ItemStack currentDataModel = ItemStack.EMPTY;
    private TileEntitySimulationChamber tile;
    private DeepEnergyStorage energyStorage;
    private FontRenderer renderer;
    private World world;

    private static final ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/simulation_chamber_base.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepConstants.MODID, "textures/gui/default_gui.png");

    public SimulationChamberGui(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        super(new ContainerSimulationChamber(te, inventory, world));

        this.energyStorage = (DeepEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY, null);

        this.renderer = Minecraft.getMinecraft().fontRenderer;
        this.animationList = new HashMap<>();
        this.world = world;
        this.tile = te;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    /* Needed on 1.12 to render tooltips */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = mouseX - guiLeft;
        int y = mouseY - guiTop;

        NumberFormat f = NumberFormat.getNumberInstance(Locale.ENGLISH);
        List<String> tooltip = new ArrayList<>();

        if(47 <= y && y < 135) {
            if(13 <= x && x < 22) {
                // Tooltip for data model data bar
                if(tile.hasDataModel()) {
                    if(DataModel.getTier(tile.getDataModel()) != DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
                        tooltip.add(DataModel.getCurrentTierSimulationCountWithKills(tile.getDataModel()) + "/" + DataModel.getTierRoof(tile.getDataModel()) + " Data collected");
                    } else {
                        tooltip.add("This data model has reached the max tier.");
                    }
                } else {
                    tooltip.add("Machine is missing a data model");
                }
                drawHoveringText(tooltip, x + 2, y + 2);
            } else if(211 <= x && x < 220) {
                // Tooltip for energy
                tooltip.add(f.format(energyStorage.getEnergyStored()) + "/" + f.format(energyStorage.getMaxEnergyStored()) + " RF");
                if(tile.hasDataModel()) {
                    MobMetaData data = DataModel.getMobMetaData(tile.getDataModel());
                    tooltip.add("Simulations with current data model drains " + f.format(data.getSimulationTickCost()) + "RF/t");
                }
                drawHoveringText(tooltip, x - 90, y - 16);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        DecimalFormat f = new DecimalFormat("0.#");
        int left = getGuiLeft() + 8;
        int top = getGuiTop();
        int spacing = 12;
        int topStart = top - 3;
        MobMetaData data = DataModel.getMobMetaData(tile.getDataModel());

        if(dataModelChanged()) {
            resetAnimations();
        }

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 216, 141);

        // Draw data model slot
        drawTexturedModalRect(left - 22, top, 0, 141, 18, 18);

        // Draw current energy
        int energyBarHeight = MathHelper.ensureRange((int) ((float) energyStorage.getEnergyStored() / (energyStorage.getMaxEnergyStored() - data.getSimulationTickCost()) * 87), 0, 87);
        int energyBarOffset = 87 - energyBarHeight;
        drawTexturedModalRect(left + 203,  top + 48 + energyBarOffset, 25, 141, 7, energyBarHeight);


        String[] lines;

        if(!tile.hasDataModel()) {
            lines = new String[] {"Please insert a data model", "to begin the simulation"};

            Animation a1 = getAnimation("pleaseInsert1");
            Animation a2 = getAnimation("pleaseInsert2");

            animateString(lines[0], a1, null, 1, false, left + 10, topStart + spacing, Color.WHITE);
            animateString(lines[1], a2, a1, 1, false, left + 10, topStart + (spacing * 2), Color.WHITE);

        } else if(DataModel.getTier(tile.getDataModel()) == 0) {

            lines = new String[] {"Insufficient data in model", "please insert a basic model", "or better "};

            Animation insufData = getAnimation("insufData1");
            Animation insufData2 = getAnimation("insufData2");
            Animation insufData3 = getAnimation("insufData3");

            animateString(lines[0], insufData, null, 1, false, left + 10, topStart + spacing, Color.WHITE);
            animateString(lines[1], insufData2, insufData, 1, false,  left + 10, topStart + (spacing * 2), Color.WHITE);
            animateString(lines[2], insufData3, insufData2, 1, false,  left + 10, topStart + (spacing * 3), Color.WHITE);

        } else {
            // Draw current data model data
            if(DataModel.getTier(tile.getDataModel()) == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
                drawTexturedModalRect(left + 6,  top + 48, 18, 141, 7, 87);
            } else {
                int collectedData = DataModel.getCurrentTierSimulationCountWithKills(tile.getDataModel());
                int tierRoof = DataModel.getTierRoof(tile.getDataModel());

                int experienceBarHeight = (int) (((float) collectedData / tierRoof * 87));
                int experienceBarOffset = 87 - experienceBarHeight;
                drawTexturedModalRect(left + 6,  top + 48 + experienceBarOffset, 18, 141, 7, experienceBarHeight);
            }

            drawString(renderer, "Tier: " + DataModel.getTierName(tile.getDataModel(), false), left + 10, topStart + spacing, Color.WHITE);
            drawString(renderer, "Iterations: " + f.format(DataModel.getTotalSimulationCount(tile.getDataModel())), left + 10, topStart + spacing * 2, Color.WHITE);
            drawString(renderer, "Pristine chance: " + DataModel.getPristineChance(tile.getDataModel()) + "%", left + 10, topStart + spacing * 3, Color.WHITE);
        }

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 20, top + 145, 0, 0, 176, 90);


        drawConsoleText(left, top, spacing);
    }


    private void drawConsoleText(int left, int top, int spacing) {
        String[] lines;

        if(!tile.hasDataModel() || DataModel.getTier(tile.getDataModel()) == 0) {
            animateString("_", getAnimation("blinkingUnderline"), null, 16, true, left + 21, top + 49, Color.WHITE);

        } else if(!tile.hasPolymerClay() && !tile.isCrafting()) {
            lines = new String[] {"Cannot begin simulation", "Missing polymer medium", "_"};
            Animation a1 = getAnimation("inputSlotEmpty1");
            Animation a2 = getAnimation("inputSlotEmpty2");
            Animation a3 = getAnimation("blinkingUnderline1");

            animateString(lines[0], a1, null, 1, false, left + 21, top + 51, Color.WHITE);
            animateString(lines[1], a2, a1, 1, false, left + 21, top + 51 + spacing, Color.WHITE);
            animateString(lines[2], a3, a2, 16, true, left + 21, top + 51 + (spacing * 2), Color.WHITE);

        } else if(!hasEnergy() && !tile.isCrafting()) {
            lines = new String[] {"Cannot begin simulation", "System energy levels critical", "_"};
            Animation a1 = getAnimation("lowEnergy1");
            Animation a2 = getAnimation("lowEnergy2");
            Animation a3 = getAnimation("blinkingUnderline2");

            animateString(lines[0], a1, null, 1, false, left + 21, top + 51, Color.WHITE);
            animateString(lines[1], a2, a1, 1, false, left + 21, top + 51 + spacing, Color.WHITE);
            animateString(lines[2], a3, a2, 16, true, left + 21, top + 51 + (spacing * 2), Color.WHITE);
        } else if(tile.outputIsFull() || tile.pristineIsFull()) {
            lines = new String[] {"Cannot begin simulation", "Output or pristine buffer is full", "_"};
            Animation a1 = getAnimation("outputSlotFilled1");
            Animation a2 = getAnimation("outputSlotFilled2");
            Animation a3 = getAnimation("blinkingUnderline3");

            animateString(lines[0], a1, null, 1, false, left + 21, top + 51, Color.WHITE);
            animateString(lines[1], a2, a1, 1, false, left + 21, top + 51 + spacing, Color.WHITE);
            animateString(lines[2], a3, a2, 16, true, left + 21, top + 51 + (spacing * 2), Color.WHITE);
        } else if(tile.isCrafting()) {
            drawString(renderer, tile.percentDone + "%", left + 176, top + 123, Color.AQUA);

            drawString(renderer, tile.getSimulationText("simulationProgressLine1"), left + 21, top + 51, Color.WHITE);
            drawString(renderer, tile.getSimulationText("simulationProgressLine1Version"), left + 124, top + 51, Color.WHITE);

            drawString(renderer, tile.getSimulationText("simulationProgressLine2"), left + 21, top + 51 + spacing, Color.WHITE);

            drawString(renderer, tile.getSimulationText("simulationProgressLine3"), left + 21, top + 51 + (spacing * 2), Color.WHITE);
            drawString(renderer, tile.getSimulationText("simulationProgressLine4"), left + 21, top + 51 + (spacing * 3), Color.WHITE);
            drawString(renderer, tile.getSimulationText("simulationProgressLine5"), left + 21, top + 51 + (spacing * 4), Color.WHITE);

            drawString(renderer, tile.getSimulationText("simulationProgressLine6"), left + 21, top + 51 + (spacing * 5), Color.WHITE);
            drawString(renderer, tile.getSimulationText("simulationProgressLine6Result"), left + 140, top + 51 + (spacing * 5), Color.WHITE);

            drawString(renderer, tile.getSimulationText("simulationProgressLine7"), left + 21, top + 51 + (spacing * 6), Color.WHITE);
            drawString(renderer, tile.getSimulationText("blinkingDots1"), left + 128, top + 51 + (spacing * 6), Color.WHITE);
        } else {
            animateString("_", getAnimation("blinkingUnderline"), null, 16, true, left + 21, top + 49, Color.WHITE);
        }
    }

    private boolean hasEnergy() {
        return tile.hasEnergyForSimulation();
    }

    private boolean dataModelChanged() {
        if(ItemStack.areItemStacksEqual(currentDataModel, tile.getDataModel())) {
            return false;
        } else {
            this.currentDataModel = tile.getDataModel();
            return true;
        }
    }

    private void resetAnimations() {
        this.animationList = new HashMap<>();
    }

    private Animation getAnimation(String key) {
        if(animationList.containsKey(key)) {
            return animationList.get(key);
        } else {
            animationList.put(key, new Animation());
            return animationList.get(key);
        }
    }

    private void animateString(String string, Animation anim, Animation precedingAnim, int delay, boolean loop, int left, int top, int color) {
        if(precedingAnim != null) {
            if (precedingAnim.hasFinished()) {
                String result = anim.animate(string, delay, world.getTotalWorldTime(), loop);
                drawString(renderer, result, left, top, color);
            } else {
                return;
            }
        }
        String result = anim.animate(string, delay, world.getTotalWorldTime(), loop);
        drawString(renderer, result, left, top, color);
    }
}
