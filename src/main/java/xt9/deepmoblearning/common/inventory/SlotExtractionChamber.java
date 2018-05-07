package xt9.deepmoblearning.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemPristineMatter;


/**
 * Created by xt9 on 2018-01-07.
 */
public class SlotExtractionChamber extends SlotItemHandler {

    public SlotExtractionChamber(IItemHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        switch(getSlotIndex()) {
            case ContainerExtractionChamber.INPUT_SLOT:
                return !stack.isEmpty() && item instanceof ItemPristineMatter;
            default:
                return false;
        }
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
