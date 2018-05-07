package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

/**
 * Created by xt9 on 2018-03-31.
 */
public class RequestKeystoneItemMessage implements IMessage {
    private BlockPos pos;
    private int dimension;

    public RequestKeystoneItemMessage () {

    }

    public RequestKeystoneItemMessage (TileEntityTrialKeystone tile) {
        pos = tile.getPos();
        dimension = tile.getWorld().provider.getDimension();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    public static class Handler implements IMessageHandler<RequestKeystoneItemMessage, IMessage> {
        @Override
        public IMessage onMessage(RequestKeystoneItemMessage message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntityTrialKeystone te = (TileEntityTrialKeystone) world.getTileEntity(message.pos);
            if (te != null) {
                return new UpdateKeystoneItemMessage(te);
            } else {
                return null;
            }
        }
    }

}
