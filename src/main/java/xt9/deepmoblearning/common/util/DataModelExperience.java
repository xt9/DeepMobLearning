package xt9.deepmoblearning.common.util;

import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.config.Config;

/**
 * Created by xt9 on 2017-06-14.
 */
public class DataModelExperience {
    // Simulations have no multipliers, they are always 1x
    private static final int[] killMultiplier = {
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier0").getInt(), 1, 100),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier1").getInt(), 1, 100),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier2").getInt(), 1, 100),
        MathHelper.ensureRange(Config.modelExperience.get("killMultiplierTier3").getInt(), 1, 100),
        0 // Max tier, no kill multiplier
    };

    private static final int[] maxExperience = {
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier1").getInt(), 1, 500) * killMultiplier[0],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier2").getInt(), 1, 500) * killMultiplier[1],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier3").getInt(), 1, 500) * killMultiplier[2],
        MathHelper.ensureRange(Config.modelExperience.get("killsToTier4").getInt(), 1, 500) * killMultiplier[3],
    };

    /* tier is CURRENT tier, kc is kill count for CURRENT tier, sc is simulation count for CURRENT  tier */
    public static boolean shouldIncreaseTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return false;
        }
        int roof = maxExperience[tier];
        int killExperience = kc * killMultiplier[tier];

        return killExperience + sc >= roof;
    }

    public static double getCurrentTierKillCountWithSims(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return kc + ((double) sc / killMultiplier[tier]);
    }

    public static int getCurrentTierSimulationCountWithKills(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return sc + (kc * killMultiplier[tier]);
    }

    public static double getKillsToNextTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        int killRoof = getTierRoof(tier, true);
        return killRoof - getCurrentTierKillCountWithSims(tier, kc, sc);
    }

    public static int getSimulationsToNextTier(int tier, int kc, int sc) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        int roof = getTierRoof(tier, false);
        return roof - getCurrentTierSimulationCountWithKills(tier, kc, sc);
    }

    public static int getTierRoof(int tier, boolean asKills) {
        if(tier == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        if(!asKills) {
            return maxExperience[tier];
        } else {
            return maxExperience[tier] / killMultiplier[tier];
        }
    }

    public static int getKillMultiplier(int tier) {
        return killMultiplier[tier];
    }
}
