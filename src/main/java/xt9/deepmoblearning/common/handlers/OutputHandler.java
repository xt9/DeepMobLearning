package xt9.deepmoblearning.common.handlers;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2017-06-19.
 */
public class OutputHandler extends BaseItemHandler {
    public OutputHandler() {
        super();
    }

    public OutputHandler(int size) {
        super(size);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return stack;
    }
}
