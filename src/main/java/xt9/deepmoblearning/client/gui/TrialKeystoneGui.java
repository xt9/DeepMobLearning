package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.client.gui.button.TransparentButton;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.inventory.ContainerTrialKeystone;
import xt9.deepmoblearning.common.network.TrialStartMessage;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.TrialRuleset;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.util.Color;
import xt9.deepmoblearning.common.util.Tier;
import xt9.deepmoblearning.common.util.TrialKey;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xt9 on 2018-03-25.
 */
public class TrialKeystoneGui extends GuiContainer {
    private static final ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/trial_keystone_gui.png");
    private static final ResourceLocation base_blank = new ResourceLocation(DeepConstants.MODID, "textures/gui/trial_keystone_gui_blank.png");

    private static final ResourceLocation defaultGui = new ResourceLocation(DeepConstants.MODID, "textures/gui/default_gui.png");

    private TileEntityTrialKeystone tile;
    private static final int WIDTH =  200;
    private static final int HEIGHT = 178;
    private ItemStack trialKey;
    private GuiButton startButton;
    private HashMap<GuiButton, ItemStack> rewardButtons = new HashMap<>();

    public TrialKeystoneGui(TileEntityTrialKeystone tile, InventoryPlayer inventory, World world) {
        super(new ContainerTrialKeystone(tile, inventory, world));
        this.tile = tile;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();
        trialKey = tile.getTrialKey();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        trialKey = tile.getTrialKey();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (startButton.mousePressed(mc, mouseX, mouseY)) {
            actionPerformed(startButton);
        }
    }

    @Override
    protected void actionPerformed(GuiButton pressedButton) {
        // Dispatch network message to start the trial
        DeepMobLearning.network.sendToServer(new TrialStartMessage());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        // Just draw a test button for now so we can try the network message
        int left = getGuiLeft();
        int top = getGuiTop();
        startButton = new GuiButton(13, left + 114, top + 76, 82, 20, "Start trial");
        startButton.enabled = false;
        startButton.visible = false;

        if(tile.hasTrialKey() && TrialKey.isAttuned(tile.getTrialKey()) && !tile.isTrialActive() && tile.areaIsClear()) {
            startButton.visible = true;
            startButton.enabled = true;
        }
    }

    /* Needed on 1.12 to render tooltips */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawStartButton(mouseX, mouseY);
        renderHoveredToolTip(mouseX, mouseY);
        drawButtonHoverText(mouseX, mouseY);
    }

    private void drawButtonHoverText(int mouseX, int mouseY) {
        rewardButtons.forEach((btn, stack) -> {
            if(btn.isMouseOver()) {
                renderToolTip(stack, mouseX, mouseY);
            }
        });
    }

    private void drawStartButton(int mouseX, int mouseY) {
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        startButton.drawButton(mc, mouseX, mouseY, 0);
        rewardButtons.forEach((button, stack) -> button.drawButton(mc, mouseX, mouseY, 0));
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int left = getGuiLeft();
        int top = getGuiTop();

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base_blank);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 200, 100);


        // Draw data model slot
        drawTexturedModalRect(left - 20, top, 0, 100, 18, 18);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect( left + 12, top + 106, 0, 0, 176, 90);


        if(tile.isTrialActive()) {
            drawCenteredString(fontRenderer, "Trial is currently active", left + 100, top + 34, Color.WHITE);
            drawCenteredString(fontRenderer, "§6Rewards§r will drop upon completion.", left + 100, top + 46, 	Color.WHITE);
            drawCenteredString(fontRenderer, "Current wave: (" + (tile.getCurrentWave() + 1) + "/" + tile.getLastWave() + ")", left + 100, top + 58, 	Color.WHITE);
            return;
        }

        if(!tile.areaIsClear()) {
            drawCenteredString(fontRenderer, "Something is blocking the §bTrial Area§r", left + 100, top + 22, Color.WHITE);
            drawCenteredString(fontRenderer, "Make sure a 15x15x10 area is clear", left + 100, top + 34, Color.WHITE);
            drawCenteredString(fontRenderer, "and the layer beneath the", left + 100, top + 46, Color.WHITE);
            drawCenteredString(fontRenderer, " §bTrial Keystone§r is made up", left + 100, top + 58, Color.WHITE);
            drawCenteredString(fontRenderer, "of full blocks (also 15x15)", left + 100, top + 70, Color.WHITE);
            return;
        }

        if(!tile.hasTrialKey()) {
            drawCenteredString(fontRenderer, "Please insert a attuned Trial Key", left + 100, top + 42, 	Color.WHITE);
            drawCenteredString(fontRenderer, "to start a trial", left + 100, top + 54, 	Color.WHITE);
            return;
        }

        if(tile.hasTrialKey() && !TrialKey.isAttuned(tile.getTrialKey())) {
            drawCenteredString(fontRenderer, "Current key is not attuned", left + 100, top + 34, 	Color.WHITE);
            drawCenteredString(fontRenderer, "learn more about attunement in the", left + 100, top + 46, 	Color.WHITE);
            drawCenteredString(fontRenderer, "guide entry for the " + new ItemStack(Registry.trialKey).getDisplayName(), left + 100, top + 58, 	Color.WHITE);
            return;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 200, 100);

        if(tile.hasTrialKey() && TrialKey.isAttuned(tile.getTrialKey()) && !tile.isTrialActive()) {
            drawKeyInfo(left, top);
        }
    }

    private void drawKeyInfo(int x, int y) {
        NonNullList<ITrialAffix> affixes = TrialKey.getAffixes(trialKey, tile.getPos(), tile.getWorld());

        drawString(fontRenderer, "Trial type", x + 6, y + 6, Color.AQUA);
        drawString(fontRenderer, TrialKey.getMobMetaData(trialKey).getName(), x + 6, y + 18, Color.WHITE);

        // Top right
        drawString(fontRenderer, "Tier:", x + 115, y + 6, Color.AQUA);
        drawString(fontRenderer, Tier.getTierName(TrialKey.getTier(trialKey), false) + "", x + 140, y + 6, Color.WHITE);

        drawString(fontRenderer, "Waves:", x + 115, y + 18, Color.AQUA);
        drawString(fontRenderer, TrialRuleset.getMaxWaveFromTier(TrialKey.getTier(trialKey)) + "", x + 150, y + 18, Color.WHITE);


        int i = 0;
        rewardButtons.clear();
        drawString(fontRenderer, "Rewards", x + 115, y + 38, Color.AQUA);
        for (ItemStack itemStack : TrialFactory.getRewards(trialKey)) {
            // Attach a non-visible button to each reward so we can render a tooltip for it
            GuiButton btn = new TransparentButton(i, x + 115 + (18 * i), y + 48, 18, 18);
            rewardButtons.put(btn, itemStack);

            drawItemStackWithCount(x + 118 + (18 * i), y + 48, itemStack);
            i++;
        }

        if(affixes.size() > 0) {
            drawString(fontRenderer, "Affixes", x + 6, y + 38, Color.AQUA);

            i = 0;
            for (ITrialAffix affix : affixes) {
                drawString(fontRenderer, affix.getAffixNameWithFormatting(), x + 6, y + 50 + (12 * i), Color.WHITE);
                i++;
            }
        }
    }

    private void drawItemStackWithCount(int x, int y, ItemStack stack) {
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(fontRenderer, stack, x - 1, y - 1, stack.getCount() != 1 ? stack.getCount() + ""  : "");
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
}

