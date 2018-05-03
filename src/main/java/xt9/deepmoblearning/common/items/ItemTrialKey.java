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
import xt9.deepmoblearning.common.network.CombatTrialStartMessage;
import xt9.deepmoblearning.common.util.KeyboardHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-03-24.
 */
public class ItemCombatTrial extends ItemBase {
    public ItemCombatTrial() {
        super("combat_trial", 1);
    }

    // todo remove
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nullable EnumHand hand) {
        if(player.world.isRemote) {
            DeepMobLearning.network.sendToServer(new CombatTrialStartMessage());
        }

        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(!KeyboardHelper.isHoldingShift()) {
            list.add(I18n.format("deepmoblearning.holdshift"));
        } else {
            list.add(I18n.format("deepmoblearning.combat_trial.instruction"));
        }
    }

}
