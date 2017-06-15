package xt9.deepmoblearning.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-11.
 */
public class DeepLearnerSlot extends SlotItemHandler {

    public DeepLearnerSlot(ItemStackHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return !stack.isEmpty() && stack.getItem() instanceof ItemMobChip;
    }
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
