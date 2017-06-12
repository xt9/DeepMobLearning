package xt9.deepmoblearning.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

/**
 * Created by xt9 on 2017-06-11.
 */
public class DeepLearnerInventory implements IInventory {
    private ItemStack stack;
    private Container container;
    public NonNullList<ItemStack> list;
    private String name;

    public DeepLearnerInventory(Container container, ItemStack stack) {
        this.container = container;

        if(!stack.isEmpty() && stack.getItem() instanceof ItemDeepLearner) {
            this.stack = stack;
            int numOfSlots = ((ItemDeepLearner) stack.getItem()).numOfInternalSlots();
            // Fill our internal slots
            this.list = NonNullList.withSize(numOfSlots, ItemStack.EMPTY);
            this.name = "container."+stack.getDisplayName();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(index > this.list.size() - 1) {
            return ItemStack.EMPTY;
        }
        return this.list.get(index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if(this.list.get(index).isEmpty()) {
            return ItemStack.EMPTY;
        }

        // Remove the stack from our list and return the removed stack
        ItemStack stack = this.list.get(index);
        this.list.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (!this.list.get(index).isEmpty())
        {
            ItemStack itemstack;

            if (this.list.get(index).getCount() <= count)
            {
                itemstack = this.list.get(index);
                this.list.set(index, ItemStack.EMPTY);
                this.markDirty();
                this.container.onCraftMatrixChanged(this);
                return itemstack;
            }
            itemstack = this.list.get(index).splitStack(count);

            if (this.list.get(index).getCount() == 0)
            {
                this.list.set(index, ItemStack.EMPTY);
            }

            this.container.onCraftMatrixChanged(this);
            return itemstack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void markDirty() {
        if(!stack.isEmpty()) {
            // TODO Save items when dirty
            // ItemDeepLearner deepLearner = this.stack.getItem().setContainedItems(stack, list);
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.list.set(index, stack);

        if(!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.container.onCraftMatrixChanged(this);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // DeepLearnerSlot slot = (DeepLearnerSlot) this.container.getSlot(index);
        return true;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack: this.list) {
            if(!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ITextComponent getDisplayName() { return new TextComponentString(this.stack.getDisplayName()); }
    @Override
    public boolean isUsableByPlayer(EntityPlayer player) { return true; }
    @Override
    public int getSizeInventory() { return this.list.size(); }
    @Override
    public int getInventoryStackLimit() { return 64; }

    // Not used
    @Override
    public int getFieldCount() { return 0; }
    @Override
    public void setField(int id, int value) {}
    @Override
    public int getField(int id) { return 0; }
    public boolean hasCustomName() { return false; }
    @Override
    public void clear() {}
    @Override
    public void openInventory(EntityPlayer player) {}
    @Override
    public void closeInventory(EntityPlayer player) {}
}
