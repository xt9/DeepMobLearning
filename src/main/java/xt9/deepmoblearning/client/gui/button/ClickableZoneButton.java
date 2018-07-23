package xt9.deepmoblearning.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt9 on 2018-07-23.
 */
public class ClickableZoneButton extends GuiButton {
    private List<String> tooltip = new ArrayList<>();
    private int screenWidth;
    private int screenHeight;

    public ClickableZoneButton(int buttonId, int x, int y, int widthIn, int heightIn, int screenWidth, int screenHeight) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setTooltip(List<String> tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        if(hovered) {
            GuiUtils.drawHoveringText(tooltip, mouseX - 18, mouseY - 9, screenWidth, screenHeight, -1, mc.fontRenderer);
        }
    }
}
