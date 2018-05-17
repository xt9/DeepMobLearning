package xt9.deepmoblearning.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.items.ItemCreativeModelLearner;
import xt9.deepmoblearning.common.items.ItemDataModel;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.util.DataModel;

/**
 * Created by xt9 on 2018-01-25.
 */
public class LevelUpModelMessage implements IMessage {
    private int index;

    public LevelUpModelMessage() {}

    public LevelUpModelMessage(int index) {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
    }

    /* Handler for the message, registered in the mod class */
    public static class Handler implements IMessageHandler<LevelUpModelMessage, IMessage> {

        @Override
        public IMessage onMessage(LevelUpModelMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            Item mainHand = player.getHeldItemMainhand().getItem();
            Item offHand = player.getHeldItemOffhand().getItem();

            // Make sure the player actually is holding a creative model learner and isn't spoofing
            if(mainHand instanceof ItemCreativeModelLearner || offHand instanceof ItemCreativeModelLearner) {
                player.getServerWorld().addScheduledTask(() -> {
                    findAndLevelUpModels(player.inventory.mainInventory, player, message.index);
                    findAndLevelUpModels(player.inventory.offHandInventory, player, message.index);
                });
            }

            return null;
        }

        public void findAndLevelUpModels(NonNullList<ItemStack> inventory, EntityPlayerMP player, int action) {
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
}
