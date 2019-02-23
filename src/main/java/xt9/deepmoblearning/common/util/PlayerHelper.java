package xt9.deepmoblearning.common.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.network.messages.UpdateTrialOverlayMessage;
import java.util.UUID;

/**
 * Created by xt9 on 2017-06-14.
 */
public class PlayerHelper {
    public static java.util.List<EntityPlayerMP> getPlayersInArea(World world, BlockPos pos, int area, int yStart, int yEnd) {
        BlockPos point1 = new BlockPos(pos.getX() - area, yStart, pos.getZ() - area);
        BlockPos point2 = new BlockPos(pos.getX() + area, yEnd, pos.getZ() + area);
        return world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(point1, point2), EntityLivingBase::isAlive);
    }

    public static void sendMessageToOverlay(EntityPlayerMP player, String type) {
        if(player != null) {
            Network.channel.sendTo(new UpdateTrialOverlayMessage(type), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendMessage(EntityPlayerMP player, TextComponentString component) {
        if(player != null) {
            player.sendMessage(component);
        }
    }

    public static EntityPlayerMP getPlayerFromUUID(UUID uuid) {
        PlayerList list = ServerLifecycleHooks.getCurrentServer().getPlayerList();
        return list.getPlayerByUUID(uuid);
    }

    private boolean isHoldingDeepLearner;
    private EntityPlayer player;
    private ItemStack stack;

    public PlayerHelper(EntityPlayer player) {
        this.player = player;
        ItemStack mainHandStack = this.player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offHandStack = this.player.getHeldItem(EnumHand.OFF_HAND);

        if(mainHandStack.getItem() instanceof ItemDeepLearner) {
            this.stack = mainHandStack;
            this.isHoldingDeepLearner = true;
        } else if(offHandStack.getItem() instanceof ItemDeepLearner) {
            this.stack = offHandStack;
            this.isHoldingDeepLearner = true;
        } else {
            this.isHoldingDeepLearner = false;
            stack = ItemStack.EMPTY;
        }

    }


    public boolean isHoldingDeepLearner() {
        return isHoldingDeepLearner;
    }

    // Check if isHoldingDeepLearner first if you can't accept an Empty itemstack
    public ItemStack getHeldDeepLearner() {
        return stack;
    }
}
