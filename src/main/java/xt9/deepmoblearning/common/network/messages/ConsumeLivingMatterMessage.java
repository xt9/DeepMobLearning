package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.items.ItemLivingMatter;
import xt9.deepmoblearning.common.util.MathHelper;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-01-25.
 */
public class ConsumeLivingMatterMessage {
    private boolean consumeStack;

    public ConsumeLivingMatterMessage(boolean consumeStack) {
        this.consumeStack = consumeStack;
    }

    public static ConsumeLivingMatterMessage decode(PacketBuffer buf) {
        return new ConsumeLivingMatterMessage(buf.readBoolean());
    }

    public static void encode(ConsumeLivingMatterMessage msg, PacketBuffer buf) {
        buf.writeBoolean(msg.consumeStack);
    }

    public static void handle(ConsumeLivingMatterMessage message, Supplier<NetworkEvent.Context> ctx) {
        EntityPlayerMP player = ctx.get().getSender();
        if (player == null) {
            return;
        }

        ItemStack mainHand = player.getHeldItemMainhand();
        ItemStack offHand = player.getHeldItemOffhand();

        // Make sure the player actually is holding a creative model learner and isn't spoofing
        if (mainHand.getItem() instanceof ItemLivingMatter) {
            ctx.get().enqueueWork(() -> consumeMatter(mainHand, message.consumeStack, player));
        } else if(offHand.getItem() instanceof ItemLivingMatter) {
            ctx.get().enqueueWork(() -> consumeMatter(offHand, message.consumeStack, player));
        }
        ctx.get().setPacketHandled(true);
    }

    private static void consumeMatter(ItemStack matter, boolean consumeStack, EntityPlayerMP player) {
        ItemLivingMatter item = (ItemLivingMatter) matter.getItem();
        int exp = MathHelper.ensureRange(Config.livingMatterEXP.get(item.getType()), 1, 999);

        if(consumeStack) {
            int size = matter.getCount();
            matter.shrink(matter.getCount());
            player.giveExperiencePoints(exp * size);
        } else {
            matter.shrink(1);
            player.giveExperiencePoints(exp);
        }
    }
}
