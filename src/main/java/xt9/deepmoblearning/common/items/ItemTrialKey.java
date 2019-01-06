package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.network.TrialStartMessage;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.affix.EmpoweredGlitchAffix;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.trials.affix.TrialAffixKey;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.TrialKey;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xt9 on 2018-03-24.
 */
public class ItemTrialKey extends ItemBase {
    public ItemTrialKey() {
        super("trial_key", 1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(!KeyboardHelper.isHoldingShift()) {
            list.add(I18n.format("deepmoblearning.holdshift"));
        }

        if(KeyboardHelper.isHoldingShift()) {
            if(TrialKey.isAttuned(stack)) {
                list.add(I18n.format("deepmoblearning.trial_key.attuned_type", TrialKey.getMobMetaData(stack).getName()));
                list.add(I18n.format("deepmoblearning.trial_key.tier", TrialKey.getTierName(stack, false)));

                NonNullList<ITrialAffix> affixes = TrialKey.getAffixes(stack, new BlockPos(0,0,0), worldIn);
                String affixList = affixes.isEmpty() ? "Faulty level trials have no affixes." : "";

                for (int i = 0; i < affixes.size(); i++) {
                    affixList += affixes.get(i).getAffixNameWithFormatting() + (i == affixes.size() - 1 ? " " : ", ");
                }

                list.add(I18n.format("deepmoblearning.trial_key.affixes", affixList));
                list.add(I18n.format("deepmoblearning.trial_key.affix_go_to_jei"));
            } else {
                list.add(I18n.format("deepmoblearning.trial_key.notattuned"));
                list.add("§bAvailable attunements:§r");

                NonNullList<String> availableTrials = TrialFactory.getValidTrialsHumanReadable();
                for (String mobName : availableTrials) {
                    list.add("  - §f" + mobName + "§r");
                }
            }
        }
    }

}
