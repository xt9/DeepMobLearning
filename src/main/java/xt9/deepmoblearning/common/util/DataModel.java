package xt9.deepmoblearning.common.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.capabilities.IPlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemGlitchArmor;
import xt9.deepmoblearning.common.items.ItemGlitchSword;
import xt9.deepmoblearning.common.mobmetas.MobKey;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.MobMetaFactory;

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

    public static ItemStack getModelFromMobKey(String mobKey) {
        ItemStack result = ItemStack.EMPTY;
        for (ItemDataModel dataModel : Registry.dataModels) {
            if(dataModel.getMobKey().equals(mobKey)) {
                result = new ItemStack(dataModel);
            }
        }
        return result;
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

    public static String getMobKey(ItemStack stack) {
        if(stack.getItem() instanceof ItemDataModel) {
            return ((ItemDataModel) stack.getItem()).getMobKey();
        } else {
            return MobKey.ZOMBIE;
        }
    }

    public static MobMetaData getMobMetaData(ItemStack stack) {
        return MobMetaFactory.createMobMetaData(getMobKey(stack));
    }

    public static int getPristineChance(ItemStack stack) {
        return Tier.getPristineChance(getTier(stack));
    }

    public static String getTierName(ItemStack stack, boolean getNextTierName) {
        return Tier.getTierName(getTier(stack), getNextTierName);
    }

    /* Called by deep learners */
    @SuppressWarnings("ConstantConditions")
    public static void increaseMobKillCount(ItemStack stack, EntityPlayerMP player) {
        // Get our current tier before increasing the kill count;
        int tier = getTier(stack);
        int i = getCurrentTierKillCount(stack);
        boolean isGlitchSwordEquipped = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof ItemGlitchSword;
        IPlayerTrial cap = player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);

        i = i + (isGlitchSwordEquipped && !cap.isTrialActive() ? 2 : 1);
        setCurrentTierKillCount(stack, i);

        // Update the totals
        setTotalKillCount(stack, getTotalKillCount(stack) + 1);

        if(DataModelExperience.shouldIncreaseTier(tier, i, getCurrentTierSimulationCount(stack))) {
            PlayerHelper.sendMessage(player, new TextComponentString(stack.getDisplayName() + " reached the " + getTierName(stack, true) + " tier"));

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
        if(getTier(stack) == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return DataModelExperience.getTierRoof(getTier(stack), true);
    }

    public static int getTierRoof(ItemStack stack) {
        if(getTier(stack) == DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
            return 0;
        }
        return DataModelExperience.getTierRoof(getTier(stack), false);
    }
}
