package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

/**
 * Created by xt9 on 2017-06-17.
 */
public class ContainerSimulationChamber extends Container {
    public IItemHandler handler;
    public TileEntitySimulationChamber tile;
    public EntityPlayer player;
    public World world;

    public ContainerSimulationChamber(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        this.player = inventory.player;
        this.world = world;
        this.tile = te;

        this.handler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.addSlotsToHandler();
        this.addInventorySlots();
    }

    private void addSlotsToHandler() {
        // Todo implement SimulationChamberSlot
        this.addSlotToContainer(new SlotMobChip(this.handler, DeepConstants.SIMULATION_CHAMBER_CHIP_SLOT, -21, 1));
        this.addSlotToContainer(new SlotItemHandler(this.handler, DeepConstants.SIMULATION_CHAMBER_INPUT_SLOT, 129, 16));
        this.addSlotToContainer(new SlotItemHandler(this.handler, DeepConstants.SIMULATION_CHAMBER_OUTPUT_SLOT, 151, 16));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(this.player.inventory, index, 9 + row * 18, 211);
            this.addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 9 + column * 18;
                int y = 153 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(this.player.inventory, index, x, y);
                this.addSlotToContainer(slot);
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
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
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
        this.tile.markDirty();
        this.player.inventory.markDirty();
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !this.player.isSpectator();
    }
}
