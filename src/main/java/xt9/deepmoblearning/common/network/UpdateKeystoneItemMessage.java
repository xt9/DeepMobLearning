package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

/**
 * Created by xt9 on 2018-03-31.
 */
public class UpdateKeystoneItemMessage implements IMessage {
    private BlockPos pos;
    private ItemStack stack;


    public UpdateKeystoneItemMessage() {

    }

    public UpdateKeystoneItemMessage(TileEntityTrialKeystone tile) {
        pos = tile.getPos();
        stack = tile.trialKey.getStackInSlot(0);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
    }

    public static class Handler implements IMessageHandler<UpdateKeystoneItemMessage, IMessage> {

        @Override
        public IMessage onMessage(UpdateKeystoneItemMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityTrialKeystone te = (TileEntityTrialKeystone) Minecraft.getMinecraft().world.getTileEntity(message.pos);

                if(te != null) {
                    te.trialKey.setStackInSlot(0, message.stack);
                }
            });
            return null;
        }
    }
}
