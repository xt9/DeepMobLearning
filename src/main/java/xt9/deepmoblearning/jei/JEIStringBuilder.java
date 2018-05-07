package xt9.deepmoblearning.jei;

import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.common.trials.TrialFactory;
import xt9.deepmoblearning.common.trials.TrialRuleset;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.trials.affix.TrialAffixFactory;

import java.util.Arrays;

/**
 * Created by xt9 on 2018-05-04.
 */
public class JEIStringBuilder {

    public static String[] getTrialKeystoneLines() {
        NonNullList<String> lines = NonNullList.create();
        lines.add("§3Trial keystone§r");
        lines.add("Fight in trials to speed up the collection of data for your data models.");
        lines.add("Conquering a trial will result in great rewards.");
        lines.add("");
        lines.add("Next page:\n What is affixes?");
        lines.add("Affixes will alter what happens during a trial. The behaviour of affixes vary greatly, an affix could be a buff to the player, or some in-world event.");
        lines.add("");
        lines.add("");
        lines.add("Next page:\n Affix scaling");
        lines.add("Affix count (by Trial key tier)");
        lines.add("Faulty: §3" + TrialRuleset.getNumberOfAffixesPerTier(0) + "§r");
        lines.add("Basic: §3" + TrialRuleset.getNumberOfAffixesPerTier(1) + "§r");
        lines.add("Advanced: §3" + TrialRuleset.getNumberOfAffixesPerTier(2) + "§r");
        lines.add("Superior: §3" + TrialRuleset.getNumberOfAffixesPerTier(3) + "§r");
        lines.add("Self Aware: §3" + TrialRuleset.getNumberOfAffixesPerTier(4) + "§r");
        lines.add("");
        lines.add("Remaining pages:\n Affix specification");

        NonNullList<ITrialAffix> affixes = TrialAffixFactory.getAllAffixes();
        affixes.forEach(affix -> {
            lines.add(affix.getAffixNameWithFormatting());
            lines.addAll(Arrays.asList(affix.getDescriptionLines()));
        });

        return lines.toArray(new String[lines.size()]);
    }

    public static String[] getTrialKeyLines() {
        NonNullList<String> lines = NonNullList.create();
        lines.add("§3A§rvailable attunements");

        NonNullList<String> availableTrials = TrialFactory.getValidTrialsHumanReadable();

        StringBuilder list = new StringBuilder();
        int i = 0;

        for (String mobName : availableTrials) {
            i++;
            if(i == availableTrials.size()) {
                list.append(mobName);
            }else {
                list.append(mobName).append(", ");
            }
        }

        lines.add(list.toString());

        lines.add("§3A§rttune by having trial key(s) in your inventory aswell as a deep learner filled with one data model that match the above list. Kill a mob that match that data model to trigger the attunement.");

        lines.add("§3T§rrial wavecount (Depends on model tier)");
        lines.add("Faulty: §3" + TrialRuleset.getMaxWaveFromTier(0) + "§r wave(s)");
        lines.add("Basic: §3" + TrialRuleset.getMaxWaveFromTier(1) + "§r wave(s)");
        lines.add("Advanced: §3" + TrialRuleset.getMaxWaveFromTier(2) + "§r wave(s)");
        lines.add("Superior: §3" + TrialRuleset.getMaxWaveFromTier(3) + "§r wave(s)");
        lines.add("Self Aware: §3" + TrialRuleset.getMaxWaveFromTier(4) + "§r wave(s)");

        return lines.toArray(new String[lines.size()]);
    }
}
