package xt9.deepmoblearning.common.network.messages;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.client.gui.TrialOverlay;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-04-13.
 */
public class UpdateTrialOverlayMessage {
    private String type;

    public UpdateTrialOverlayMessage(String type) {
        this.type = type;
    }


    public static UpdateTrialOverlayMessage decode(PacketBuffer buf) {
        return new UpdateTrialOverlayMessage(buf.readString(20));
    }

    public static void encode(UpdateTrialOverlayMessage msg, PacketBuffer buf) {
        buf.writeString(msg.type);
    }


    public static void handle(UpdateTrialOverlayMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> TrialOverlay.handleMessage(message.type));
        ctx.get().setPacketHandled(true);
    }
}
