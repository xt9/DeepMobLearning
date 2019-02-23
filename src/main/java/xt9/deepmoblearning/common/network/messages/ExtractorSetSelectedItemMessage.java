package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-01-09.
 */
public class ExtractorSetSelectedItemMessage {
    private int index;

    public ExtractorSetSelectedItemMessage(int index) {
        this.index = index;
    }

    public static ExtractorSetSelectedItemMessage decode(PacketBuffer buf) {
        return new ExtractorSetSelectedItemMessage(buf.readInt());
    }

    public static void encode(ExtractorSetSelectedItemMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.index);
    }

    public static void handle(ExtractorSetSelectedItemMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayerMP player = ctx.get().getSender();
            if (player == null) {
                return;
            }

            if(player.openContainer instanceof ContainerExtractionChamber) {
                ContainerExtractionChamber container = (ContainerExtractionChamber) player.openContainer;

                container.tile.finishCraft(true);
                container.tile.resultingItem = container.tile.getItemFromList(message.index);
                container.tile.updateState();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
