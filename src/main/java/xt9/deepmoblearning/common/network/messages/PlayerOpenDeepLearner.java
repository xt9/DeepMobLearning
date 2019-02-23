package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2019-02-23.
 */
public class PlayerOpenDeepLearner {
    public PlayerOpenDeepLearner() {
    }

    public static PlayerOpenDeepLearner decode(PacketBuffer buf) {
        return new PlayerOpenDeepLearner();
    }

    public static void encode(PlayerOpenDeepLearner msg, PacketBuffer buf) {}

    public static void handle(PlayerOpenDeepLearner message, Supplier<NetworkEvent.Context> ctx) {
        EntityPlayerMP player = ctx.get().getSender();
        if (player == null) {
            return;
        }
        Item mainHand = player.getHeldItemMainhand().getItem();

        ItemStack heldItem = mainHand instanceof ItemDeepLearner ? player.getHeldItem(EnumHand.MAIN_HAND) : player.getHeldItem(EnumHand.OFF_HAND);
        if(heldItem.getItem() instanceof ItemDeepLearner) {
            NetworkHooks.openGui(player, (IInteractionObject) heldItem.getItem(), (buf) -> buf.writeItemStack(heldItem));
        }
        ctx.get().setPacketHandled(true);
    }
}
