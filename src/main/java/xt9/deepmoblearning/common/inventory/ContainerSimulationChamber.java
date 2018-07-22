package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

/**
 * Created by xt9 on 2017-06-17.
 */
public class ContainerSimulationChamber extends Container {
    public static final int DATA_MODEL_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    public static final int PRISTINE_SLOT = 3;

    private IItemHandler inventory;
    private TileEntitySimulationChamber tile;
    private EntityPlayer player;
    private World world;

    public ContainerSimulationChamber(TileEntitySimulationChamber te, InventoryPlayer inventory, World world) {
        this.player = inventory.player;
        this.world = world;
        this.tile = te;

        this.inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotsToHandler();
        addInventorySlots();
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if(!world.isRemote) {
            // Update the tile every tick while container is open
            tile.updateState();
        }
    }

    private void addSlotsToHandler() {
        addSlotToContainer(new SlotSimulationChamber(inventory, DATA_MODEL_SLOT, -13, 1));
        addSlotToContainer(new SlotSimulationChamber(inventory, INPUT_SLOT, 176, 7));
        addSlotToContainer(new SlotSimulationChamber(inventory, OUTPUT_SLOT, 196, 7));
        addSlotToContainer(new SlotSimulationChamber(inventory, PRISTINE_SLOT, 186, 27));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(player.inventory, index, 36 + row * 18, 211);
            addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 36 + column * 18;
                int y = 153 + row * 18;
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
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !entityplayer.isSpectator();
    }
}
