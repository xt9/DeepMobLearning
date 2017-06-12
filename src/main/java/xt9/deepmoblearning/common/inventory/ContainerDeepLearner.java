package xt9.deepmoblearning.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ContainerDeepLearner extends Container {
    protected World world;
    protected EntityPlayer player;
    protected ItemStack deepLearner;
    protected EntityEquipmentSlot equipmentSlot;
    protected int internalSlots;
    protected DeepLearnerInventory internalInventory;
    protected int deepLearnerSlot;

    public ContainerDeepLearner(InventoryPlayer inventory, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        this.world = world;
        this.player = inventory.player;
        this.deepLearner = heldItem;
        this.internalSlots = DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
        this.internalInventory = new DeepLearnerInventory(this, this.deepLearner);
        this.equipmentSlot = slot;
        this.addChipSlots();
        this.addInventorySlots();
        this.deepLearnerSlot = inventory.currentItem + internalSlots;

        // Populate stored items
        this.internalInventory.list = ((ItemDeepLearner) this.deepLearner.getItem()).getContainedItems(this.deepLearner);
    }

    public NonNullList<ItemStack> getInternalItemStacks() {
        return this.internalInventory.list;
    }

    private void addChipSlots() {
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,0, 388, 80));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,1, 406, 80));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,2, 388, 98));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,3, 406, 98));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(this.player.inventory, index, 220 + row * 18, 202);
            this.addSlotToContainer(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 220 + column * 18;
                int y = 144 + row * 18;
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
        if(slotId == this.deepLearnerSlot || (clickTypeIn == ClickType.SWAP && dragType == player.inventory.currentItem)) {
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
        ((ItemDeepLearner) this.deepLearner.getItem()).setContainedItems(this.deepLearner, this.internalInventory.list);
        ItemStack hand = player.getItemStackFromSlot(this.equipmentSlot);
        if(!hand.isEmpty() && !hand.equals(deepLearner))
            player.setItemStackToSlot(this.equipmentSlot, this.deepLearner);
        player.inventory.markDirty();
    }
}
