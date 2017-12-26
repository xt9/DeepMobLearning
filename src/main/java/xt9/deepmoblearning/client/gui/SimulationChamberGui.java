package xt9.deepmoblearning.client.gui;

import jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.handlers.SimulationChamberHandler;
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.util.Animation;
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
    private ItemStack currentChip = ItemStack.EMPTY;
    private TileEntitySimulationChamber tile;
    private SimulationChamberHandler itemHandler;
    private DeepEnergyStorage energyStorage;
    private FontRenderer renderer;
    private World world;

    private static final ResourceLocation base = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/simulation_chamber_base.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/default_gui.png");

    public SimulationChamberGui(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        super(new ContainerSimulationChamber(te, inventory, world));

        this.itemHandler = (SimulationChamberHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.energyStorage = (DeepEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY, null);

        this.renderer = Minecraft.getMinecraft().fontRenderer;
        this.animationList = new HashMap<>();
        this.world = world;
        this.tile = te;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    /* Needed on 1.12 to render tooltips */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = mouseX - guiLeft;
        int y = mouseY - guiTop;

        NumberFormat f = NumberFormat.getNumberInstance(Locale.ENGLISH);
        List<String> tooltip = new ArrayList<>();

        if(47 <= y && y < 135) {
            if(13 <= x && x < 22) {
                // Tooltip for Chip exp bar
                if(this.itemHandler.hasChip()) {
                    if(ItemMobChip.getTier(this.itemHandler.getChip()) != DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
                        tooltip.add(ItemMobChip.getCurrentTierSimulationCountWithKills(this.itemHandler.getChip()) + "/" + ItemMobChip.getTierRoof(this.itemHandler.getChip()) + " Data collected");
                    } else {
                        tooltip.add("This data model has reached the max tier.");
                    }
                } else {
                    tooltip.add("Machine is missing a data model");
                }
                drawHoveringText(tooltip, x + 2, y + 2);
            } else if(211 <= x && x < 220) {
                tooltip.add(f.format(this.energyStorage.getEnergyStored()) + "/" + f.format(this.energyStorage.getMaxEnergyStored()) + " RF");
                if(this.itemHandler.hasChip()) {
                    tooltip.add("Simulations with current data model drains " + f.format(ItemMobChip.getMobMetaData(this.itemHandler.getChip()).getSimulationTickCost()) + "RF/t");
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

        if(this.chipChanged()) {
            this.resetAnimations();
        }

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 216, 141);

        // Draw chip slot
        drawTexturedModalRect(left - 22, top, 0, 141, 18, 18);

        // Draw current energy
        int energyBarHeight = (int) (((float) this.energyStorage.getEnergyStored() / this.energyStorage.getMaxEnergyStored() * 87));
        int energyBarOffset = 87 - energyBarHeight;
        drawTexturedModalRect(left + 203,  top + 48 + energyBarOffset, 25, 141, 7, energyBarHeight);


        String[] lines;

        if(!this.itemHandler.hasChip()) {
            lines = new String[] {"Please insert a data model", "to begin the simulation"};

            Animation a1 = this.getAnimation("pleaseInsert1");
            Animation a2 = this.getAnimation("pleaseInsert2");

            this.animateString(lines[0], a1, null, 5, false, left + 10, topStart + spacing, 16777215);
            this.animateString(lines[1], a2, a1, 5, false, left + 10, topStart + (spacing * 2), 16777215);

        } else if(ItemMobChip.getTier(this.itemHandler.getChip()) == 0) {

            lines = new String[] {"Insufficient data in model", "please insert a basic model", "or better "};

            Animation insufData = this.getAnimation("insufData1");
            Animation insufData2 = this.getAnimation("insufData2");
            Animation insufData3 = this.getAnimation("insufData3");

            this.animateString(lines[0], insufData, null, 5, false, left + 10, topStart + spacing, 16777215);
            this.animateString(lines[1], insufData2, insufData, 5, false,  left + 10, topStart + (spacing * 2), 16777215);
            this.animateString(lines[2], insufData3, insufData2, 5, false,  left + 10, topStart + (spacing * 3), 16777215);

        } else {
            // Draw current chip experience
            if(ItemMobChip.getTier(this.itemHandler.getChip()) == DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
                drawTexturedModalRect(left + 6,  top + 48, 18, 141, 7, 87);
            } else {
                int collectedData = ItemMobChip.getCurrentTierSimulationCountWithKills(this.itemHandler.getChip());
                int tierRoof = ItemMobChip.getTierRoof(this.itemHandler.getChip());

                int experienceBarHeight = (int) (((float) collectedData / tierRoof * 87));
                int experienceBarOffset = 87 - experienceBarHeight;
                drawTexturedModalRect(left + 6,  top + 48 + experienceBarOffset, 18, 141, 7, experienceBarHeight);
            }

            drawString(renderer, "Tier: " + ItemMobChip.getTierName(this.itemHandler.getChip(), false), left + 10, topStart + spacing, 16777215);
            drawString(renderer, "Iterations: " + f.format(ItemMobChip.getTotalSimulationCount(this.itemHandler.getChip())), left + 10, topStart + spacing * 2, 16777215);
            drawString(renderer, "Pristine chance: " + ItemMobChip.getPristineChance(this.itemHandler.getChip()) + "%", left + 10, topStart + spacing * 3, 16777215);
        }

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 20, top + 145, 0, 0, 176, 90);


        this.drawConsoleText(left, top, spacing);
    }


    private void drawConsoleText(int left, int top, int spacing) {
        String[] lines;

        if(!this.itemHandler.hasChip() || ItemMobChip.getTier(this.itemHandler.getChip()) == 0) {
            this.animateString("_", this.getAnimation("blinkingUnderline"), null, 100, true, left + 21, top + 49, 16777215);

        } else if(!this.itemHandler.hasPolymerClay()) {
            lines = new String[] {"Cannot begin simulation", "Missing polymer medium", "_"};
            Animation a1 = this.getAnimation("inputSlotEmpty1");
            Animation a2 = this.getAnimation("inputSlotEmpty2");
            Animation a3 = this.getAnimation("blinkingUnderline1");

            this.animateString(lines[0], a1, null, 5, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 5, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 100, true, left + 21, top + 51 + (spacing * 2), 16777215);

        } else if(!this.hasEnergy() && !this.tile.isCrafting) {
            lines = new String[] {"Cannot begin simulation", "System energy levels critical", "_"};
            Animation a1 = this.getAnimation("lowEnergy1");
            Animation a2 = this.getAnimation("lowEnergy2");
            Animation a3 = this.getAnimation("blinkingUnderline2");

            this.animateString(lines[0], a1, null, 5, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 5, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 100, true, left + 21, top + 51 + (spacing * 2), 16777215);
        } else if(this.itemHandler.outputIsFull() || this.itemHandler.pristineIsFull()) {
            lines = new String[] {"Cannot begin simulation", "Output or pristine buffer is full", "_"};
            Animation a1 = this.getAnimation("outputSlotFilled1");
            Animation a2 = this.getAnimation("outputSlotFilled2");
            Animation a3 = this.getAnimation("blinkingUnderline3");

            this.animateString(lines[0], a1, null, 5, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 5, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 100, true, left + 21, top + 51 + (spacing * 2), 16777215);
        } else if(this.tile.isCrafting) {
            drawString(renderer, this.tile.percentDone + "%", left + 176, top + 123, 6478079);

            drawString(renderer, this.tile.getSimulationText("simulationProgressLine1"), left + 21, top + 51, 16777215);
            drawString(renderer, this.tile.getSimulationText("simulationProgressLine1Version"), left + 124, top + 51, 16777215);

            drawString(renderer, this.tile.getSimulationText("simulationProgressLine2"), left + 21, top + 51 + spacing, 16777215);

            drawString(renderer, this.tile.getSimulationText("simulationProgressLine3"), left + 21, top + 51 + (spacing * 2), 16777215);
            drawString(renderer, this.tile.getSimulationText("simulationProgressLine4"), left + 21, top + 51 + (spacing * 3), 16777215);
            drawString(renderer, this.tile.getSimulationText("simulationProgressLine5"), left + 21, top + 51 + (spacing * 4), 16777215);

            drawString(renderer, this.tile.getSimulationText("simulationProgressLine6"), left + 21, top + 51 + (spacing * 5), 16777215);
            drawString(renderer, this.tile.getSimulationText("simulationProgressLine6Result"), left + 140, top + 51 + (spacing * 5), 16777215);

            drawString(renderer, this.tile.getSimulationText("simulationProgressLine7"), left + 21, top + 51 + (spacing * 6), 16777215);
            drawString(renderer, this.tile.getSimulationText("blinkingDots1"), left + 128, top + 51 + (spacing * 6), 16777215);
        } else {
            this.animateString("_", this.getAnimation("blinkingUnderline"), null, 250, true, left + 21, top + 49, 16777215);
        }
    }

    private boolean hasEnergy() {
        return this.tile.hasEnergyForSimulation();
    }

    private boolean chipChanged() {
        if(ItemStack.areItemStacksEqual(this.currentChip, this.itemHandler.getChip())) {
            return false;
        } else {
            this.currentChip = this.itemHandler.getChip();
            return true;
        }
    }

    private void resetAnimations() {
        this.animationList = new HashMap<>();
    }

    private Animation getAnimation(String key) {
        if(this.animationList.containsKey(key)) {
            return this.animationList.get(key);
        } else {
            this.animationList.put(key, new Animation());
            return this.animationList.get(key);
        }
    }

    private void animateString(String string, Animation anim, @Nullable Animation precedingAnim, int delay, boolean loop, int left, int top, int color) {
        if(precedingAnim != null) {
            if (precedingAnim.hasFinished()) {
                String result = anim.animate(string, delay, loop);
                drawString(renderer, result, left, top, color);
            } else {
                return;
            }
        }
        String result = anim.animate(string, delay, loop);
        drawString(renderer, result, left, top, color);
    }
}
