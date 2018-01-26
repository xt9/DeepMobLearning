package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;

/**
 * Created by xt9 on 2018-01-13.
 */
public class ExtractionChamberChangePageMessage implements IMessage {

    private String type;

    public ExtractionChamberChangePageMessage() {}

    public ExtractionChamberChangePageMessage(String type) {
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

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<ExtractionChamberChangePageMessage, IMessage> {

        @Override
        public IMessage onMessage(ExtractionChamberChangePageMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
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

            // System.out.println(String.format("Received %s from %s", message.index, ctx.getServerHandler().player.getName()));
            return null;
        }
    }
}
