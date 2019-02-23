package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-03-31.
 */
public class RequestKeystoneItemMessage {
    private BlockPos pos;
    private int dimension;

    public RequestKeystoneItemMessage(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public static RequestKeystoneItemMessage decode(PacketBuffer buf) {
        return new RequestKeystoneItemMessage(buf.readBlockPos(), buf.readInt());
    }

    public static void encode(RequestKeystoneItemMessage msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.dimension);
    }

    public static void handle(RequestKeystoneItemMessage message, Supplier<NetworkEvent.Context> ctx) {
        EntityPlayerMP player = ctx.get().getSender();
        if (player == null) {
            return;
        }

        DimensionType dimensionType = DimensionType.getById(message.dimension);

        if(dimensionType != null) {
            World world = ServerLifecycleHooks.getCurrentServer().getWorld(dimensionType);
            TileEntityTrialKeystone te = (TileEntityTrialKeystone) world.getTileEntity(message.pos);
            if (te != null) {
                ctx.get().enqueueWork(() -> Network.channel.sendTo(new UpdateKeystoneItemMessage(te.getPos(), te.trialKey.getStackInSlot(0)), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT));
            }
        }
        ctx.get().setPacketHandled(true);
    }

}
