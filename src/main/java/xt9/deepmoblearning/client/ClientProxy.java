package xt9.deepmoblearning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.client.gui.ChipExperienceGui;
import xt9.deepmoblearning.client.gui.DeepLearnerGui;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.blocks.ItemBlockBase;
import xt9.deepmoblearning.common.items.ItemBase;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        MinecraftForge.EVENT_BUS.register(new ChipExperienceGui(Minecraft.getMinecraft()));
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ResourceLocation location = new ResourceLocation(DeepMobLearning.MODID, id);

        if(item instanceof ItemBase) {
            ItemBase itemBase = (ItemBase) item;

            if(itemBase.hasSubTypes()) {
                location = new ResourceLocation(DeepMobLearning.MODID, id + "/" + itemBase.getSubNames()[meta]);
            }
        }

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        switch (ID % 100) {
            case DeepConstants.ITEM_DEEP_LEARNER_GUI_ID:
                return new DeepLearnerGui(player.inventory, world, slot, item);
            default:
                return null;
        }
    }
}
