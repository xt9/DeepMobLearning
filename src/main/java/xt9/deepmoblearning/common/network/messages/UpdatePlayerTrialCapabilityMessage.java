package xt9.deepmoblearning.common.network.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-04-07.
 */
public class UpdatePlayerTrialCapabilityMessage {
    private NBTTagCompound compound;

    public UpdatePlayerTrialCapabilityMessage(NBTTagCompound compound) {
        this.compound = compound;
    }

    public static UpdatePlayerTrialCapabilityMessage decode(PacketBuffer buf) {
        return new UpdatePlayerTrialCapabilityMessage(buf.readCompoundTag());
    }

    public static void encode(UpdatePlayerTrialCapabilityMessage msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.compound);
    }

    public static void handle(UpdatePlayerTrialCapabilityMessage message, Supplier<NetworkEvent.Context> ctx) {
        PlayerTrial clientCapability = DeepMobLearning.proxy.getTrialCapability(Minecraft.getInstance().player);
        ctx.get().enqueueWork(() -> clientCapability.read(message.compound));
        ctx.get().setPacketHandled(true);
    }
}
