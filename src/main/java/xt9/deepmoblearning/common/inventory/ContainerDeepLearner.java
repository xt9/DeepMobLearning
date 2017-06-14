package xt9.deepmoblearning.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.util.ItemHandlerHelper;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ContainerDeepLearner extends Container {
    protected ItemStackHandler handler;
    protected EntityEquipmentSlot equipmentSlot;
    protected World world;
    protected EntityPlayer player;
    protected ItemStack deepLearner;
    protected int internalSlots;
    protected int deepLearnerSlot;

    public ContainerDeepLearner(InventoryPlayer inventory, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        this.internalSlots = DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
        this.handler = new ItemStackHandler(ItemDeepLearner.getContainedItems(heldItem));
        this.deepLearnerSlot = inventory.currentItem + internalSlots;
        this.deepLearner = heldItem;
        this.world = world;
        this.player = inventory.player;
        this.equipmentSlot = slot;
        this.addChipSlots();
        this.addInventorySlots();
    }

    private void addChipSlots() {
        this.addSlotToContainer(new DeepLearnerSlot(this.handler, 0, 257, 100));
        this.addSlotToContainer(new DeepLearnerSlot(this.handler, 1, 275, 100));
        this.addSlotToContainer(new DeepLearnerSlot(this.handler, 2, 257, 118));
        this.addSlotToContainer(new DeepLearnerSlot(this.handler, 3, 275, 118));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(this.player.inventory, index, 89 + row * 18, 211);
            this.addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 89 + column * 18;
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
        updateInventories();
        return itemstack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {

        if(slotId == this.deepLearnerSlot && !this.equipmentSlot.getName().equals("offhand") || (clickTypeIn == ClickType.SWAP && dragType == player.inventory.currentItem)) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        updateInventories();
        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        updateInventories();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !this.player.isSpectator();
    }

    private void updateInventories() {
        ItemDeepLearner.setContainedItems(this.deepLearner, ItemHandlerHelper.getItemStackHandlerList(this.handler));
        ItemStack hand = player.getItemStackFromSlot(this.equipmentSlot);
        if(!hand.isEmpty() && !hand.equals(deepLearner))
            player.setItemStackToSlot(this.equipmentSlot, this.deepLearner);
        player.inventory.markDirty();
    }
}
