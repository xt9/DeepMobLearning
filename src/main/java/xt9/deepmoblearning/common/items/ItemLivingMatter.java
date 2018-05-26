package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.network.ConsumeLivingMatterMessage;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2017-12-20.
 */
public class ItemLivingMatter extends ItemBase {
    private String matterTypeFormatted;
    private String type;

    private ItemLivingMatter(String name, String matterTypeFormatted, String type) {
        super(name, 64);
        this.matterTypeFormatted = matterTypeFormatted;
        this.type = type;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        list.add("Can be consumed for experience §r(Right click)§r");
        list.add("Hold §rSHIFT§7 to consume entire stack.");
        list.add(I18n.format("deepmoblearning.living_matter.exp", Config.livingMatterEXP.get(getType()).getInt()));
    }

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        if(player.world.isRemote) {
            if(KeyboardHelper.isHoldingShift()) {
                DeepMobLearning.network.sendToServer(new ConsumeLivingMatterMessage(true));
            } else {
                DeepMobLearning.network.sendToServer(new ConsumeLivingMatterMessage(false));
            }
        }

        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public String getType() {
        return type;
    }

    public String getMatterTypeName() {
        return matterTypeFormatted;
    }

    public static class Overworldian extends ItemLivingMatter {
        public Overworldian() {
            super("living_matter_overworldian", "§aOverworldian§r", "overworldian");
        }

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Hellish extends ItemLivingMatter {
        public Hellish() {
            super("living_matter_hellish", "§cHellish§r", "hellish");
        }

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Extraterrestrial extends ItemLivingMatter {
        public Extraterrestrial() {
            super("living_matter_extraterrestrial", "§dExtraterrestrial§r", "extraterrestrial");
        }

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Twilight extends ItemLivingMatter {
        public Twilight() {
            super("living_matter_twilight", "§dTwilight§r", "twilight");
        }

        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }
}
