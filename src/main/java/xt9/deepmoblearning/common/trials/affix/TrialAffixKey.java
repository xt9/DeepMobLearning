package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.util.NonNullList;
import java.util.Random;

/**
 * Created by xt9 on 2018-04-21.
 */
public class TrialAffixKey {
    public static final String SPEED = "speed";
    public static final String REGEN_PARTY = "regen_party";
    public static final String EMPOWERED_GLITCHES = "empowered_glitches";
    public static final String KNOCKBACK_IMMUNITY = "knockback_immunity";
    public static final String BLAZE_INVADERS = "blaze_invaders";
    public static final String LOOT_HOARDERS = "loot_hoarders";
    public static final String THUNDERDOME = "thunderdome";

    public static String getRandomKey(NonNullList<String> excluding) {
        String[] keyList = {SPEED, REGEN_PARTY, EMPOWERED_GLITCHES, KNOCKBACK_IMMUNITY, BLAZE_INVADERS, LOOT_HOARDERS, THUNDERDOME};

        String key = keyList[new Random().nextInt(keyList.length)];
        boolean keyIsExcluded = excluding.contains(key);
        int length = keyList.length;

        if(excluding.size() >= length) {
            return null;
        }

        while (keyIsExcluded) {
            key = keyList[new Random().nextInt(keyList.length)];
            if(!excluding.contains(key)) {
                keyIsExcluded = false;
            }
        }

        return key;
    }
}
