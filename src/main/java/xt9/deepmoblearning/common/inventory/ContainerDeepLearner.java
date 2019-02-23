package xt9.deepmoblearning.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.handlers.BaseItemHandler;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ContainerDeepLearner extends Container {
    protected BaseItemHandler handler;
    protected EnumHand usedHand;
    protected World world;
    protected EntityPlayer player;
    protected ItemStack deepLearner;
    protected int internalSlots;
    protected int deepLearnerSlot;

    public ContainerDeepLearner(InventoryPlayer inventory) {
        this.internalSlots = DeepConstants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
        this.world = inventory.player.world;
        this.player = inventory.player;

        Item mainHand = player.getHeldItemMainhand().getItem();
        usedHand = mainHand instanceof ItemDeepLearner ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        ItemStack heldItem = mainHand instanceof ItemDeepLearner ? player.getHeldItem(EnumHand.MAIN_HAND) : player.getHeldItem(EnumHand.OFF_HAND);

        this.handler = new BaseItemHandler(ItemDeepLearner.getContainedItems(heldItem));
        this.deepLearnerSlot = inventory.currentItem + internalSlots;
        this.deepLearner = heldItem;
        addDataModelSlots();
        addInventorySlots();
    }

    private void addDataModelSlots() {
        addSlot(new SlotDeepLearner(handler, 0, 257, 100));
        addSlot(new SlotDeepLearner(handler, 1, 275, 100));
        addSlot(new SlotDeepLearner(handler, 2, 257, 118));
        addSlot(new SlotDeepLearner(handler, 3, 275, 118));
    }

    private void addInventorySlots() {
        // Bind actionbar
        for (int row = 0; row < 9; row++) {
            int index = row;
            Slot slot = new Slot(player.inventory, index, 89 + row * 18, 211);
            addSlot(slot);
        }

        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int x = 89 + column * 18;
                int y = 153 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(player.inventory, index, x, y);
                addSlot(slot);
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
        updateInventories();
        return itemstack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {

        if(slotId == deepLearnerSlot && usedHand != EnumHand.OFF_HAND || (clickTypeIn == ClickType.SWAP && dragType == player.inventory.currentItem)) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        updateInventories();
        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        updateInventories();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !player.isSpectator();
    }

    private void updateInventories() {
        ItemDeepLearner.setContainedItems(deepLearner, handler.getItemStacks());
        ItemStack hand = player.getHeldItem(usedHand);
        if(!hand.isEmpty() && !hand.equals(deepLearner)) {
            player.setHeldItem(usedHand, deepLearner);
        }
        player.inventory.markDirty();
    }
}
