package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.SpiderMeta;
import xt9.deepmoblearning.common.mobmetas.ZombieMeta;
import xt9.deepmoblearning.common.util.Color;
import xt9.deepmoblearning.common.util.DataModel;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by xt9 on 2017-06-08.
 */
public class DeepLearnerGui extends GuiContainer {
    public static final int WIDTH =  338;
    public static final int HEIGHT = 235;
    private FontRenderer renderer;
    protected ItemStack deepLearner;
    private MobMetaData meta;
    private World world;
    private NonNullList<ItemStack> validDataModels;
    private int currentItem = 0;

    private static final ResourceLocation base = new ResourceLocation(DeepConstants.MODID, "textures/gui/deeplearner_base.png");
    private static final ResourceLocation extras = new ResourceLocation(DeepConstants.MODID, "textures/gui/deeplearner_extras.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepConstants.MODID, "textures/gui/default_gui.png");

    public DeepLearnerGui(InventoryPlayer inventory, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        super(new ContainerDeepLearner(inventory, world, slot, heldItem));
        this.world = world;
        this.renderer = Minecraft.getMinecraft().fontRenderer;
        this.deepLearner = heldItem;
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
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int left = getGuiLeft();
        int top = getGuiTop();

        // Draw the main GUI
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(left + 41, top, 0, 0, 256, 140);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 81, top + 145, 0, 0, 176, 90);

        // Get the meta for the first ItemDataModel in this deeplearner
        NonNullList<ItemStack> list = ItemDeepLearner.getContainedItems(deepLearner);
        this.validDataModels = DataModel.getValidFromList(list);

        // Render cycle buttons if we have multiple models (atleast 2).
        if(validDataModels.size() > 1) {
            renderCycleButtons(left, top, mouseX, mouseY);
        }

        if(currentItem >= validDataModels.size()) {
            this.currentItem = previousItemIndex();
        }

        // If we have at least 1 valid data model
        if(validDataModels.size() >= 1 && currentItem < validDataModels.size()) {
            this.meta = DataModel.getMobMetaData(validDataModels.get(currentItem));

            renderMetaDataText(meta, left, top, validDataModels.get(currentItem));
            renderMobDisplayBox(left, top);
            renderEntity(meta.getEntity(world), meta.getInterfaceScale(), left + meta.getInterfaceOffsetX(), top + 80 + meta.getInterfaceOffsetY(), partialTicks);
            if(meta instanceof ZombieMeta || meta instanceof SpiderMeta) {
                renderEntity(meta.getExtraEntity(world), meta.getInterfaceScale(), left + meta.getExtraInterfaceOffsetX(), top + 80 + meta.getExtraInterfaceOffsetY(), partialTicks);
            }
        } else {
            renderDefaultScreen(left, top);
        }

    }

    private int nextItemIndex() {
        int result;

        // If last in list go back to start of list
        if(currentItem == validDataModels.size() - 1) {
            result = 0;
        } else {
            result = currentItem + 1;
        }

        return result;
    }

    private int previousItemIndex() {
        int result;
        // If first in list
        if(currentItem == 0) {
            if(validDataModels.size() > 1) {
                result = validDataModels.size() - 1;
            } else {
                result = 0;
            }
        } else {
            result = currentItem - 1;
        }

        return result;
    }

    private void renderCycleButtons(int left, int top, int mouseX, int mouseY) {
        int x = mouseX - guiLeft;
        int y = mouseY - guiTop;

        // Draw the mob display box
        Minecraft.getMinecraft().getTextureManager().bindTexture(extras);
        drawTexturedModalRect( left - 27, top + 105, 75, 0, 24, 24);
        drawTexturedModalRect( left - 1, top + 105, 99, 0, 24, 24);

        // Hover states
        if(x >= -27 && x < -3 && 105 <= y && y < 129) {
            drawTexturedModalRect( left - 27, top + 105, 75, 24, 24, 24);
        } else if(x >= -2 && x < 23 && 105 <= y && y < 129) {
            drawTexturedModalRect( left - 1, top + 105, 99, 24, 24, 24);
        }
    }

    @Override
    protected void mouseClicked(int mX, int mY, int mouseButton) throws IOException {
        int x = mX - guiLeft;
        int y = mY - guiTop;

        if(validDataModels.size() > 1) {
            if (x >= -27 && x < 23 && 105 <= y && y < 129) {

                if (-27 <= x && x < -3) {
                    this.currentItem = previousItemIndex();
                } else if (-2 <= x && x < 23) {
                    this.currentItem = nextItemIndex();
                }
            }
        }
        super.mouseClicked(mX, mY, mouseButton);
    }

    private void renderDefaultScreen(int left, int top) {
        int leftStart = left + 49;
        int spacing = 12;

        drawString(renderer, "No Data Model Found", leftStart, top + spacing, Color.AQUA);
        drawString(renderer,  "Please insert a Data Model!", leftStart, top + (spacing * 2), Color.WHITE);
        drawString(renderer,  "Your data models will collect data", leftStart, top + (spacing * 3), Color.WHITE);
        drawString(renderer,  "when they are placed in the deep learner.", leftStart, top + (spacing * 4), Color.WHITE);

        drawString(renderer,  "In order to collect data, you must", leftStart, top + (spacing * 6), Color.WHITE);
        drawString(renderer,  "deliver the killing blow.", leftStart, top + (spacing * 7), Color.WHITE);
    }

    private void renderMetaDataText(MobMetaData meta, int left, int top, ItemStack stack) {
        DecimalFormat f = new DecimalFormat("0.#");
        int leftStart = left + 49;
        int topStart = top - 4;
        int spacing = 12;

        drawString(renderer, "Name", leftStart, topStart + spacing, Color.AQUA);
        drawString(renderer,  "The " + meta.getName(), leftStart, topStart + (spacing *  2), Color.WHITE);

        drawString(renderer, "Information", leftStart, topStart + (spacing *  3), Color.AQUA);
        String mobTrivia[] = meta.getMobTrivia();
        for (int i = 0; i < mobTrivia.length; i++) {
            drawString(renderer, mobTrivia[i], leftStart, topStart + (spacing * 3) + ((i + 1) * 12), Color.WHITE);
        }

        String dataModelTier = DataModel.getTierName(stack, false);
        String nextTier = DataModel.getTierName(stack, true);
        String pluralMobName = DataModel.getMobMetaData(stack).getPluralName();

        int totalKills = DataModel.getTotalKillCount(stack);
        double killsToNextTier = DataModel.getKillsToNextTier(stack);

        drawString(renderer, "Model Tier: " + dataModelTier, leftStart, topStart + (spacing * 8), Color.WHITE);
        drawString(renderer, pluralMobName + " defeated: " + totalKills, leftStart, topStart + (spacing * 9), Color.WHITE);


        if(DataModel.getTier(stack) != DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            drawString(renderer, "Defeat " + f.format(killsToNextTier) + " more to reach " + nextTier, leftStart, topStart + (spacing * 10), Color.WHITE);
        } else {
            drawString(renderer, "Maximum tier achieved", leftStart, topStart + (spacing * 10), Color.WHITE);
        }


        // Draw heart
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        drawTexturedModalRect(left + 235, topStart + (spacing * 2) - 2, 0, 140, 9, 9);

        drawString(renderer, "Life points", left + 235, topStart + spacing, Color.AQUA);

        int numOfHearts = meta.getNumberOfHearts();
        if(numOfHearts == 0) {
            // Obfuscate if hears is 0, use for models with multiple mobmetas
            drawString(renderer, "§k10§r", left + 246, topStart + (spacing * 2) - 1, Color.WHITE);
        } else {
            drawString(renderer, "" + meta.getNumberOfHearts(), left + 246, topStart + (spacing * 2) - 1, Color.WHITE);
        }
    }

    private void renderMobDisplayBox(int left, int top) {
        // Draw the mob display box
        Minecraft.getMinecraft().getTextureManager().bindTexture(extras);
        drawTexturedModalRect( left -41, top, 0, 0, 75, 101);
    }

    private void renderEntity(Entity entity, float scale, float x, float y, float partialTicks) {
        // Disable the lightmap
        Minecraft.getMinecraft().entityRenderer.disableLightmap();

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        double heightOffset = Math.sin((world.getTotalWorldTime() + partialTicks) / 16.0) / 8.0;

        // Make sure the Z axis is high so it does not clip behind the backdrop or inventory
        GlStateManager.translate(0.2f, 0.0f + heightOffset, 15.0f);
        GlStateManager.rotate((world.getTotalWorldTime() + partialTicks) * 3.0f, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entity,0.0f, 0.0f, 0.0f, 1.0f, 0, true);

        GlStateManager.popMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }
}
