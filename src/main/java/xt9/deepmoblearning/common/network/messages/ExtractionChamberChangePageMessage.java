package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-01-13.
 */
public class ExtractionChamberChangePageMessage {
    private String type;

    public ExtractionChamberChangePageMessage(String type) {
        this.type = type;
    }

    public static ExtractionChamberChangePageMessage decode(PacketBuffer buf) {
        return new ExtractionChamberChangePageMessage(buf.readString(20));
    }

    public static void encode(ExtractionChamberChangePageMessage msg, PacketBuffer buf) {
        buf.writeString(msg.type);
    }

    public static void handle(ExtractionChamberChangePageMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EntityPlayerMP player = ctx.get().getSender();
            if (player == null) {
                return;
            }

            if(player.openContainer instanceof ContainerExtractionChamber) {
                ContainerExtractionChamber container = (ContainerExtractionChamber) player.openContainer;
                if(message.type.equals("increase")) {
                    container.tile.pageHandler.increase();
                } else if(message.type.equals("decrease")) {
                    container.tile.pageHandler.decrease();
                }

                container.tile.updateState();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
