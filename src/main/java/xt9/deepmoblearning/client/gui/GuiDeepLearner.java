package xt9.deepmoblearning.client.gui;

import jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.api.mobs.*;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.inventory.DeepLearnerSlot;
import xt9.deepmoblearning.common.util.MetaUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2017-06-08.
 */
public class GuiDeepLearner extends GuiContainer {
    public static final int WIDTH =  600;
    public static final int HEIGHT = 189;
    private MobMetaData meta;
    private World world;

    private static final ResourceLocation base = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/deeplearner_base.png");
    private static final ResourceLocation extras = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/deeplearner_extras.png");
    private static final ResourceLocation defaultGui = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/default_gui.png");

    public GuiDeepLearner(InventoryPlayer inventory, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        super(new ContainerDeepLearner(inventory, world, slot, heldItem));
        this.world = world;
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
        drawTexturedModalRect(left + 172, top - 20, 0, 0, 256, 140);

        // Draw player inventory
        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultGui);
        drawTexturedModalRect(left + 212, top + 136, 0, 0, 176, 90);


        this.meta = MetaUtil.getMetaFromItemStackList(((ContainerDeepLearner) this.inventorySlots).getInternalItemStacks());

        if(!(this.meta instanceof EmptyMeta)) {
            // Todo render Switch tab button
            renderMetaData(meta, left, top, 8, 162, 3);
            renderEntity(meta.getEntity(), meta.getInterfaceScale(), left + 130 + meta.getInterfaceOffsetX(), top + 60 + meta.getInterfaceOffsetY(), partialTicks);
            if(meta instanceof ZombieMeta) {
                renderEntity(meta.getChildEntity(), meta.getInterfaceScale(), left + 130 + meta.getChildInterfaceOffsetX(), top + 60 + meta.getChildInterfaceOffsetY(), partialTicks);
            }
        } else {
            // Todo render "please insert text"
        }

    }

    private void renderMetaData(MobMetaData meta, int left, int top, int fightsCompleted, int fightsSimulated, int chipTier) {
        FontRenderer renderer = this.mc.fontRendererObj;
        int leftStart = left + 180;
        int spacing = 12;

        drawString(renderer, "Name", leftStart, top - spacing, 6478079);
        drawString(renderer,  meta.getMobName(), leftStart, top, 16777215);

        drawString(renderer, "Information", leftStart, top + spacing, 6478079);
        String mobTrivia[] = meta.getMobTrivia();
        for (int i = 0; i < mobTrivia.length; i++) {
            drawString(renderer, mobTrivia[i], leftStart, top + spacing + ((i + 1) * 12), 16777215);
        }

        drawString(renderer, "Realistic fights completed: " + fightsCompleted, leftStart, top + (spacing * 5), 16777215);
        drawString(renderer, "Fights simulated: " + fightsSimulated, leftStart, top + (spacing * 6), 16777215);
        drawString(renderer, "Current tier: " + chipTier, leftStart, top + (spacing * 7), 16777215);

        // Draw the mob display box
        Minecraft.getMinecraft().getTextureManager().bindTexture(extras);
        drawTexturedModalRect( left + 90, top - 20, 0, 0, 75, 101);

        // Draw heart
        Minecraft.getMinecraft().getTextureManager().bindTexture(base);
        drawTexturedModalRect(left + 100, top + 95, 0, 140, 9, 9);

        drawString(renderer, "Life points", left + 104, top + 84, 6478079);
        drawString(renderer, "" + meta.getNumberOfHearts(), left + 110, top + 96, 16777215);
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
        GlStateManager.translate(0.2f, 0.0f + heightOffset, 10.0f);
        GlStateManager.rotate((this.world.getTotalWorldTime() + partialTicks) * 3.0f, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity,0.0, 0.0, 0.0, 1.0f, 0, true);

        GlStateManager.popMatrix();
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }
}
