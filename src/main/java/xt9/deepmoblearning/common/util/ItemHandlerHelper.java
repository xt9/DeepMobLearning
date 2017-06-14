package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by xt9 on 2017-06-14.
 */
public class ItemHandlerHelper {
    public static NonNullList<ItemStack> getItemStackHandlerList(ItemStackHandler handler) {
        NonNullList<ItemStack> list = NonNullList.create();
        int numOfSlots  = handler.getSlots();
        for (int i = 0; i < numOfSlots; i++) {
            list.add(i, handler.getStackInSlot(i));
        }
        return list;
    }
}
