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
import xt9.deepmoblearning.common.network.messages.LevelUpModelMessage;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCreativeModelLearner extends ItemBase {
    public ItemCreativeModelLearner() {
        super("creative_model_learner", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        if(player.world.isRemote) {
            if(KeyboardHelper.isHoldingShift()) {
                Network.channel.sendToServer(new LevelUpModelMessage(0));
            } else if(KeyboardHelper.isHoldingCTRL()) {
                Network.channel.sendToServer(new LevelUpModelMessage(1));
            }
        }


        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }


    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        if(!KeyboardHelper.isHoldingShift()) {
            list.add(new TextComponentString(I18n.format("deepmoblearning.holdshift")));
        } else {
            list.add(new TextComponentString("A creative item that levels up data models inside the Deep Learner."));
            list.add(new TextComponentString("§r§oSHIFT§r§7 + §r§oRIGHT§r§7 click to increase tier.§r"));
            list.add(new TextComponentString("§r§oCTRL§r§7 + §r§oRIGHT§r§7 click to simulate kills.§r"));
        }

    }
}