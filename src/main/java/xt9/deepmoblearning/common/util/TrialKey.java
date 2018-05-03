package xt9.deepmoblearning.common.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import xt9.deepmoblearning.common.mobs.MobMetaData;

/**
 * Created by xt9 on 2018-03-24.
 */
public class TrialKeystone {
    public static void attune(ItemStack stack, ItemStack dataModel, EntityPlayerMP player) {
        setAttunedStatus(stack, true);

        MobMetaData data = DataModel.getMobMetaData(dataModel);
        setMobKey(stack, data.getKey());
        player.sendMessage(new TextComponentString(stack.getDisplayName() + " was attuned to: " + data.getName()));

    }

    public static String getMobKey(ItemStack stack) {
        return ItemStackNBTHelper.getString(stack, "mobKey", "");
    }

    public static void setMobKey(ItemStack stack, String key) {
        ItemStackNBTHelper.setString(stack, "mobKey", key);
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
}
