package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

/**
 * Created by xt9 on 2018-03-25.
 */
public class ContainerTrialKeystone extends Container {
    private InventoryPlayer inventory;
    public TileEntityTrialKeystone tile;
    private World world;

    public ContainerTrialKeystone(TileEntityTrialKeystone te, InventoryPlayer inventory, World world) {
        this.tile = te;
        this.inventory = inventory;
        this.world = world;

        addSlotsToHandler();
        addInventorySlots();

    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(!world.isRemote) {
            tile.updateState();
        }
    }

    private void addSlotsToHandler() {
        addSlotToContainer(new SlotTrialKeystone(tile.trialKey, 0, -19, 1));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(inventory, index, 20 + row * 18, 172);
            addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 20 + column * 18;
                int y = 114 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(inventory, index, x, y);
                addSlotToContainer(slot);
            }
        }
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        tile.markDirty();
        inventory.markDirty();
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return !player.isSpectator();
    }
}
