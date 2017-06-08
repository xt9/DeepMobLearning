package xt9.deepmoblearning.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.gui.ContainerBase;
import xt9.deepmoblearning.common.items.IGuiItem;


/**
 * Created by xt9 on 2017-06-08.
 */
public class CommonProxy implements IGuiHandler {
    public static boolean openItemGui(EntityPlayer player, ItemStack stack, int guiID) {
        if(stack == null || !(stack.getItem() instanceof IGuiItem)) {
            return false;
        }
        System.out.println(player.world.isRemote);
        player.openGui(DeepMobLearning.instance, guiID, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return true;
    }

    public void preInit() {

    }

    public void init() {

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerBase();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
