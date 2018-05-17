package xt9.deepmoblearning.common.trials;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.mobmetas.MobKey;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.MobMetaFactory;
import xt9.deepmoblearning.common.util.MathHelper;
import xt9.deepmoblearning.common.util.Tier;
import xt9.deepmoblearning.common.util.TrialKey;


/**
 * Created by xt9 on 2018-03-27.
 */
public class TrialFactory {
    public static Trial createTrial(String key) {
        Trial trial = new ZombieTrial(MobKey.ZOMBIE, new int[] {4, 5, 6, 6, 10, 14, 20});

        if(key.equals(MobKey.ZOMBIE)) {
            trial = new ZombieTrial(MobKey.ZOMBIE, new int[] {4, 5, 6, 6, 10, 14, 20});
        } else if(key.equals(MobKey.ENDERMAN)) {
            trial = new EndermanTrial(MobKey.ENDERMAN, new int[] {2, 2, 3, 4, 4, 7, 9});
        } else if(key.equals(MobKey.SKELETON)) {
            trial = new SkeletonTrial(MobKey.SKELETON, new int[] {4, 4, 5, 7, 9, 11, 16});
        } else if(key.equals(MobKey.WITCH)) {
            trial = new WitchTrial(MobKey.WITCH, new int[]{2, 2, 3, 3, 4, 5, 6});
        } else if(key.equals(MobKey.SPIDER)) {
            trial = new SpiderTrial(MobKey.SPIDER, new int[]{3, 4, 5, 5, 6, 8, 10});
        } else if(key.equals(MobKey.SLIME)) {
            trial = new SlimeTrial(MobKey.SLIME, new int[]{4, 5, 5, 7, 7, 9, 15});
        } else if(key.equals(MobKey.WITHERSKELETON)) {
            trial = new WitherSkeletonTrial(MobKey.WITHERSKELETON, new int[]{2, 3, 4, 4, 6, 8, 11});
        }

        return trial;
    }

    public static NonNullList<String> getValidTrials() {
        NonNullList<String> validKeys = NonNullList.create();
        validKeys.add(MobKey.ZOMBIE);
        validKeys.add(MobKey.ENDERMAN);
        validKeys.add(MobKey.SKELETON);
        validKeys.add(MobKey.WITCH);
        validKeys.add(MobKey.SPIDER);
        validKeys.add(MobKey.SLIME);
        validKeys.add(MobKey.WITHERSKELETON);
        return validKeys;
    }

    public static NonNullList<String> getValidTrialsHumanReadable() {
        NonNullList<String> mobNames = NonNullList.create();

        getValidTrials().forEach(key -> {
            MobMetaData data = MobMetaFactory.createMobMetaData(key);
            mobNames.add(data.getName());

        });

        return mobNames;
    }

    public static boolean isMobKeyValidForTrial(String key) {
        return getValidTrials().contains(key);
    }

    public static NonNullList<ItemStack> getRewards(ItemStack trialKey) {
        NonNullList<ItemStack> rewards = NonNullList.create();
        Trial trialData = TrialFactory.createTrial(TrialKey.getMobKey(trialKey));

        rewards.addAll(trialData.getTrialRewards(TrialKey.getTier(trialKey)));

        if(Tier.isMaxTier(TrialKey.getTier(trialKey))) {
            // Limit the max tier reward list to 3 items
            NonNullList<ItemStack> maxTierRewards = Config.LootParser.getTrialRewards(trialData.getMobKey());
            for (int i = 0; i < MathHelper.ensureRange(maxTierRewards.size(), 0, 3); i++) {
                rewards.add(maxTierRewards.get(i));
            }
        }

        return rewards;
    }
}
