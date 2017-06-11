package xt9.deepmoblearning.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xt9.deepmoblearning.Constants;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2017-06-10.
 */
public class ContainerDeepLearner extends Container {
    protected World world;
    protected EntityPlayer player;
    protected InventoryPlayer inventory;
    protected ItemStack deepLearner;
    protected int internalSlots;
    protected DeepLearnerInventory internalInventory;

    public ContainerDeepLearner(InventoryPlayer inventory, World world, ItemStack heldItem) {
        this.world = world;
        this.player = inventory.player;
        this.inventory = inventory;
        this.deepLearner = heldItem;
        this.internalSlots = Constants.DEEP_LEARNER_INTERNAL_SLOTS_SIZE;
        this.internalInventory = new DeepLearnerInventory(this, this.deepLearner);
        this.addChipSlots();
        this.addInventorySlots();
    }

    private void addChipSlots() {
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,0, 388, 80));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,1, 406, 80));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,2, 388, 98));
        this.addSlotToContainer(new DeepLearnerSlot(this, this.internalInventory,3, 406, 98));
    }

    private void addInventorySlots() {
        // 3 Top rows, starting with the bottom one
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                int x = 220 + column * 18;
                int y = 144 + row * 18;
                int index = column + row * 9 + 9;
                Slot slot = new Slot(this.inventory, index, x, y);
                this.addSlotToContainer(slot);
            }
        }

        // Hotbar
        for (int row = 0; row < 9; ++row) {
            int index = row;
            Slot slot = new Slot(this.inventory, index, 220 + row * 18, 202);
            this.addSlotToContainer(slot);
        }

        List<ItemStack> list = this.getInventory();
        for(ItemStack stack: list) {
            System.out.println(stack.getDisplayName());
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
    {
        ItemStack oldStackInSlot = ItemStack.EMPTY;
        Slot slotObject = inventorySlots.get(slot);

        if(slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            oldStackInSlot = stackInSlot.copy();

            if(slot < internalSlots)
            {
                if(!this.mergeItemStack(stackInSlot, internalSlots, (internalSlots + 36), true))
                    return ItemStack.EMPTY;
            }
            else if(!stackInSlot.isEmpty())
            {
                boolean b = true;
                for(int i=0; i<internalSlots; i++)
                {
                    Slot s = inventorySlots.get(i);
                    if(s!=null && s.isItemValid(stackInSlot))
                    {
                        if(!s.getStack().isEmpty() && (!ItemStack.areItemsEqual(stackInSlot,s.getStack())))
                            continue;
                        int space = Math.min(s.getItemStackLimit(stackInSlot), stackInSlot.getMaxStackSize());
                        if(!s.getStack().isEmpty())
                            space -= s.getStack().getCount();
                        if(space <= 0)
                            continue;
                        ItemStack insert = stackInSlot;
                        if(space < stackInSlot.getCount())
                            insert = stackInSlot.splitStack(space);
                        if(this.mergeItemStack(insert, i, i + 1, true))
                        {
                            b = false;
                        }
                    }
                }
                if(b)
                    return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0)
                slotObject.putStack(ItemStack.EMPTY);
            else
                slotObject.onSlotChanged();

            slotObject.inventory.markDirty();
            if (stackInSlot.getCount() == oldStackInSlot.getCount())
                return ItemStack.EMPTY;
            slotObject.onTake(player, oldStackInSlot);

            updateItemContents();
            detectAndSendChanges();
        }
        return oldStackInSlot;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer entityPlayer) {
        System.out.println(slotId);
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !this.player.isSpectator();
    }

    private void updateItemContents() {
        // TODO this.deepLearner.getItem().setContainedItems(this.deepLearner, this.internalInventory.getList()):
    }
}
