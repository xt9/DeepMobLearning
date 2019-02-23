package xt9.deepmoblearning.client.gui.button;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by xt9 on 2018-05-05.
 */
public class TransparentButton extends GuiButton {
    public TransparentButton(int buttonId, int x, int y, int widthIn, int heightIn) {
        super(buttonId, x, y, widthIn, heightIn, "");
    }

    @Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
