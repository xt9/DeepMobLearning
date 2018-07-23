package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.client.gui.button.ClickableZoneButton;
import xt9.deepmoblearning.client.gui.button.ItemSelectButton;
import xt9.deepmoblearning.client.gui.button.PaginationButton;
import xt9.deepmoblearning.common.energy.DeepEnergyStorage;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;
import xt9.deepmoblearning.common.network.ExtractionChamberChangePageMessage;
import xt9.deepmoblearning.common.network.ExtractorSetSelectedItemMessage;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.util.Color;
import xt9.deepmoblearning.common.util.MathHelper;
import xt9.deepmoblearning.common.util.Pagination;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by xt9 on 2017-06-17.
 */
public class ExtractionChamberGui extends GuiContainer {
    private static final ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/extraction_chamber_base.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepConstants.MODID, "textures/gui/default_gui.png");
    private static final int WIDTH =  176;
    private static final int HEIGHT = 178;
    private FontRenderer renderer;

    private TileEntityExtractionChamber tile;
    private DeepEnergyStorage energyStorage;
    private PaginationButton leftButton;
    private PaginationButton rightButton;
    private ClickableZoneButton deselectItemButton;
    private int currentPage;
    private ItemStack currentInput;

    public ExtractionChamberGui(TileEntityExtractionChamber te, InventoryPlayer inventory, World world) {
        super(new ContainerExtractionChamber(te, inventory, world));
        this.tile = te;
        this.energyStorage = (DeepEnergyStorage) te.getCapability(CapabilityEnergy.ENERGY, null);
        this.renderer = Minecraft.getMinecraft().fontRenderer;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();
        initSelectButtons();
        initPaginationButtons();
        deselectItemButton = new ClickableZoneButton(1339, getGuiLeft() + 79, getGuiTop() + 4, 16, 16, this.width, this.height);
        currentPage = tile.pageHandler.getCurrentPageIndex();
        currentInput = tile.getPristine();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        // What to do if item changes
        if(tile.pristineChanged() || tile.getPristine().isEmpty() || currentPage != tile.pageHandler.getCurrentPageIndex() || !ItemStack.areItemsEqual(currentInput, tile.getPristine())) {
            currentPage = tile.pageHandler.getCurrentPageIndex();
            currentInput = tile.getPristine();
            buttonList.clear();
            initSelectButtons();
        }
        initPaginationButtons();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        drawLootList();
        drawButtonHoverText();
        drawSelectedItem();

        int x = mouseX - guiLeft;
        int y = mouseY - guiTop;

        NumberFormat f = NumberFormat.getNumberInstance(Locale.ENGLISH);
        List<String> tooltip = new ArrayList<>();

        if(10 <= y && y < 63) {
            if(6 <= x && x < 13) {
                // Tooltip for energy
                tooltip.add(f.format(energyStorage.getEnergyStored()) + "/" + f.format(energyStorage.getMaxEnergyStored()) + " RF");
                tooltip.add("Operational cost: " + f.format(tile.energyCost) + " RF/t");
                drawHoveringText(tooltip, x - 16, y - 16);
            }
        }

        if(!tile.resultingItem.isEmpty()) {
            List<String> deselectTooltip = new ArrayList<>();
            deselectTooltip.add("Click to remove autocraft");
            deselectItemButton.setTooltip(deselectTooltip);
        }
    }

    private void drawButtonHoverText() {
        for (GuiButton aButtonList : buttonList) {
            if (aButtonList instanceof ItemSelectButton) {
                ItemSelectButton btn = (ItemSelectButton) aButtonList;
                if (btn.isMouseOver()) {
                    renderer.drawString(btn.stack.getDisplayName(), 2, -10, Color.WHITE);
                }
            }
        }
    }

    private void drawSelectedItem() {
        drawItemStackWithCount(79, 4, tile.resultingItem, tile.resultingItem.getCount());
    }

    private void drawLootList() {
        Map<Integer, ItemStack> pageList = getPaginatedItems();

        int i = 0;
        int rowIndex = 0;
        Iterator it = pageList.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ItemStack stack = (ItemStack) pair.getValue();

            if(i <= 2) { drawItemStackWithCount(17 + (rowIndex * 19), 9, stack, stack.getCount()); }
            else if(i <= 5)  { drawItemStackWithCount(17 + (rowIndex * 19), 28, stack, stack.getCount()); }
            else if(i <= 8) { drawItemStackWithCount(17 + (rowIndex * 19), 47, stack, stack.getCount()); }

            i++;
            // Reset rowindex after 3 rendered items
            rowIndex = rowIndex == 2 ? 0 : rowIndex + 1;
            it.remove();
        }
    }

    private void initSelectButtons() {
        Map<Integer, ItemStack> pageList = getPaginatedItems();
        int left = getGuiLeft();
        int top = getGuiTop();

        int i = 0;
        int rowIndex = 0;
        Iterator it = pageList.entrySet().iterator();
        boolean foundSelected = false;

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            ItemStack loot = (ItemStack) pair.getValue();
            boolean selected = !foundSelected && ItemStack.areItemsEqual(loot, tile.resultingItem);
            if(selected) {
                foundSelected = true;
            }

            if(i <= 2) { buttonList.add(new ItemSelectButton(i + (9 * tile.pageHandler.getCurrentPageIndex()), left + 16 + (rowIndex * 19), top + 8, selected, loot)); }
            else if(i <= 5)  { buttonList.add(new ItemSelectButton(i + (9 * tile.pageHandler.getCurrentPageIndex()), left + 16 + (rowIndex * 19), top + 27, selected, loot)); }
            else if(i <= 8) { buttonList.add(new ItemSelectButton(i + (9 * tile.pageHandler.getCurrentPageIndex()), left + 16 + (rowIndex * 19), top + 46, selected, loot)); }

            i++;
            // Reset rowindex after 3 rendered items
            rowIndex = rowIndex == 2 ? 0 : rowIndex + 1;
            it.remove();
        }
    }

    private void initPaginationButtons() {
        int left = getGuiLeft();
        int top = getGuiTop();
        Pagination p = tile.pageHandler;
        leftButton = new PaginationButton(1337, left + 13, top + 66, PaginationButton.LEFT, p, p.getCurrentPageIndex() == p.getFirstPageIndex());
        rightButton = new PaginationButton(1338, left + 44, top + 66, PaginationButton.RIGHT, p,p.getCurrentPageIndex() == p.getLastPageIndex());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (leftButton.mousePressed(mc, mouseX, mouseY)) {
            actionPerformed(leftButton);
        } else if(rightButton.mousePressed(mc, mouseX, mouseY)) {
            actionPerformed(rightButton);
        } else if(deselectItemButton.mousePressed(mc, mouseX, mouseY)) {
            actionPerformed(deselectItemButton);
        }
    }

    @Override
    protected void actionPerformed(GuiButton pressedButton) throws IOException {
        if(pressedButton instanceof ItemSelectButton) {
            ItemSelectButton btn = (ItemSelectButton) pressedButton;
            // Select or deselect the clicked button
            if(btn.isSelected()) {
                btn.setSelected(false);
                DeepMobLearning.network.sendToServer(new ExtractorSetSelectedItemMessage(-1));
            } else {
                // Deselect all Selectable buttons
                clearSelectedItems();
                btn.setSelected(true);
                DeepMobLearning.network.sendToServer(new ExtractorSetSelectedItemMessage(pressedButton.id));
            }
        }

        if(pressedButton instanceof PaginationButton) {
            if(((PaginationButton) pressedButton).isLeftArrow()) {
                DeepMobLearning.network.sendToServer(new ExtractionChamberChangePageMessage("decrease"));
            } else {
                DeepMobLearning.network.sendToServer(new ExtractionChamberChangePageMessage("increase"));
            }
            buttonList.clear();
            initSelectButtons();
            initPaginationButtons();
        }

        if(pressedButton.id == deselectItemButton.id) {
            clearSelectedItems();
            DeepMobLearning.network.sendToServer(new ExtractorSetSelectedItemMessage(-1));
        }
    }

    private void clearSelectedItems() {
        for(GuiButton b : buttonList) {
            if(b instanceof ItemSelectButton) {
                ((ItemSelectButton) b).setSelected(false);
            }
        }
    }


    private Map<Integer, ItemStack> getPaginatedItems() {
        Map<Integer, ItemStack> list = new TreeMap<>();

        int count = 0;
        for (int i = tile.pageHandler.getCurrentPageIndex() * 9; i < tile.getLootFromPristine().size(); i++) {
            if(count < 9) {
                list.put(i, tile.getItemFromList(i));
            }
            count++;
        }

        return list;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int left = getGuiLeft();
        int top = getGuiTop();
        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left, top, 0, 0, 176, 83);

        // Draw crafting progress
        int craftingBarHeight = (int) (((float) tile.percentDone / 50 * 36));
        int craftingBarOffset = 36 - craftingBarHeight;
        drawTexturedModalRect(left + 84,  top + 22 + craftingBarOffset, 7, 83, 6, craftingBarHeight);

        // Draw current energy
        int energyBarHeight = MathHelper.ensureRange((int) ((float) energyStorage.getEnergyStored() / (energyStorage.getMaxEnergyStored() - tile.energyCost) * 53), 0, 53);
        int energyBarOffset = 53 - energyBarHeight;
        drawTexturedModalRect(left + 6,  top + 10 + energyBarOffset, 0, 83, 7, energyBarHeight);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left, top + 88, 0, 0, 176, 90);

        if(!tile.resultingItem.isEmpty()) {
            deselectItemButton.drawButton(mc, mouseX, mouseY, mc.getRenderPartialTicks());
        }
    }

    /* Needed on 1.12 to render tooltips */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawPaginationButtons(mouseX, mouseY);
        renderHoveredToolTip(mouseX, mouseY);

    }

    private void drawPaginationButtons(int mouseX, int mouseY) {
        leftButton.drawButton(mc, mouseX, mouseY, 0);
        rightButton.drawButton(mc, mouseX, mouseY, 0);
    }

    private void drawItemStackWithCount(int x, int y, ItemStack stack, int count) {
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(fontRenderer, stack, x - 1, y - 1, count != 1 ? count + ""  : "");
    }
}
