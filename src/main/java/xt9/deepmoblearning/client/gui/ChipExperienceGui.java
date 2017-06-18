package xt9.deepmoblearning.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.util.PlayerHelper;

import java.text.DecimalFormat;

/**
 * Created by xt9 on 2017-06-14.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ChipExperienceGui extends GuiScreen {
    private final FontRenderer renderer;
    private Minecraft mc;
    private ItemStack deepLearner;
    private NonNullList<ItemStack> chipStackList;
    private PlayerHelper playerH;

    private static final ResourceLocation experienceBar = new ResourceLocation(DeepMobLearning.MODID, "textures/gui/experience_gui.png");

    public ChipExperienceGui(Minecraft mc) {
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
            ItemStack stack = this.chipStackList.get(i);
            String tierName = ItemMobChip.getTierName(stack, false);
            int tier = ItemMobChip.getTier(stack);
            double k = ItemMobChip.getKillsToNextTier(stack);
            double c = ItemMobChip.getCurrentTierKillCountWithSims(stack);
            int roof = ItemMobChip.getTierRoofAsKills(stack);
            this.drawExperienceBar(x, y, i, tierName, tier, k, c, roof, stack);
        }
    }

    private void drawExperienceBar(int x, int y, int index, String tierName, int tier, double killsToNextTier, double currenKillCount, int tierRoof, ItemStack stack) {
        int componentHeight = 26;
        int spacing = 12;
        DecimalFormat f = new DecimalFormat("0.#");

        this.drawItemStack(x - 18, y - 2 + spacing + (index * componentHeight), stack);
        drawString(this.renderer, tierName + " Model", x - 14, y + (index * componentHeight) + 2, 16777215);

        // Draw the bar
        mc.getTextureManager().bindTexture(experienceBar);
        drawTexturedModalRect(x, y + spacing + (index * componentHeight), 0, 0, 89, 12);

        if(tier == DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
            drawTexturedModalRect(x + 1,  y + 1 + spacing + (index * componentHeight), 0, 12, 89, 11);
        } else {
            drawTexturedModalRect(x + 1,  y + 1 + spacing + (index * componentHeight), 0, 12,
                    (int) (((float) currenKillCount / tierRoof * 89)), 11);
            drawString(this.renderer, f.format(killsToNextTier) + " to go", x + 3, y + 2 + spacing + (index * componentHeight), 16777215);
        }
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
