package xt9.deepmoblearning.common.events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.client.gui.TrialOverlay;
import xt9.deepmoblearning.common.capabilities.PlayerProperties;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;

/**
 * Created by xt9 on 2018-04-07.
 */
@Mod.EventBusSubscriber
public class CapabilityHandler {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(DeepConstants.MODID, "player_trial"), new PlayerTrialProvider());
        }
    }

    @SubscribeEvent
    @SuppressWarnings({"ConstantConditions", "unused"})
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();

        if(!player.world.isRemote) {
            event.getOriginal()
                .getCapability(PlayerProperties.playerTrialCap)
                .ifPresent(oldCap -> player.getCapability(PlayerProperties.playerTrialCap).ifPresent(cap -> cap.copyFrom(oldCap)));
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerJoinedWorldClient(EntityJoinWorldEvent event) {
        /* Re-init the player capability when player joins world */
        if(event.getEntity() instanceof EntityPlayerSP) {
            TrialOverlay.initPlayerCapability();
        }
    }
}
