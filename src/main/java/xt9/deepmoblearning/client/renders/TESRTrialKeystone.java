package xt9.deepmoblearning.client.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

/**
 * Created by xt9 on 2018-04-04.
 */
public class TESRTrialKeystone extends TileEntitySpecialRenderer<TileEntityTrialKeystone> {

    @Override
    public void render(TileEntityTrialKeystone te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.trialKey.getStackInSlot(0);
        if (!stack.isEmpty()) {
            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            double offset = Math.sin((te.getWorld().getTotalWorldTime() + partialTicks) / 16) / 16.0;
            GlStateManager.translate(x + 0.5, y + 0.7 + offset, z + 0.5);
            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            Minecraft.getMinecraft().entityRenderer.enableLightmap();
        }
    }
}
