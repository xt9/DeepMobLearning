package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.util.PlayerHelper;

/**
 * Created by xt9 on 2017-06-14.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class GuiExperienceBar extends GuiScreen {
    private final FontRenderer renderer;
    private Minecraft mc;
    private ItemStack deepLearner;
    private NonNullList<ItemStack> chipStackList;
    private PlayerHelper playerH;

    private static final ResourceLocation experienceBar = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/experience_gui.png");

    public GuiExperienceBar(Minecraft mc) {
        super();
        this.mc = mc;
        this.renderer = this.mc.fontRendererObj;
        this.itemRender = this.mc.getRenderItem();
        this.setGuiSize(89, 12);
    }

    @SubscribeEvent(priority=EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if(!this.mc.inGameHasFocus) {
            return;
        }

        this.playerH = new PlayerHelper(this.mc.player);
        if(!playerH.isHoldingDeepLearner()) {
            return;
        } else {
            this.deepLearner = playerH.getHeldDeepLearner();
            this.chipStackList = ItemMobChip.getValidFromList(ItemDeepLearner.getContainedItems(this.deepLearner));
        }

        // Todo Option for left/right gui or disable it entirely
        int x = getRightCornerX();
        int y = 5;

        for (int i = 0; i < this.chipStackList.size(); i++) {
            this.drawExperienceBar(x, y, i, this.chipStackList.get(i));
        }
    }

    private void drawExperienceBar(int x, int y, int index, ItemStack chip) {
        int componentHeight = 26;
        int spacing = 12;
        this.drawItemStack(x - 16, y - 2 + spacing + (index * componentHeight), chip);
        drawString(this.renderer, ItemMobChip.getTierName(chip) + " Model", x + 3, y + (index * componentHeight) + 2, 16777215);

        // Bar
        mc.getTextureManager().bindTexture(experienceBar);
        this.drawTexturedModalRect(x, y + spacing + (index * componentHeight), 0, 0, 89, 12);

        this.drawTexturedModalRect(x + 1,  y + 1 + spacing + (index * componentHeight), 0, 12,
                (int) (((float) ItemMobChip.getKillCountForCurrentTier(chip) / ItemMobChip.getMaxKillsForCurrentTier(chip)) * 89), 11);
        drawString(this.renderer, ItemMobChip.getKillsToNextTier(chip) + " to go", x + 3, y + 2 + spacing + (index * componentHeight), 16777215);
    }

    private int getLeftCornerX() {
        return 5;
    }

    private int getRightCornerX() {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        return scaledResolution.getScaledWidth() - this.width - 5;
    }

    private void drawItemStack(int x, int y, ItemStack stack)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }
}
