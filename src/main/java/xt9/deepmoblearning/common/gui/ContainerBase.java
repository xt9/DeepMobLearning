package xt9.deepmoblearning.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ContainerBase extends Container {
    public IInventory inv;

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inv.isUsableByPlayer(player);
    }
}
