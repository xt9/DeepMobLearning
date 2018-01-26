package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemLivingMatter;
import xt9.deepmoblearning.common.util.MathHelper;

/**
 * Created by xt9 on 2018-01-25.
 */
public class ConsumeLivingMatterMessage implements IMessage {
    private boolean consumeStack;

    public ConsumeLivingMatterMessage() {}

    public ConsumeLivingMatterMessage(boolean consumeStack) {
        this.consumeStack = consumeStack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        consumeStack = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(consumeStack);
    }

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<ConsumeLivingMatterMessage, IMessage> {

        @Override
        public IMessage onMessage(ConsumeLivingMatterMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            ItemStack mainHand = player.getHeldItemMainhand();
            ItemStack offHand = player.getHeldItemOffhand();

            // Make sure the player actually is holding a creative model learner and isn't spoofing
            if (mainHand.getItem() instanceof ItemLivingMatter) {
                player.getServerWorld().addScheduledTask(() -> consumeMatter(mainHand, message.consumeStack, player));
            } else if(offHand.getItem() instanceof ItemLivingMatter) {
                player.getServerWorld().addScheduledTask(() -> consumeMatter(offHand, message.consumeStack, player));
            }

            return null;
        }

        private void consumeMatter(ItemStack matter, boolean consumeStack, EntityPlayerMP player) {
            ItemLivingMatter item = (ItemLivingMatter) matter.getItem();
            int exp = MathHelper.ensureRange(Config.livingMatterEXP.get(item.getType()).getInt(), 1, 999);

            if(consumeStack) {
                int size = matter.getCount();
                matter.shrink(matter.getCount());
                player.addExperience(exp * size);
            } else {
                matter.shrink(1);
                player.addExperience(exp);
            }
        }
    }
}
