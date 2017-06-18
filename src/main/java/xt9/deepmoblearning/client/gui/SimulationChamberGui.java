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
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.items.ItemMobChip;
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
    private IItemHandler itemHandler;
    private IEnergyStorage energyStorage;
    private FontRenderer renderer;
    private World world;


    private static final ResourceLocation base = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/simulation_chamber_base.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/default_gui.png");

    public SimulationChamberGui(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        super(new ContainerSimulationChamber(te, inventory, world));

        this.itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.energyStorage = te.getCapability(CapabilityEnergy.ENERGY, null);
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
        int left = getGuiLeft();
        int top = getGuiTop();
        int spacing = 12;
        int topStart = top - 3;

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 216, 141);

        // Draw chip slot
        drawTexturedModalRect(left - 22, top, 0, 141, 18, 18);

        // Draw energy bar
        drawTexturedModalRect(left + 220, top + 22, 216, 0, 12, 101);

        int energyBarHeight = (int) (((float) this.energyStorage.getEnergyStored() / this.energyStorage.getMaxEnergyStored() * 99));
        int energyBarOffset = 99 - height;
        drawTexturedModalRect(left + 221,  top + 23 + energyBarOffset, 228, 0, 10, energyBarHeight);

        if(this.chipChanged()) {
            this.resetAnimations();
        }

        if(!this.hasChip()) {
            this.animateString("Please insert a data model...", this.getAnimation("pleaseInsert"), null, 20, false, left + 10, topStart + spacing, 16777215);
        } else if(ItemMobChip.getTier(this.getChip()) == 0) {

            String[] il = new String[] {"Insufficient data in model", "please insert a basic model", "or better "};

            Animation insufData = this.getAnimation("insufData");
            Animation insufData2 = this.getAnimation("insufData2");
            Animation insufData3 = this.getAnimation("insufData3");

            this.animateString(il[0], insufData, null, 20, false, left + 10, topStart + spacing, 16777215);
            this.animateString(il[1], insufData2, insufData, 20, false,  left + 10, topStart + (spacing * 2), 16777215);
            this.animateString(il[2], insufData3, insufData2, 20, false,  left + 10, topStart + (spacing * 3), 16777215);

        } else {
            // Draw chip experience
            drawTexturedModalRect(left - 16, top + 22, 216, 0, 12, 101);

            if(ItemMobChip.getTier(this.getChip()) == DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
                drawTexturedModalRect(left - 15,  top + 23, 228, 0, 10, 99);
            } else {
                int collectedData = ItemMobChip.getCurrentTierSimulationCountWithKills(this.getChip());
                int tierRoof = ItemMobChip.getTierRoof(this.getChip());

                int height = (int) (((float) collectedData / tierRoof * 99));
                int offset = 99 - height;
                drawTexturedModalRect(left - 15,  top + 23 + offset, 228, 0, 10, height);
            }

            drawString(renderer, "Tier: " + ItemMobChip.getTierName(this.getChip(), false), left + 10, topStart + spacing, 16777215);
            drawString(renderer, "Iterations completed: " + f.format(ItemMobChip.getTotalSimulationCount(this.getChip())), left + 10, topStart + spacing * 2, 16777215);
            drawString(renderer, "Success chance: " + ItemMobChip.getSuccessChance(this.getChip()), left + 10, topStart + spacing * 3, 16777215);
        }

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 28, top + 145, 0, 0, 176, 90);


        this.drawConsoleProgress(left, top);
    }

    private void resetAnimations() {
        for(Animation anim : this.animationList.values()) {
            anim.clear();
        }
    }

    private void drawConsoleProgress(int left, int top) {
        this.animateString("_", this.getAnimation("blinkingUnderline"), null, 250, true, left + 10, top + 49, 16777215);
    }

    private ItemStack getChip() {
        return this.itemHandler.getStackInSlot(DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT);
    }

    private boolean hasChip() {
        return this.itemHandler.getStackInSlot(DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT).getItem() instanceof ItemMobChip;
    }

    private boolean chipChanged() {
        // Todo, check if this resets animation when chip increases simulationcount
        if(ItemStack.areItemsEqual(this.currentChip, this.getChip())) {
            return false;
        } else {
            this.currentChip = this.getChip();
            return true;
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
