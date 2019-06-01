package xt9.deepmoblearning.common.events;

import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.common.entity.EntityTrialEnderman;

/**
 * Created by xt9 on 2019-05-31.
 */
@Mod.EventBusSubscriber
public class TrialEndermanTeleport {
    @SubscribeEvent
    public static void onEndermanTeleport(EnderTeleportEvent event) {
        if (event.getEntityLiving() instanceof EntityTrialEnderman) {
            event.setCanceled(true);
        }
    }
}
