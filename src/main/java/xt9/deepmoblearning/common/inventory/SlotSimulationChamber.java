package xt9.deepmoblearning.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemPolymerClay;

/**
 * Created by xt9 on 2017-06-11.
 */
public class SlotSimulationChamber extends SlotItemHandler {

    public SlotSimulationChamber(IItemHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        switch(getSlotIndex()) {
            case ContainerSimulationChamber.DATA_MODEL_SLOT:
                return !stack.isEmpty() && item instanceof ItemDataModel;
            case ContainerSimulationChamber.INPUT_SLOT:
                return !stack.isEmpty() && item instanceof ItemPolymerClay;
            case ContainerSimulationChamber.OUTPUT_SLOT:
                return false;
            case ContainerSimulationChamber.PRISTINE_SLOT:
                return false;
            default:
                return false;
        }
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
