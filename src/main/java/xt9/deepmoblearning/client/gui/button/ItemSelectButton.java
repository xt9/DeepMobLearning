package xt9.deepmoblearning.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2018-01-09.
 */
public class ItemSelectButton extends GuiButton {
    public ItemStack stack;
    private boolean selected;

    protected static final ResourceLocation TEXTURE = new ResourceLocation(DeepConstants.MODID,"textures/gui/buttons/button_select.png");

    public ItemSelectButton(int buttonId, int x, int y, boolean selected, ItemStack stack) {
        super(buttonId, x, y, 18, 18, "");
        this.selected = selected;
        this.stack = stack;
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        if (selected) {
            return 0;
        } else if (mouseOver) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            mc.getTextureManager().bindTexture(TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int i = getHoverState(hovered);

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            drawTexturedModalRect(x, y, i * 18, 0, width, height);
            mouseDragged(mc, mouseX, mouseY);
        }

    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        //
    }
}
