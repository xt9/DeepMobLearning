package xt9.deepmoblearning.client.gui;

import jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
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
import xt9.deepmoblearning.common.items.ItemPolymerClay;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.util.Animation;
import java.text.DecimalFormat;
import java.util.HashMap;

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

        this.renderer = Minecraft.getMinecraft().fontRendererObj;
        this.animationList = new HashMap<>();
        this.world = world;
        this.tile = te;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        DecimalFormat f = new DecimalFormat("0.#");
        int left = getGuiLeft() + 8;
        int top = getGuiTop();
        int spacing = 12;
        int topStart = top - 3;

        if(this.chipChanged() || this.craftingFinished()) {
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

            this.animateString(lines[0], a1, null, 20, false, left + 10, topStart + spacing, 16777215);
            this.animateString(lines[1], a2, a1, 20, false, left + 10, topStart + (spacing * 2), 16777215);

        } else if(ItemMobChip.getTier(this.itemHandler.getChip()) == 0) {

            lines = new String[] {"Insufficient data in model", "please insert a basic model", "or better "};

            Animation insufData = this.getAnimation("insufData1");
            Animation insufData2 = this.getAnimation("insufData2");
            Animation insufData3 = this.getAnimation("insufData3");

            this.animateString(lines[0], insufData, null, 20, false, left + 10, topStart + spacing, 16777215);
            this.animateString(lines[1], insufData2, insufData, 20, false,  left + 10, topStart + (spacing * 2), 16777215);
            this.animateString(lines[2], insufData3, insufData2, 20, false,  left + 10, topStart + (spacing * 3), 16777215);

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
            drawString(renderer, "Living clay chance: " + ItemMobChip.getSuccessChance(this.itemHandler.getChip()) + "%", left + 10, topStart + spacing * 3, 16777215);
        }

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 20, top + 145, 0, 0, 176, 90);


        this.drawConsoleText(left, top, spacing);
    }

    private void drawConsoleText(int left, int top, int spacing) {
        String[] lines;

        if(!this.itemHandler.hasChip()) {
            this.animateString("_", this.getAnimation("blinkingUnderline"), null, 250, true, left + 21, top + 49, 16777215);

        } else if(!this.itemHandler.hasPolymer()) {
            lines = new String[] {"Cannot begin simulation", "Missing binding agent", "_"};
            Animation a1 = this.getAnimation("inputSlotEmpty1");
            Animation a2 = this.getAnimation("inputSlotEmpty2");
            Animation a3 = this.getAnimation("blinkingUnderline1");

            this.animateString(lines[0], a1, null, 20, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 20, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 250, true, left + 21, top + 51 + (spacing * 2), 16777215);

        } else if(!this.hasEnergy() && !this.tile.isCrafting) {
            lines = new String[] {"Cannot begin simulation", "System energy levels critical", "_"};
            Animation a1 = this.getAnimation("lowEnergy1");
            Animation a2 = this.getAnimation("lowEnergy2");
            Animation a3 = this.getAnimation("blinkingUnderline2");

            this.animateString(lines[0], a1, null, 20, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 20, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 250, true, left + 21, top + 51 + (spacing * 2), 16777215);
        } else if(this.itemHandler.outputIsFull()) {
            lines = new String[] {"Cannot begin simulation", "Output buffer is full", "_"};
            Animation a1 = this.getAnimation("outputSlotFilled1");
            Animation a2 = this.getAnimation("outputSlotFilled2");
            Animation a3 = this.getAnimation("blinkingUnderline3");

            this.animateString(lines[0], a1, null, 20, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 20, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 250, true, left + 21, top + 51 + (spacing * 2), 16777215);
        } else {
            lines = new String[] {"Simulation started", "Loading Data model", "Assessing threat level", "Engaged enemy", "Processing results", "..."};
            Animation a1 = this.getAnimation("simulationProgress1");
            Animation a2 = this.getAnimation("simulationProgress2");
            Animation a3 = this.getAnimation("simulationProgress3");
            Animation a4 = this.getAnimation("simulationProgress4");
            Animation a5 = this.getAnimation("simulationProgress5");
            Animation a6 = this.getAnimation("blinkingUnderline4");

            drawString(renderer, this.tile.percentDone + "%", left + 176, top + 123, 6478079);

            this.animateString(lines[0], a1, null, 20, false, left + 21, top + 51, 16777215);
            this.animateString(lines[1], a2, a1, 90, false, left + 21, top + 51 + spacing, 16777215);
            this.animateString(lines[2], a3, a2, 90, false, left + 21, top + 51 + (spacing * 2), 16777215);
            this.animateString(lines[3], a4, a3, 90, false, left + 21, top + 51 + (spacing * 3), 16777215);
            this.animateString(lines[4], a5, a4, 90, false, left + 21, top + 51 + (spacing * 4), 16777215);
            this.animateString(lines[5], a6, a5, 300, true, left + 118, top + 51 + (spacing * 4), 16777215);
        }
    }

    private boolean hasEnergy() {
        return this.tile.hasEnergyForSimulation();
    }

    private boolean craftingFinished() {
        return this.tile.percentDone >= 99;
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
        for(Animation anim : this.animationList.values()) {
            anim.clear();
        }
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
