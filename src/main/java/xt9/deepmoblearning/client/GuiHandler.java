package xt9.deepmoblearning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import xt9.deepmoblearning.client.gui.DeepLearnerGui;
import xt9.deepmoblearning.client.gui.ExtractionChamberGui;
import xt9.deepmoblearning.client.gui.SimulationChamberGui;
import xt9.deepmoblearning.client.gui.TrialKeystoneGui;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2019-02-23.
 */
public class GuiHandler {
    @Nullable
    public static GuiScreen getClientGuiElement(FMLPlayMessages.OpenContainer container) {
        PacketBuffer buf = container.getAdditionalData();
        EntityPlayer player = Minecraft.getInstance().player;

        if (container.getId().toString().contains("item/")) {
            ItemStack heldItem = buf.readItemStack();
            return new DeepLearnerGui(player.inventory, heldItem);
        } else if (container.getId().toString().contains("tile/")) {
            BlockPos blockPos = buf.readBlockPos();
            TileEntity tile = player.world.getTileEntity(blockPos);
            if (tile instanceof TileEntitySimulationChamber) {
                return new SimulationChamberGui((TileEntitySimulationChamber) tile, player.inventory, player.world);
            } else if (tile instanceof TileEntityExtractionChamber) {
                return new ExtractionChamberGui((TileEntityExtractionChamber) tile, player.inventory, player.world);
            } else if (tile instanceof TileEntityTrialKeystone) {
                return new TrialKeystoneGui((TileEntityTrialKeystone) tile, player.inventory, player.world);
            }
        }
        return null;
    }
}
