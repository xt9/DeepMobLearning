package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import xt9.deepmoblearning.common.gui.ContainerBase;

/**
 * Created by xt9 on 2017-06-08.
 */
public class DeepLearnerGui extends GuiContainer {
    public static World world;

    public DeepLearnerGui(World world) {
        super(new ContainerBase());
        this.world = world;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Wither scale 25
        // Witch scale 40
        // Blaze scale 35
        // Skeleton scale 40
        // Zombie scale 40
        // Ghast scale 12
        // Wither skeleton scale 35

        //EntityWitch entity = new EntityWitch(this.world);
        //EntityBlaze entity = new EntityBlaze(this.world);
        //entity.setOnFire(true);
        EntityWither entity = new EntityWither(this.world);
        // EntitySkeleton entity = new EntitySkeleton(this.world);
        // EntitySpider entity = new EntitySpider(this.world);
        // EntityGhast entity = new EntityGhast(this.world);
        // EntityWitherSkeleton entity = new EntityWitherSkeleton(this.world);

        // EntityZombie entity = new EntityZombie(this.world);
        // EntityZombie entity2 = new EntityZombie(this.world);
        // entity2.setChild(true);

        renderEntity(entity, 28, 0, 0, partialTicks);
        // renderEntity(entity2, 40, 15, 0, partialTicks);
    }


    private void renderEntity(Entity entity, float scale, float xOffset, float yOffset, float partialTicks) {
        int left = getGuiLeft();
        int top = getGuiTop();

        float x = (left + 100) + xOffset;
        float y = (top + 100) + yOffset;

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);

        double heightOffset = Math.sin((this.world.getTotalWorldTime() + partialTicks) / 8.0) / 4.0;
        GlStateManager.translate(0.2f, 0.0f + heightOffset, 0.2f);
        GlStateManager.rotate((this.world.getTotalWorldTime() + partialTicks) * 3.0f, 0.0f, 1.0f, 0.0f);

        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity,0.0, 0.0, 0.0, 1.0f, 0, false);

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}
