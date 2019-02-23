package xt9.deepmoblearning.common.network.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-03-31.
 */
public class UpdateKeystoneItemMessage {
    private BlockPos pos;
    private ItemStack stack;

    public UpdateKeystoneItemMessage(BlockPos pos, ItemStack stack) {
        this.pos = pos;
        this.stack = stack;
    }

    public static UpdateKeystoneItemMessage decode(PacketBuffer buf) {
        return new UpdateKeystoneItemMessage(buf.readBlockPos(), buf.readItemStack());
    }

    public static void encode(UpdateKeystoneItemMessage msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeItemStack(msg.stack);
    }

    public static void handle(UpdateKeystoneItemMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntityTrialKeystone te = (TileEntityTrialKeystone) Minecraft.getInstance().world.getTileEntity(message.pos);

            if (te != null) {
                te.trialKey.setStackInSlot(0, message.stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
