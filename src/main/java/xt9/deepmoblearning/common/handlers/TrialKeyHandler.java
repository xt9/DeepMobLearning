package xt9.deepmoblearning.common.handlers;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.common.items.ItemTrialKey;

import javax.annotation.Nonnull;

/**
 * Created by xt9 on 2018-03-31.
 */
public class TrialKeyHandler extends BaseItemHandler {
    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(stack.getItem() instanceof ItemTrialKey) {
            return super.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }
    }
}
