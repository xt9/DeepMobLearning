package xt9.deepmoblearning.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.common.items.ItemTrialKey;

/**
 * Created by xt9 on 2018-04-04.
 */
public class SlotTrialKeystone extends SlotItemHandler {

    public SlotTrialKeystone(IItemHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        return !stack.isEmpty() && item instanceof ItemTrialKey;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
