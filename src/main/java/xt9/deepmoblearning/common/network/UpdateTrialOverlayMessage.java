package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.client.gui.TrialOverlay;

/**
 * Created by xt9 on 2018-04-13.
 */
public class UpdateTrialOverlayMessage implements IMessage {
    private String type;

    public UpdateTrialOverlayMessage() {

    }

    public UpdateTrialOverlayMessage(String type) {
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, type);
    }


    public static class Handler implements IMessageHandler<UpdateTrialOverlayMessage, IMessage> {

        @Override
        public IMessage onMessage(UpdateTrialOverlayMessage message, MessageContext ctx) {
            TrialOverlay.handleMessage(message.type);

            return null;
        }
    }
}
