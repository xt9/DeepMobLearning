package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;

/**
 * Created by xt9 on 2018-04-07.
 */
public class UpdatePlayerTrialCapability implements IMessage {
    private NBTTagCompound compound;

    public UpdatePlayerTrialCapability() {}

    public UpdatePlayerTrialCapability(PlayerTrial instance) {
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


    public class Handler implements IMessageHandler<UpdatePlayerTrialCapability, IMessage> {

        @Override
        public IMessage onMessage(UpdatePlayerTrialCapability message, MessageContext context) {
            return null;
        }
    }
}
