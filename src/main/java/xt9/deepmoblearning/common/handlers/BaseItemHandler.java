package xt9.deepmoblearning.common.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by xt9 on 2017-06-19.
 */
public class BaseItemHandler extends ItemStackHandler {
    private int size;

    public BaseItemHandler() {
        super();
    }

    public BaseItemHandler(int size) {
        super(size);
        this.size = size;
    }

    public BaseItemHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public NonNullList<ItemStack> getItemStacks() {
        NonNullList<ItemStack> list = NonNullList.create();
        int numOfSlots  = getSlots();
        for (int i = 0; i < numOfSlots; i++) {
            list.add(i, getStackInSlot(i));
        }
        return list;
    }

    public boolean canInsertItem(ItemStack stack) {
        int availableSlots = 0;

        for(int i = 0; i < size; i++) {
            if(getStackInSlot(i).isEmpty()) {
                availableSlots = availableSlots + stack.getMaxStackSize();
            } else if(ItemStack.areItemsEqual(getStackInSlot(i), stack)) {
                availableSlots = availableSlots + stack.getMaxStackSize() - getStackInSlot(i).getCount();
            }
        }

        return availableSlots >= stack.getCount();
    }

    public ItemStack setInFirstAvailableSlot(ItemStack stack) {
        ItemStack remainder = ItemStack.EMPTY;

        for(int i = 0; i < size; i++) {
            if(getStackInSlot(i).isEmpty()) {
                setStackInSlot(i, stack.copy());
                return remainder;
            } else if(ItemHandlerHelper.canItemStacksStack(getStackInSlot(i), stack) && getStackInSlot(i).getCount() < getStackInSlot(i).getMaxStackSize()) {
                ItemStack itemInSlot = getStackInSlot(i);
                int available = itemInSlot.getMaxStackSize() - itemInSlot.getCount();
                if(stack.getCount() <= available) {
                    itemInSlot.grow(stack.getCount());
                    return remainder;
                } else {
                    int newSize = stack.getCount() - available;
                    itemInSlot.grow(available);
                    return ItemHandlerHelper.copyStackWithSize(stack, newSize);
                }
            }
        }

        return remainder;

    }
}
