package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;

/**
 * Created by xt9 on 2018-01-09.
 */
public class ExtractorSetSelectedItemMessage implements IMessage {

    private int index;

    public ExtractorSetSelectedItemMessage() {}

    public ExtractorSetSelectedItemMessage(int index) {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
    }

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<ExtractorSetSelectedItemMessage, IMessage> {

        @Override
        public IMessage onMessage(ExtractorSetSelectedItemMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                if(player.openContainer instanceof ContainerExtractionChamber) {
                    ContainerExtractionChamber container = (ContainerExtractionChamber) player.openContainer;

                    container.tile.finishCraft(true);
                    container.tile.resultingItem = container.tile.getItemFromList(message.index);
                    container.tile.updateState();
                }
            });

            // System.out.println(String.format("Received %s from %s", message.index, ctx.getServerHandler().player.getName()));
            return null;
        }
    }
}
