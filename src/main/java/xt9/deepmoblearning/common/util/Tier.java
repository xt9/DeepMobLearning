package xt9.deepmoblearning.common.util;

import xt9.deepmoblearning.common.config.Config;

/**
 * Created by xt9 on 2018-03-25.
 * DataModel tiers, used for datamodels & TileEntityRelation keys
 */
public class Tier {
    public static int getPristineChance(int tier) {
        switch(tier) {
            case 0: return 0;
            case 1: return Config.pristineChance.get("tier1").getInt();
            case 2: return Config.pristineChance.get("tier2").getInt();
            case 3: return Config.pristineChance.get("tier3").getInt();
            case 4: return Config.pristineChance.get("tier4").getInt();
            default: return 0;
        }
    }

    public static String getTierName(int tier, boolean getNextTierName) {
        int addTiers = getNextTierName ? 1 : 0;
        switch(tier + addTiers) {
            case 0: return "§8Faulty§r";
            case 1: return "§aBasic§r";
            case 2: return "§9Advanced§r";
            case 3: return "§dSuperior§r";
            case 4: return "§6Self Aware§r";
            default: return "§8Faulty§r";
        }
    }

    public static boolean isMaxTier(int tier) {
        return tier == 4;
    }
}
