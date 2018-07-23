package xt9.deepmoblearning.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.util.Pagination;

/**
 * Created by xt9 on 2018-01-10.
 */
public class PaginationButton extends GuiButton {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DeepConstants.MODID,"textures/gui/buttons/button_paginate.png");
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public Pagination handler;
    private int direction;

    public PaginationButton(int buttonId, int x, int y, int direction, Pagination handler, boolean disable) {
        super(buttonId, x, y, 31, 12, "");
        this.direction = direction;
        this.handler = handler;
        if(disable) {
            visible = false;
            enabled = false;
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        if (mouseOver && (direction == LEFT && handler.getFirstPageIndex() != handler.getCurrentPageIndex())){
            return 1;
        } else if(mouseOver && (direction == RIGHT &&  handler.getLastPageIndex() != handler.getCurrentPageIndex())) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            mc.getTextureManager().bindTexture(TEXTURE);
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int i = getHoverState(hovered);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            drawTexturedModalRect(x, y, i * width, direction * height, width, height);
            mouseDragged(mc, mouseX, mouseY);
        }

    }

    public boolean isLeftArrow() {
        return direction == LEFT;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        //
    }
}
