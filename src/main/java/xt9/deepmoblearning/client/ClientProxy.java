package xt9.deepmoblearning.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import xt9.deepmoblearning.Constants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.client.gui.GuiDeepLearner;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.items.ItemBase;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        if(item instanceof ItemBase) {
            ItemBase itemBase = (ItemBase) item;
            ResourceLocation location;

            if(itemBase.hasSubTypes()) {
                location = new ResourceLocation(DeepMobLearning.MODID, id + "/" + itemBase.getSubNames()[meta]);
            } else {
                location = new ResourceLocation(DeepMobLearning.MODID, id);
            }

            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        switch (ID % 100) {
            case Constants.ITEM_DEEP_LEARNER_GUI_ID:
                return new GuiDeepLearner(player.inventory, world, item);
            default:
                return null;
        }
    }
}
