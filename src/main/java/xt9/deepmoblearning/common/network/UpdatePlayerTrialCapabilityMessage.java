package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;

/**
 * Created by xt9 on 2018-04-07.
 */
public class UpdatePlayerTrialCapabilityMessage implements IMessage {
    private NBTTagCompound compound;

    public UpdatePlayerTrialCapabilityMessage() {}

    public UpdatePlayerTrialCapabilityMessage(PlayerTrial instance) {
        compound = instance.writeNBT(PlayerTrialProvider.PLAYER_TRIAL_CAP, instance, null);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, compound);
    }


    public static class Handler implements IMessageHandler<UpdatePlayerTrialCapabilityMessage, IMessage> {

        @Override
        public IMessage onMessage(UpdatePlayerTrialCapabilityMessage message, MessageContext ctx) {
            PlayerTrial clientCapability = (PlayerTrial) DeepMobLearning.proxy.getClientPlayerTrialCapability();

            clientCapability.readNBT(PlayerTrialProvider.PLAYER_TRIAL_CAP, clientCapability, null, message.compound);
            return null;
        }
    }
}
