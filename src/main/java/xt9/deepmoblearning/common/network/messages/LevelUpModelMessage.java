package xt9.deepmoblearning.common.network.messages;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.NetworkEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemCreativeModelLearner;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.util.DataModel;

import java.util.function.Supplier;

/**
 * Created by xt9 on 2018-01-25.
 */
public class LevelUpModelMessage {
    private int index;

    public LevelUpModelMessage(int index) {
        this.index = index;
    }

    public static LevelUpModelMessage decode(PacketBuffer buf) {
        return new LevelUpModelMessage(buf.readInt());
    }

    public static void encode(LevelUpModelMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.index);
    }

    public static void handle(LevelUpModelMessage message, Supplier<NetworkEvent.Context> ctx) {
        EntityPlayerMP player = ctx.get().getSender();
        if (player == null) {
            return;
        }

        Item mainHand = player.getHeldItemMainhand().getItem();
        Item offHand = player.getHeldItemOffhand().getItem();

        // Make sure the player actually is holding a creative model learner and isn't spoofing
        if(mainHand instanceof ItemCreativeModelLearner || offHand instanceof ItemCreativeModelLearner) {
            ctx.get().enqueueWork(() -> {
                findAndLevelUpModels(player.inventory.mainInventory, player, message.index);
                findAndLevelUpModels(player.inventory.offHandInventory, player, message.index);
            });
        }
        ctx.get().setPacketHandled(true);
    }

    private static void findAndLevelUpModels(NonNullList<ItemStack> inventory, EntityPlayerMP player, int action) {
        for (ItemStack inventoryStack : inventory) {
            if (inventoryStack.getItem() instanceof ItemDeepLearner) {
                NonNullList<ItemStack> deepLearnerInternalInv = ItemDeepLearner.getContainedItems(inventoryStack);
                for (ItemStack stack : deepLearnerInternalInv) {
                    if (stack.getItem() instanceof ItemDataModel) {
                        int tier = DataModel.getTier(stack);
                        if (tier != DeepConstants.DATA_MODEL_MAXIMUM_TIER) {
                            if (action == 0) {
                                DataModel.setTier(stack, (tier + 1));
                            } else {
                                DataModel.increaseMobKillCount(stack, player);
                            }
                        }

                    }
                    ItemDeepLearner.setContainedItems(inventoryStack, deepLearnerInternalInv);
                }
            }
        }
    }
}
