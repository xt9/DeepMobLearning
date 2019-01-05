package xt9.deepmoblearning.common.events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.client.gui.TrialOverlay;
import xt9.deepmoblearning.common.capabilities.IPlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;
import xt9.deepmoblearning.common.util.PlayerHelper;

/**
 * Created by xt9 on 2018-04-07.
 */
@Mod.EventBusSubscriber
public class CapabilityHandler {
    public static final ResourceLocation PLAYER_TRIAL_CAP = new ResourceLocation(DeepConstants.MODID, "player_trial");


    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(PLAYER_TRIAL_CAP, new PlayerTrialProvider());
        }
    }

    @SubscribeEvent
    @SuppressWarnings({"ConstantConditions", "unused"})
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();

        if(!player.world.isRemote) {
            IPlayerTrial cap = player.getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);

            IPlayerTrial oldCap = event.getOriginal().getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);

            cap.setDefeated(oldCap.getDefated());
            cap.setCurrentWave(oldCap.getCurrentWave());
            cap.setLastWave(oldCap.getLastWave());
            cap.setWaveMobTotal(oldCap.getWaveMobTotal());
            cap.setTilePos(oldCap.getTilePos());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onPlayerJoinedWorldClient(EntityJoinWorldEvent event) {
        /* Re-init the player capability when player joins world */
        if(event.getEntity() instanceof EntityPlayerSP) {
            TrialOverlay.initPlayerCapability();
        }
    }
}
