package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.inventory.ContainerTrialKeystone;
import xt9.deepmoblearning.common.util.PlayerHelper;

/**
 * Created by xt9 on 2018-03-24.
 */
public class TrialStartMessage implements IMessage {
    public TrialStartMessage() {}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<TrialStartMessage, IMessage> {

        @Override
        public IMessage onMessage(TrialStartMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                if(player.openContainer instanceof ContainerTrialKeystone) {
                    ContainerTrialKeystone container = (ContainerTrialKeystone) player.openContainer;
                    container.tile.startTrial();
                }
            });
            return null;
        }
    }
}
