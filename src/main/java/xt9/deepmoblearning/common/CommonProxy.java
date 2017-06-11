package xt9.deepmoblearning.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import xt9.deepmoblearning.Constants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.client.gui.GuiDeepLearner;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.items.IGuiItem;


/**
 * Created by xt9 on 2017-06-08.
 */
public class CommonProxy implements IGuiHandler {
    public static boolean openItemGui(EntityPlayer player, EntityEquipmentSlot slot) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        IGuiItem item = (IGuiItem)stack.getItem();

        if(stack.isEmpty() || !(stack.getItem() instanceof IGuiItem)) {
            return false;
        }
        int slotID = slot.getName() == "mainhand" ? 0 : 1;

        player.openGui(DeepMobLearning.instance, 100 * slotID + item.getGuiID(), player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return true;
    }

    public void preInit() {

    }

    public void init() {

    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        switch (ID % 100) {
            case Constants.ITEM_DEEP_LEARNER_GUI_ID:
                return new ContainerDeepLearner(player.inventory, world, item);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
