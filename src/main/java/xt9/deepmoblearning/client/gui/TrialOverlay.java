package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.util.Color;


/**
 * Created by xt9 on 2018-04-08.
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class TrialOverlay extends GuiScreen {
    private Minecraft minecraft;
    private long lastTick = 0;

    private static String lastMessage = "";
    private static int ticksToRender = 0;
    private static int ticksToRenderGlitchNotification = 0;
    private static int ticksToRenderNextWaveMessage = 0;
    private static PlayerTrial clientCapability;

    private static final ResourceLocation texture = new ResourceLocation(DeepConstants.MODID, "textures/gui/trial_overlay_gui.png");


    public TrialOverlay() {
        super();
        minecraft = Minecraft.getInstance();
    }

    public static void initPlayerCapability() {
        clientCapability = DeepMobLearning.proxy.getTrialCapability(Minecraft.getInstance().player);
    }

    public static void handleMessage(String type) {
        clientCapability = DeepMobLearning.proxy.getTrialCapability(Minecraft.getInstance().player);

        switch (type) {
            case "GlitchNotification": ticksToRenderGlitchNotification = 80; break;
            case "TrialAborted": ticksToRender = 80; lastMessage = "Trial aborted due to unknown reasons"; break;
            case "TrialCompleted": ticksToRender = 120; lastMessage = "Good job! Collect your reward from the keystone"; break;
            case "WaveNumber": ticksToRender = 80; lastMessage = "Wave " + (clientCapability.getCurrentWave() + 1); break;
            // Subtract 2 from ticks so it does not overlap with WaveNumber
            case "WaveCountdown": ticksToRenderNextWaveMessage = 118; break;
        }
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        doTickChecks();

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        } else if(!minecraft.isGameFocused()) {
            return;
        }

        if(ticksToRender > 0) {
            renderMessage();
        }

        if(ticksToRenderGlitchNotification > 0) {
            renderGlitchNotification();
        }

        if(ticksToRenderNextWaveMessage > 0) {
            renderWaveCountdown();
        }

        if(clientCapability == null) {
            clientCapability = DeepMobLearning.proxy.getTrialCapability(Minecraft.getInstance().player);
        }

        if(!clientCapability.isTrialActive()) {
            return;
        }

        renderTrialScoreboard();
    }

    private void doTickChecks() {
        if(lastTick != minecraft.world.getWorldInfo().getGameTime()) {
            lastTick = minecraft.world.getWorldInfo().getGameTime();

            if(ticksToRender > 0) {
                ticksToRender--;
            }

            if(ticksToRenderNextWaveMessage > 0) {
                ticksToRenderNextWaveMessage--;
            }

            if(ticksToRenderGlitchNotification > 0) {
                ticksToRenderGlitchNotification--;
            }
        }
    }

    private void renderWaveCountdown() {
        float scale = 1.6F;
        int x = getScreenCenterX(scale) - getHalfLineWidth("Next wave in: 0", scale);

        if((ticksToRenderNextWaveMessage / 20) > 0) {
            renderScaledString(scale, x, 80, "Next wave in: " + ticksToRenderNextWaveMessage / 20);
        }
    }

    private void renderMessage() {
        float scale = lastMessage.length() < 8 ? 2.2F : 1.6F;
        int x = getScreenCenterX(scale) - getHalfLineWidth(lastMessage, scale);

        renderScaledString(scale, x, 80, lastMessage);
    }

    private void renderGlitchNotification() {
        float scale1 = 1.9F;
        float scale2 = 1.2F;

        String notif1 = "! Glitch Alert !";
        String notif2 = "Deal with it before it becomes a problem";
        int x1 = getScreenCenterX(scale1) - getHalfLineWidth(notif1, scale1);
        int x2 = getScreenCenterX(scale2) - getHalfLineWidth(notif2, scale2);
        int string1Width = minecraft.fontRenderer.getStringWidth(notif1);

        // Draw the Glitch faces
        minecraft.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(getScreenCenterX() - string1Width - 22, 108, 0, 0, 17, 17);
        drawTexturedModalRect(getScreenCenterX() + string1Width + 4, 108, 0, 0, 17, 17);

        renderScaledStringWithColor(scale1, x1, 110, notif1, Color.BRIGHT_PURPLE);
        renderScaledString(scale2, x2, 130, notif2);
    }

    private void renderTrialScoreboard() {
        int x = getRightCornerX() - 100;
        float scale = 1.3F;
        int scaledX = (int) (x / scale);
        int y = 145;

        drawItemStackWithCount(x - 21, y + 4, new ItemStack(Items.CLOCK));
        renderScaledStringWithColor(scale, scaledX, y + 2, "Wave", 	Color.BRIGHT_LIME);
        drawString(minecraft.fontRenderer, (clientCapability.getCurrentWave() + 1) + "/" + clientCapability.getLastWave(), x + 1, y + 16, Color.WHITE);

        ItemStack skull = new ItemStack(Items.SKELETON_SKULL);

        drawItemStackWithCount(x - 21, y + 36, skull);
        renderScaledStringWithColor(scale, scaledX, y + 32, "Opponents", Color.BRIGHT_LIME);

        int toKill = clientCapability.getWaveMobTotal() - clientCapability.getDefated();
        if(toKill== 0) {
            drawString(minecraft.fontRenderer, "Wave cleared", x + 1, y + 46, Color.WHITE);
        } else {
            drawString(minecraft.fontRenderer, toKill + " to go", x + 1, y + 46, Color.WHITE);
        }
    }


    private void renderScaledStringWithColor(float scale, int x, int y, String text, int color) {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        drawString(minecraft.fontRenderer, text, x, (int) (y / scale), color);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderScaledString(float scale, int x, int y, String text) {
        renderScaledStringWithColor(scale, x, y, text, Color.WHITE);
    }

    private int getHalfLineWidth(String text, float glScale) {
        return (int) (((minecraft.fontRenderer.getStringWidth(text) / 2) * glScale) / glScale);
    }

    private int getScreenCenterX() {
        return Minecraft.getInstance().mainWindow.getScaledWidth() / 2;
    }

    private int getScreenCenterX(float glScale) {
        return (int) ((Minecraft.getInstance().mainWindow.getScaledWidth() / 2) / glScale);
    }

    private int getLeftCornerX() {
        return 5;
    }

    private int getRightCornerX() {
        return Minecraft.getInstance().mainWindow.getScaledWidth() - width - 5;
    }

    private void drawItemStackWithCount(int x, int y, ItemStack stack) {
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        DeepMobLearning.proxy.getClientItemRenderer().renderItemAndEffectIntoGUI(stack, x, y);
        DeepMobLearning.proxy.getClientItemRenderer().renderItemOverlayIntoGUI(minecraft.fontRenderer, stack, x - 1, y - 1, "");
        this.zLevel = 0.0F;

        RenderHelper.disableStandardItemLighting();
    }

}
