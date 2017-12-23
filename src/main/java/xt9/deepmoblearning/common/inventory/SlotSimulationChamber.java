package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.handlers.SimulationChamberHandler;
import xt9.deepmoblearning.common.items.ItemMobChip;
import xt9.deepmoblearning.common.items.ItemPolymerClay;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2017-06-11.
 */
public class SlotSimulationChamber extends SlotItemHandler {

    public SlotSimulationChamber(SimulationChamberHandler handler, int index, int x, int y) {
        super(handler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        switch(this.getSlotIndex()) {
            case DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT:
                return !stack.isEmpty() && item instanceof ItemMobChip;
            case DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT:
                return !stack.isEmpty() && item instanceof ItemPolymerClay;
            case DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT:
                return false;
            case DeepConstants.SIMULATION_CHAMBER_PRISTINE_SLOT:
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        SimulationChamberHandler handler = (SimulationChamberHandler) this.getItemHandler();
        return !handler.extractItemAsPlayer(this.getSlotIndex(), 1, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount) {
        SimulationChamberHandler handler = (SimulationChamberHandler) this.getItemHandler();
        return handler.extractItemAsPlayer(this.getSlotIndex(), amount, false);
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
