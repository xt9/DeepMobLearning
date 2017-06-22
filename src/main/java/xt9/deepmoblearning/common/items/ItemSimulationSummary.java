package xt9.deepmoblearning.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.common.util.ItemStackNBTHelper;

import java.util.List;

/**
 * Created by xt9 on 2017-06-19.
 */
public class ItemSimulationSummary extends ItemBase {
    public ItemSimulationSummary() {
        super("simulation_summary", 64);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
        list.add(getMobType(stack));
    }

    public static String getMobType(ItemStack stack) {
        return ItemStackNBTHelper.getString(stack, "mobType", "Empty summary");
    }

    public static void setMobType(ItemStack stack, String type) {
        ItemStackNBTHelper.setString(stack, "mobType", type);
    }
}
