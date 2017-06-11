package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.common.items.ItemMobChip;

/**
 * Created by xt9 on 2017-06-11.
 */
public class DeepLearnerSlot extends Slot {
    private Container container;

    public DeepLearnerSlot(Container container, DeepLearnerInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.container = container;
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
