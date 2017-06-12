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
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.api.mobs.*;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.util.MetaUtil;
import xt9.deepmoblearning.common.util.NBTHelper;

import java.io.IOException;

/**
 * Created by xt9 on 2017-06-08.
 */
public class GuiDeepLearner extends GuiContainer {
    public static final int WIDTH =  338;
    public static final int HEIGHT = 235;

    private FontRenderer renderer;
    private MobMetaData meta;
    private World world;
    private NonNullList<ItemStack> validModelChips;
    private int currentItem = 0;

    private static final ResourceLocation base = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/deeplearner_base.png");
    private static final ResourceLocation extras = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/deeplearner_extras.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/default_gui.png");

    public GuiDeepLearner(InventoryPlayer inventory, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        super(new ContainerDeepLearner(inventory, world, slot, heldItem));
        this.world = world;
        this.renderer = Minecraft.getMinecraft().fontRendererObj;
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
        drawTexturedModalRect(left + 41, top, 0, 0, 256, 140);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 81, top + 145, 0, 0, 176, 90);

        // Get the meta for the first mobchip in this container, only loop the internal stacks and not the whole player inventory
        NonNullList<ItemStack> list = ((ContainerDeepLearner) this.inventorySlots).getInternalItemStacks();
        this.validModelChips = ItemMobChip.getValidFromList(list);

        // Render cycle buttons if we have multiple models (atleast 2).
        if(this.validModelChips.size() > 1) {
            this.renderCycleButtons(left, top, mouseX, mouseY);
        }

        if(this.currentItem >= this.validModelChips.size()) {
            this.currentItem = this.previousItemIndex();
        }

        // If we have atleast 1 valid mob chip
        if(this.validModelChips.size() >= 1 && this.currentItem < this.validModelChips.size()) {
            this.meta = MetaUtil.getMetaFromItemStack(this.validModelChips.get(this.currentItem));

            this.renderMetaDataText(meta, left, top, this.validModelChips.get(this.currentItem));
            this.renderMobDisplayBox(left, top);
            this.renderEntity(meta.getEntity(), meta.getInterfaceScale(), left + meta.getInterfaceOffsetX(), top + 80 + meta.getInterfaceOffsetY(), partialTicks);
            if(meta instanceof ZombieMeta || meta instanceof SpiderMeta) {
                this.renderEntity(meta.getExtraEntity(), meta.getInterfaceScale(), left + meta.getExtraInterfaceOffsetX(), top + 80 + meta.getExtraInterfaceOffsetY(), partialTicks);
            }
        } else {
            this.renderDefaultScreen(left, top);
        }

    }

    private int nextItemIndex() {
        int result;

        // If last in list go back to start of list
        if(this.currentItem == this.validModelChips.size() - 1) {
            result = 0;
        } else {
            result = this.currentItem + 1;
        }

        return result;
    }

    private int previousItemIndex() {
        int result;
        // If first in list
        if(this.currentItem == 0) {
            if(this.validModelChips.size() > 1) {
                result = this.validModelChips.size() - 1;
            } else {
                result = 0;
            }
        } else {
            result = this.currentItem - 1;
        }

        return result;
    }

    private void renderCycleButtons(int left, int top, int mouseX, int mouseY) {
        // Draw the mob display box
        Minecraft.getMinecraft().getTextureManager().bindTexture(extras);
        drawTexturedModalRect( left - 27, top + 105, 75, 0, 24, 24);
        drawTexturedModalRect( left - 1, top + 105, 99, 0, 24, 24);

        // Hover states
        if(mouseX >= 124 && mouseX < 148 && 160 <= mouseY && mouseY < 184)  {
            drawTexturedModalRect( left - 27, top + 105, 75, 24, 24, 24);
        } else if(mouseX >= 152 && mouseX < 176 && 160 <= mouseY && mouseY < 184) {
            drawTexturedModalRect( left - 1, top + 105, 99, 24, 24, 24);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(this.validModelChips.size() > 1) {
            if (mouseX >= 124 && mouseX < 176 && 160 <= mouseY && mouseY < 184) {

                if (124 <= mouseX && mouseX < 148) {
                    this.currentItem = this.previousItemIndex();
                } else if (152 <= mouseX && mouseX < 176) {
                    this.currentItem = this.nextItemIndex();
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void renderDefaultScreen(int left, int top) {
        int leftStart = left + 49;
        int spacing = 12;

        drawString(this.renderer, "No Data Model Found", leftStart, top + spacing, 6478079);
        drawString(this.renderer,  "Please insert a Data Model!", leftStart, top + (spacing * 2), 16777215);
        drawString(this.renderer,  "Your data models will only gain knowledge", leftStart, top + top + (spacing * 3), 16777215);
        drawString(this.renderer,  "when they are placed in the deep learner.", leftStart, top + (spacing * 4), 16777215);

        drawString(this.renderer,  "In order to gain knowledge, you must", leftStart, top + (spacing * 6), 16777215);
        drawString(this.renderer,  "deliver the killing blow.", leftStart, top + (spacing * 7), 16777215);
    }

    private void renderMetaDataText(MobMetaData meta, int left, int top, ItemStack stack) {
        int leftStart = left + 49;
        int topStart = top - 4;
        int spacing = 12;

        drawString(this.renderer, "Name", leftStart, topStart + spacing, 6478079);
        drawString(this.renderer,  meta.getMobName(), leftStart, topStart + (spacing *  2), 16777215);

        drawString(this.renderer, "Information", leftStart, topStart + (spacing *  3), 6478079);
        String mobTrivia[] = meta.getMobTrivia();
        for (int i = 0; i < mobTrivia.length; i++) {
            drawString(this.renderer, mobTrivia[i], leftStart, topStart + (spacing * 3) + ((i + 1) * 12), 16777215);
        }

        drawString(this.renderer, "Real fights completed: " + NBTHelper.getInt(stack, "mobsKilled", 0), leftStart, topStart + (spacing * 8), 16777215);
        drawString(this.renderer, "Fights simulated: " + NBTHelper.getInt(stack, "simulatedFights", 0), leftStart, topStart + (spacing * 9), 16777215);
        drawString(this.renderer, "Current tier: " + NBTHelper.getInt(stack, "tier", 0), leftStart, topStart + (spacing * 10), 16777215);

        // Draw heart
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        drawTexturedModalRect(left + 235, topStart + (spacing * 2) - 2, 0, 140, 9, 9);

        drawString(renderer, "Life points", left + 235, topStart + spacing, 6478079);
        drawString(renderer, "" + meta.getNumberOfHearts(), left + 246, topStart + (spacing * 2) - 1, 16777215);
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
        double heightOffset = Math.sin((this.world.getTotalWorldTime() + partialTicks) / 16.0) / 8.0;

        // Make sure the Z axis is high so it does not clip behind the backdrop or inventory
        GlStateManager.translate(0.2f, 0.0f + heightOffset, 15.0f);
        GlStateManager.rotate((this.world.getTotalWorldTime() + partialTicks) * 3.0f, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity,0.0f, 0.0f, 0.0f, 1.0f, 0, true);

        GlStateManager.popMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }
}
