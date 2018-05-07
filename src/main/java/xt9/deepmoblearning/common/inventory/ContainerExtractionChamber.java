package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;

/**
 * Created by xt9 on 2018-01-07.
 */
public class ContainerExtractionChamber extends Container {
    public static final int INPUT_SLOT = 0;

    private IItemHandler inventory;
    private ItemStack currentPristine;
    public TileEntityExtractionChamber tile;
    private EntityPlayer player;
    private World world;

    public ContainerExtractionChamber(TileEntityExtractionChamber te, InventoryPlayer inventory, World world) {
        this.player = inventory.player;
        this.world = world;
        this.tile = te;
        this.inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.currentPristine = tile.getPristine();

        tile.updateState();
        tile.updatePageHandler(tile.pageHandler.getCurrentPageIndex());

        addSlotsToHandler();
        addInventorySlots();
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(!world.isRemote) {
            // Update the tile every tick while container is open
            tile.updateState();

            if(pristineItemChanged() || tile.getPristine().isEmpty()) {
                tile.updatePageHandler(0);
            }
        }
    }

    private boolean pristineItemChanged() {
        if(!ItemStack.areItemsEqual(currentPristine, tile.getPristine())) {
            currentPristine = tile.getPristine();
            return true;
        } else {
            return false;
        }
    }

    private void addSlotsToHandler() {
        addSlotToContainer(new SlotExtractionChamber(inventory, INPUT_SLOT, 79, 62));

        // Output inventory x16 slots
         // Row 1
        addSlotToContainer(new SlotExtractionChamber(inventory, 1, 100, 7));
        addSlotToContainer(new SlotExtractionChamber(inventory, 2, 100 + (18), 7));
        addSlotToContainer(new SlotExtractionChamber(inventory, 3, 100 + (18 * 2), 7));
        addSlotToContainer(new SlotExtractionChamber(inventory, 4, 100 + (18 * 3), 7));
        // Row 2
        addSlotToContainer(new SlotExtractionChamber(inventory, 5, 100, 7 + 18));
        addSlotToContainer(new SlotExtractionChamber(inventory, 6, 100 + (18), 7 + 18));
        addSlotToContainer(new SlotExtractionChamber(inventory, 7, 100 + (18 * 2), 7 + 18));
        addSlotToContainer(new SlotExtractionChamber(inventory, 8, 100 + (18 * 3), 7 + 18));
        // Row 3
        addSlotToContainer(new SlotExtractionChamber(inventory, 9, 100, 7 + (18 * 2)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 10, 100 + (18), 7 + (18 * 2)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 11, 100 + (18 * 2), 7 + (18 * 2)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 12, 100 + (18 * 3), 7 + (18 * 2)));
        // Row 4
        addSlotToContainer(new SlotExtractionChamber(inventory, 13, 100, 7 + (18 * 3)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 14, 100 + (18), 7 + (18 * 3)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 15, 100 + (18 * 2), 7 + (18 * 3)));
        addSlotToContainer(new SlotExtractionChamber(inventory, 16, 100 + (18 * 3), 7 + (18 * 3)));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(player.inventory, index, 8 + row * 18, 154);
            addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 8 + column * 18;
                int y = 96 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(player.inventory, index, x, y);
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
        this.player.inventory.markDirty();
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return !player.isSpectator();
    }
}
