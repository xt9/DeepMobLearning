package xt9.deepmoblearning.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Created by xt9 on 2019-02-18.
 */
public class PlayerProperties {
    @CapabilityInject(PlayerTrial.class)
    public static Capability<PlayerTrial> PLAYER_TRIAL_CAP;
}
