package xt9.deepmoblearning.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.util.KeyboardHelper;
import xt9.deepmoblearning.common.util.TrialKey;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-03-24.
 */
public class ItemTrialKey extends ItemBase {
    public ItemTrialKey() {
        super("trial_key", 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        if(!KeyboardHelper.isHoldingShift()) {
            list.add(new TextComponentString(I18n.format("deepmoblearning.holdshift")));
        }

        if(KeyboardHelper.isHoldingShift()) {
            if(TrialKey.isAttuned(stack)) {
                list.add(new TextComponentString(I18n.format("deepmoblearning.trial_key.attuned_type", TrialKey.getMobMetaData(stack).getName())));
                list.add(new TextComponentString(I18n.format("deepmoblearning.trial_key.tier", TrialKey.getTierName(stack, false))));

                NonNullList<ITrialAffix> affixes = TrialKey.getAffixes(stack, new BlockPos(0,0,0), worldIn);
                StringBuilder affixList = new StringBuilder(affixes.isEmpty() ? "Faulty level trials have no affixes." : "");

                for (int i = 0; i < affixes.size(); i++) {
                    affixList.append(affixes.get(i).getAffixNameWithFormatting()).append(i == affixes.size() - 1 ? " " : ", ");
                }

                list.add(new TextComponentString(I18n.format("deepmoblearning.trial_key.affixes", affixList.toString())));
                list.add(new TextComponentString(I18n.format("deepmoblearning.trial_key.affix_go_to_jei")));
            } else {
                list.add(new TextComponentString(I18n.format("deepmoblearning.trial_key.notattuned")));
                list.add(new TextComponentString("§bAvailable attunements:§r"));

                NonNullList<String> availableTrials = TrialFactory.getValidTrialsHumanReadable();
                for (String mobName : availableTrials) {
                    list.add(new TextComponentString("  - §f" + mobName + "§r"));
                }
            }
        }
    }

}
