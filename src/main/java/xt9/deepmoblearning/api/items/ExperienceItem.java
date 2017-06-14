package xt9.deepmoblearning.api.items;

/**
 * Created by xt9 on 2017-06-14.
 */
public class ExperienceItem {
    // Todo configurable exp
    // Kills required per  tier 4, 8, 20, 55, 100
    public static final int[] maxExperience = {64, 256, 960, 3520, 9600};
    public static final int[] killMultiplier = {16, 32, 48, 64, 96};
    // Simulations have no multipliers, they are always 1x

    public static boolean shouldIncreaseTier(int currentTier, int killCount, int simulationCount) {
        int max = maxExperience[currentTier];
        int previousTiersExp = getPreviousTierSimulationMax(currentTier);

        int currentKillExp = getKillCountForCurrentTier(currentTier, killCount) * killMultiplier[currentTier];

        return currentKillExp + simulationCount - previousTiersExp >= max;
    }

    public static int killsToNextTier(int currentTier, int killCount, int simulationCount) {
        int max = maxExperience[currentTier];
        int previousMaxes = getPreviousTierKillMax(currentTier);

        int killsFromSims = (int) ((float) getSimulationCountForCurrentTier(currentTier, simulationCount) / killMultiplier[currentTier]);
        return max + previousMaxes - getKillCountForCurrentTier(currentTier, killCount) - killsFromSims;
    }

    public static int simulationsToNextTier(int currentTier, int killCount, int simulationCount) {
        int max = maxExperience[currentTier];
        int currentKillExp = getKillCountForCurrentTier(currentTier, killCount) * killMultiplier[currentTier];
        int previousMaxes = getPreviousTierSimulationMax(currentTier);
        return max + previousMaxes - currentKillExp - simulationCount;
    }

    public static int getMaxKills(int currentTier) {
        return (int) ((float) maxExperience[currentTier] / killMultiplier[currentTier]);
    }

    public static int getKillCountForCurrentTier(int currentTier, int killcount) {
        return killcount - getPreviousTierKillMax(currentTier);
    }

    public static int getSimulationCountForCurrentTier(int currentTier, int simulationCount) {
        return simulationCount - getPreviousTierSimulationMax(currentTier);
    }

    private static int getPreviousTierKillMax(int currentTier) {
        int result = 0;
        for (int i = currentTier - 1; i >= 0; i--) {
            result = result + (int)((float) maxExperience[i] / killMultiplier[i]);
        }
        return result;
    }

    private static int getPreviousTierSimulationMax(int currentTier) {
        int result = 0;
        for (int i = currentTier - 1; i >= 0; i--) {
            result = result + maxExperience[i];
        }
        return result;
    }
}
