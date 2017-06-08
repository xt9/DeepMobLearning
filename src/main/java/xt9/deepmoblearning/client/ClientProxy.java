package xt9.deepmoblearning.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import xt9.deepmoblearning.Constants;
import xt9.deepmoblearning.client.gui.DeepLearnerGui;
import xt9.deepmoblearning.common.CommonProxy;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case Constants.ITEM_DEEP_LEARNER_GUI_ID:
                return new DeepLearnerGui(world);
            default:
                return null;
        }
    }
}
