package xt9.deepmoblearning.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.mobs.MobMetaData;
import xt9.deepmoblearning.common.mobs.MobMetaFactory;

/**
 * Created by xt9 on 2018-01-06.
 */
public class DataModel {
    public static NonNullList<ItemStack> getValidFromList(NonNullList<ItemStack> list) {
        NonNullList<ItemStack> filteredList = NonNullList.create();

        for(ItemStack stack : list) {
            Item item = stack.getItem();

            if(item instanceof ItemDataModel) {
                filteredList.add(stack);
            }
        }

        return filteredList;
    }

    public static boolean hasExtraTooltip(ItemStack stack) {
        return getExtraTooltip(stack) != null;
    }

    public static String getExtraTooltip(ItemStack stack) {
        return getMobMetaData(stack).getExtraTooltip();
    }

    public static String getMatterTypeName(ItemStack stack) {
        return getMobMetaData(stack).getMatterTypeName();
    }

    public static int getSimulationTickCost(ItemStack stack) {
        return getMobMetaData(stack).getSimulationTickCost();
    }

    public static MobMetaData getMobMetaData(ItemStack stack) {
        return MobMetaFactory.createMobMetaData(stack);
    }

    public static int getPristineChance(ItemStack stack) {
        switch(getTier(stack)) {
            case 0: return 0;
            case 1: return Config.pristineChance.get("tier1").getInt();
            case 2: return Config.pristineChance.get("tier2").getInt();
            case 3: return Config.pristineChance.get("tier3").getInt();
            case 4: return Config.pristineChance.get("tier4").getInt();
            default: return 0;
        }
    }

    public static String getTierName(ItemStack stack, boolean getNextTierName) {
        int addTiers = getNextTierName ? 1 : 0;
        switch(getTier(stack) + addTiers) {
            case 0: return "§8Faulty§r";
            case 1: return "§aBasic§r";
            case 2: return "§9Advanced§r";
            case 3: return "§dSuperior§r";
            case 4: return "§6Self Aware§r";
            default: return "§8Faulty§r";
        }
    }

    /* Called by deep learners */
    public static void increaseMobKillCount(ItemStack stack, EntityPlayer player) {
        // Get our current tier before increasing the kill count;
        int tier = getTier(stack);
        int i = getCurrentTierKillCount(stack);
        i = i + 1;
        setCurrentTierKillCount(stack, i);

        // Update the totals
        setTotalKillCount(stack, getTotalKillCount(stack) + 1);

        if(DataModelExperience.shouldIncreaseTier(tier, i, getCurrentTierSimulationCount(stack))) {
            player.sendMessage(new TextComponentString(stack.getDisplayName() + " reached the " + getTierName(stack, true) + " tier"));

            setCurrentTierKillCount(stack, 0);
            setCurrentTierSimulationCount(stack, 0);
            setTier(stack, tier + 1);
        }
    }

    /* Called by simulation chamber */
    public static void increaseSimulationCount(ItemStack stack) {
        int tier = getTier(stack);
        int i = getCurrentTierSimulationCount(stack);
        i = i + 1;
        setCurrentTierSimulationCount(stack, i);

        // Update the totals
        setTotalSimulationCount(stack, getTotalSimulationCount(stack) + 1);

        if(DataModelExperience.shouldIncreaseTier(tier, getCurrentTierKillCount(stack), i)) {
            setCurrentTierKillCount(stack, 0);
            setCurrentTierSimulationCount(stack, 0);
            setTier(stack, tier + 1);
        }
    }



    public static int getTier(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "tier", 0);
    }

    public static void setTier(ItemStack stack, int tier) {
        ItemStackNBTHelper.setInt(stack, "tier", tier);
    }

    public static int getCurrentTierKillCount(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "killCount", 0);
    }

    public static void setCurrentTierKillCount(ItemStack stack, int count) {
        ItemStackNBTHelper.setInt(stack, "killCount", count);
    }

    public static int getCurrentTierSimulationCount(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "simulationCount", 0);
    }

    public static void setCurrentTierSimulationCount(ItemStack stack, int count) {
        ItemStackNBTHelper.setInt(stack, "simulationCount", count);
    }

    public static int getTotalKillCount(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "totalKillCount", 0);
    }

    public static void setTotalKillCount(ItemStack stack, int count) {
        ItemStackNBTHelper.setInt(stack, "totalKillCount", count);
    }

    public static int getTotalSimulationCount(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "totalSimulationCount", 0);
    }

    public static void setTotalSimulationCount(ItemStack stack, int count) {
        ItemStackNBTHelper.setInt(stack, "totalSimulationCount", count);
    }


    /*
     The functions below are not NBT getters/setters
    */
    public static double getCurrentTierKillCountWithSims(ItemStack stack) {
        return DataModelExperience.getCurrentTierKillCountWithSims(getTier(stack), getCurrentTierKillCount(stack), getCurrentTierSimulationCount(stack));
    }

    public static int getCurrentTierSimulationCountWithKills(ItemStack stack) {
        return DataModelExperience.getCurrentTierSimulationCountWithKills(getTier(stack), getCurrentTierKillCount(stack), getCurrentTierSimulationCount(stack));
    }

    public static double getKillsToNextTier(ItemStack stack) {
        return DataModelExperience.getKillsToNextTier(getTier(stack), getCurrentTierKillCount(stack), getCurrentTierSimulationCount(stack));
    }

    public static int getSimulationsToNextTier(ItemStack stack) {
        return DataModelExperience.getSimulationsToNextTier(getTier(stack), getCurrentTierKillCount(stack), getCurrentTierSimulationCount(stack));
    }

    public static int getTierRoofAsKills(ItemStack stack) {
        if(getTier(stack) == DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
            return 0;
        }
        return DataModelExperience.getTierRoof(getTier(stack), true);
    }

    public static int getTierRoof(ItemStack stack) {
        if(getTier(stack) == DeepConstants.MOB_CHIP_MAXIMUM_TIER) {
            return 0;
        }
        return DataModelExperience.getTierRoof(getTier(stack), false);
    }
}
