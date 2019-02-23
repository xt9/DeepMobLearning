package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.network.messages.ConsumeLivingMatterMessage;
import xt9.deepmoblearning.common.network.Network;
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

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TextComponentString("Can be consumed for experience §r(Right click)§r"));
        list.add(new TextComponentString("Hold §rSHIFT§7 to consume entire stack."));
        list.add(new TextComponentString(I18n.format("deepmoblearning.living_matter.exp", Config.livingMatterEXP.get(getType()))));
    }

    @Override
    @SuppressWarnings({"NullableProblems", "unchecked"})
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        if(player.world.isRemote) {
            if(KeyboardHelper.isHoldingShift()) {
                Network.channel.sendToServer(new ConsumeLivingMatterMessage(true));
            } else {
                Network.channel.sendToServer(new ConsumeLivingMatterMessage(false));
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

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Hellish extends ItemLivingMatter {
        public Hellish() {
            super("living_matter_hellish", "§cHellish§r", "hellish");
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Extraterrestrial extends ItemLivingMatter {
        public Extraterrestrial() {
            super("living_matter_extraterrestrial", "§dExtraterrestrial§r", "extraterrestrial");
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }

    public static class Twilight extends ItemLivingMatter {
        public Twilight() {
            super("living_matter_twilight", "§dTwilight§r", "twilight");
        }

        @OnlyIn(Dist.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, list, flagIn);
        }
    }
}
