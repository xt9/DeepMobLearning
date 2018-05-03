package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.items.ItemLivingMatter;

/**
 * Created by xt9 on 2018-03-24.
 */
public class CombatTrialStartMessage implements IMessage {
    public CombatTrialStartMessage() {}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<CombatTrialStartMessage, IMessage> {

        @Override
        public IMessage onMessage(CombatTrialStartMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            System.out.println("Trial started");

            ItemStack mainHand = player.getHeldItemMainhand();
            ItemStack offHand = player.getHeldItemOffhand();

            // Make sure the player actually is holding a creative model learner and isn't spoofing
            if (mainHand.getItem() instanceof ItemLivingMatter) {
                player.getServerWorld().addScheduledTask(() -> startTrial(mainHand, player));
            } else if(offHand.getItem() instanceof ItemLivingMatter) {
                player.getServerWorld().addScheduledTask(() -> startTrial(offHand, player));
            }

            return null;
        }

        private void startTrial(ItemStack matter, EntityPlayerMP player) {

        }
    }
}
