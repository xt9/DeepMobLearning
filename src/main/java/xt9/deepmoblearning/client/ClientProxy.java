package xt9.deepmoblearning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.client.gui.DataModelExperienceGui;
import xt9.deepmoblearning.client.gui.DeepLearnerGui;
import xt9.deepmoblearning.client.gui.ExtractionChamberGui;
import xt9.deepmoblearning.client.gui.SimulationChamberGui;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.items.ItemBase;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy extends CommonProxy {
    public void registerRenderers() {
        MinecraftForge.EVENT_BUS.register(new DataModelExperienceGui(Minecraft.getMinecraft()));
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ResourceLocation location = new ResourceLocation(DeepConstants.MODID, id);

        if(item instanceof ItemBase) {
            ItemBase itemBase = (ItemBase) item;
        }

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        if(ID % 100 == DeepConstants.ITEM_DEEP_LEARNER_GUI_ID && item.getItem() instanceof ItemDeepLearner) {
            return new DeepLearnerGui(player.inventory, world, slot, item);
        } else {
            switch (ID) {
                case DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID:
                    return new SimulationChamberGui((TileEntitySimulationChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                case DeepConstants.TILE_EXTRACTION_CHAMBER_GUI_ID:
                    return new ExtractionChamberGui((TileEntityExtractionChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                default:
                    return null;
            }
        }
    }
}
