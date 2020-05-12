package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.util.Color;


/**
 * Created by xt9 on 2018-04-08.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class TrialOverlay extends GuiScreen {
    private FontRenderer fontRender;
    private Minecraft minecraft;
    private long lastTick = 0;

    private static String lastMessage = "";
    private static int ticksToRender = 0;
    private static int ticksToRenderGlitchNotification = 0;
    private static int ticksToRenderNextWaveMessage = 0;
    private static PlayerTrial clientCapability;

    private static final ResourceLocation texture = new ResourceLocation(DeepConstants.MODID, "textures/gui/trial_overlay_gui.png");


    public TrialOverlay(Minecraft mc) {
        super();
        minecraft = mc;
        fontRender = mc.fontRenderer;
        this.itemRender = minecraft.getRenderItem();
        setGuiSize(89, 12);
    }

    public static void initPlayerCapability() {
        clientCapability = (PlayerTrial) DeepMobLearning.proxy.getClientPlayerTrialCapability();
    }

    public static void handleMessage(String type) {
        clientCapability = (PlayerTrial) DeepMobLearning.proxy.getClientPlayerTrialCapability();

        switch (type) {
            case "GlitchNotification": ticksToRenderGlitchNotification = 80; break;
            case "TrialAborted": ticksToRender = 80; lastMessage = "Trial aborted due to unknown reasons"; break;
            case "TrialCompleted": ticksToRender = 120; lastMessage = "Good job! Collect your reward from the keystone"; break;
            case "WaveNumber": ticksToRender = 80; lastMessage = "Wave " + (clientCapability.getCurrentWave() + 1); break;
            // Subtract 2 from ticks so it does not overlap with WaveNumber
            case "WaveCountdown": ticksToRenderNextWaveMessage = 118; break;
        }
    }

    /* Needed on 1.12 to render tooltips */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        doTickChecks();

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        } else if(!minecraft.inGameHasFocus) {
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
            clientCapability = (PlayerTrial) DeepMobLearning.proxy.getClientPlayerTrialCapability();
        }

        if(!clientCapability.isTrialActive()) {
            return;
        }

        renderTrialScoreboard();
    }

    private void doTickChecks() {
        if(lastTick != minecraft.world.getTotalWorldTime()) {
            lastTick = minecraft.world.getTotalWorldTime();

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
        int string1Width = fontRender.getStringWidth(notif1);

        // Draw the Glitch faces
        minecraft.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(getScreenCenterX() - string1Width - 22, 108, 0, 0, 17, 17);
        drawTexturedModalRect(getScreenCenterX() + string1Width + 4, 108, 0, 0, 17, 17);

        renderScaledStringWithColor(scale1, x1, 110, notif1, Color.BRIGHT_PURPLE);
        renderScaledString(scale2, x2, 130, notif2);
    }

    private void renderTrialScoreboard() {
        int x = getRightCornerX() - 50;
        float scale = 1.3F;
        int scaledX = (int) (x / scale);
        int y = 145;

        drawItemStackWithCount(x - 21, y + 4, new ItemStack(Items.CLOCK));
        renderScaledStringWithColor(scale, scaledX, y + 2, "Wave", 	Color.BRIGHT_LIME);
        drawString(fontRender, (clientCapability.getCurrentWave() + 1) + "/" + clientCapability.getLastWave(), x + 1, y + 16, Color.WHITE);

        ItemStack skull = new ItemStack(Items.SKULL);
        skull.setItemDamage(0);

        drawItemStackWithCount(x - 21, y + 36, skull);
        renderScaledStringWithColor(scale, scaledX, y + 32, "Opponents", Color.BRIGHT_LIME);

        int toKill = clientCapability.getWaveMobTotal() - clientCapability.getDefated();
        if(toKill <= 0) {
            drawString(fontRender, "Wave cleared", x + 1, y + 46, Color.WHITE);
        } else {
            drawString(fontRender, toKill + " to go", x + 1, y + 46, Color.WHITE);
        }
    }


    private void renderScaledStringWithColor(float scale, int x, int y, String text, int color) {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        drawString(fontRender, text, x, (int) (y / scale), color);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    private void renderScaledString(float scale, int x, int y, String text) {
        renderScaledStringWithColor(scale, x, y, text, Color.WHITE);
    }

    private int getHalfLineWidth(String text, float glScale) {
        return (int) (((fontRender.getStringWidth(text) / 2) * glScale) / glScale);
    }

    private int getScreenCenterX() {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return scaledResolution.getScaledWidth() / 2;
    }

    private int getScreenCenterX(float glScale) {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return (int) ((scaledResolution.getScaledWidth() / 2) / glScale);
    }

    private int getLeftCornerX() {
        return 5;
    }

    private int getRightCornerX() {
        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        return scaledResolution.getScaledWidth() - width - 5;
    }

    private void drawItemStackWithCount(int x, int y, ItemStack stack) {
        RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(fontRender, stack, x - 1, y - 1, "");
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;

        RenderHelper.disableStandardItemLighting();
    }

}
