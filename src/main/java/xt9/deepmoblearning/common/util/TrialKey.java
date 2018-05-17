package xt9.deepmoblearning.common.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.mobmetas.MobMetaData;
import xt9.deepmoblearning.common.mobmetas.MobMetaFactory;
import xt9.deepmoblearning.common.trials.affix.ITrialAffix;
import xt9.deepmoblearning.common.trials.affix.TrialAffixFactory;

/**
 * Created by xt9 on 2018-03-24.
 */
public class TrialKey {
    public static void attune(ItemStack stack, ItemStack dataModel, EntityPlayerMP player) {
        setAttunedNBT(stack, dataModel);
        MobMetaData data = DataModel.getMobMetaData(dataModel);

        PlayerHelper.sendMessage(player, new TextComponentString(stack.getDisplayName() + " was attuned to: " + data.getName()));
    }

    public static void setAttunedNBT(ItemStack stack, ItemStack dataModel) {
        setAttunedStatus(stack, true);

        MobMetaData data = DataModel.getMobMetaData(dataModel);
        setMobKey(stack, data.getKey());
        setTier(stack, DataModel.getTier(dataModel));
    }

    public static NonNullList<ITrialAffix> getAffixes(ItemStack stack, BlockPos pos, World world) {
        NonNullList<String> affixKeys = getAffixList(stack);
        NonNullList<ITrialAffix> result = NonNullList.create();

        affixKeys.forEach(key -> result.add(TrialAffixFactory.createAffix(key, pos, world)));

        return result;
    }

    public static String getTierName(ItemStack stack, boolean getNextTierName) {
        return Tier.getTierName(getTier(stack), getNextTierName);
    }

    public static MobMetaData getMobMetaData(ItemStack stack) {
        String key = getMobKey(stack);
        return MobMetaFactory.createMobMetaData(key);
    }

    public static String getMobKey(ItemStack stack) {
        return ItemStackNBTHelper.getString(stack, "mobKey", "");
    }

    public static void setMobKey(ItemStack stack, String key) {
        ItemStackNBTHelper.setString(stack, "mobKey", key);
    }

    public static int getTier(ItemStack stack) {
        return ItemStackNBTHelper.getInt(stack, "tier", 0);
    }

    public static void setTier(ItemStack stack, int tier) {
        ItemStackNBTHelper.setInt(stack, "tier", tier);
    }

    public static boolean isAttuned(ItemStack stack) {
        return getAttunedStatus(stack);
    }

    public static boolean getAttunedStatus(ItemStack stack) {
        return ItemStackNBTHelper.getBoolean(stack, "attuned", false);
    }

    public static void setAttunedStatus(ItemStack stack, boolean attuned) {
        ItemStackNBTHelper.setBoolean(stack, "attuned", attuned);
    }

    public static NonNullList<String> getAffixList(ItemStack stack) {
        return ItemStackNBTHelper.getStringList(stack, "affixes");
    }

    public static void setAffixList(ItemStack stack, NonNullList<String> list) {
        ItemStackNBTHelper.setStringList(stack, list, "affixes");
    }

}
