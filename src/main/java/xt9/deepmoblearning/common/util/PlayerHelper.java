package xt9.deepmoblearning.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

/**
 * Created by xt9 on 2017-06-14.
 */
public class PlayerHelper {
    private boolean isHoldingDeepLearner;
    private EntityPlayer player;
    private ItemStack stack;

    public PlayerHelper(EntityPlayer player) {
        this.player = player;
        ItemStack mainHandStack = this.player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offHandStack = this.player.getHeldItem(EnumHand.OFF_HAND);

        if(mainHandStack.getItem() instanceof ItemDeepLearner) {
            this.stack = mainHandStack;
            this.isHoldingDeepLearner = true;
        } else if(offHandStack.getItem() instanceof ItemDeepLearner) {
            this.stack = offHandStack;
            this.isHoldingDeepLearner = true;
        } else {
            this.isHoldingDeepLearner = false;
            stack = ItemStack.EMPTY;
        }

    }

    public boolean isHoldingDeepLearner() {
        return this.isHoldingDeepLearner;
    }

    // Check if isHoldingDeepLearner first if you can't accept an Empty itemstack
    public ItemStack getHeldDeepLearner() {
        return this.stack;
    }
}
