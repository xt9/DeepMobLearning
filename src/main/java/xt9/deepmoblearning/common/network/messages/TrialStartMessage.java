package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.common.inventory.ContainerTrialKeystone;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-03-24.
 */
public class TrialStartMessage {
    public TrialStartMessage() {}

    public static TrialStartMessage decode(PacketBuffer buf) {
        return new TrialStartMessage();
    }

    public static void encode(TrialStartMessage msg, PacketBuffer buf) {}

    public static void handle(TrialStartMessage message, Supplier<NetworkEvent.Context> ctx) {
        EntityPlayerMP player = ctx.get().getSender();
        if (player == null) {
            return;
        }

        ctx.get().enqueueWork(() -> {
            if(player.openContainer instanceof ContainerTrialKeystone) {
                ContainerTrialKeystone container = (ContainerTrialKeystone) player.openContainer;
                container.tile.startTrial();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
