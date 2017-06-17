package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.util.AnimationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt9 on 2017-06-17.
 */
public class SimulationChamberGui extends GuiContainer {
    private static final int WIDTH =  176;
    private static final int HEIGHT = 230;

    private List<AnimationHelper> animationHelperList;
    private TileEntitySimulationChamber tile;
    private IItemHandler handler;
    private FontRenderer renderer;
    private World world;


    private static final ResourceLocation base = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/simulation_chamber_base.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/default_gui.png");

    public SimulationChamberGui(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        super(new ContainerSimulationChamber(te, inventory, world));

        this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.renderer = Minecraft.getMinecraft().fontRendererObj;
        this.animationHelperList = new ArrayList<>();

        this.world = world;
        this.tile = te;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int left = getGuiLeft();
        int top = getGuiTop();

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 177, 141);

        // Draw chip slot
        drawTexturedModalRect(left - 22, top, 238, 0, 18, 18);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 1, top + 145, 0, 0, 176, 90);

        int spacing = 12;
        int topStart = top - 3;

        if(this.hasChip()) {
            drawString(renderer, "Tier: " + ItemMobChip.getTierName(this.getChip(), false), left + 10, topStart + spacing, 16777215);
            drawString(renderer, "Completed sims: " + ItemMobChip.getCurrentTierSimulationCountWithKills(this.getChip()), left + 10, topStart + spacing * 2, 16777215);
            drawString(renderer, "Success chance: " + ItemMobChip.getSuccessChance(this.getChip()), left + 10, topStart + spacing * 3, 16777215);
        }

        this.animateString("Animating stuff", 0, left + 9, top + 49, 16777215, 30, false);
        this.animateString("at different", 1, left + 9, top + 49 + (spacing), 16777215, 60, false);
        this.animateString("Speeeeeeeeeds", 2, left + 9, top + 49 + (spacing * 2), 16777215, 90, false);
        this.animateString("¯\\_(ツ)_/¯", 3, left + 9, top + 49 + (spacing * 3), 16777215, 120, false);
        this.animateString("_", 4, left + 9, top + 49 + (spacing * 4), 16777215, 144, true);
    }

    public ItemStack getChip() {
        return this.handler.getStackInSlot(DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT);
    }

    public boolean hasChip() {
        return this.handler.getStackInSlot(DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT).getItem() instanceof ItemMobChip;
    }

    public AnimationHelper getAnimationHelper(int index) {
        if(index < this.animationHelperList.size()) {
            return this.animationHelperList.get(index);
        } else {
            this.animationHelperList.add(index, new AnimationHelper());
            return this.animationHelperList.get(index);
        }
    }

    public void animateString(String string, int helperIndex, int left, int top, int color, int speed, boolean loop) {
        AnimationHelper helper = getAnimationHelper(helperIndex);
        String result = helper.animate(string, speed, loop);
        drawString(renderer, result, left, top, color);
    }
}
