package xt9.deepmoblearning.common.trials;

import net.minecraft.world.World;
import xt9.deepmoblearning.common.entity.EntityGlitch;

/**
 * Created by xt9 on 2018-03-27.
 */
public class TrialRuleset {
    public static int getNumberOfAffixesPerTier(int tier) {
        switch(tier) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 1;
            case 3: return 2;
            case 4: return 3;
            default: return 0;
        }
    }

    public static int getMaxWaveFromTier(int tier) {
        switch(tier) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 4;
            case 3: return 5;
            case 4: return 7;
            default: return 1;
        }
    }

    public static int getGlitchSpawnChance(int tier) {
        switch(tier) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 3;
            case 3: return 6;
            case 4: return 11;
            default: return 0;
        }
    }

    /* Get a Glitch entity */
    public static EntityGlitch getGlitch(World world) {
        return new EntityGlitch(world);
    }
}
