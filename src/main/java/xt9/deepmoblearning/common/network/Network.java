package xt9.deepmoblearning.common.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.network.messages.*;

/**
 * Created by xt9 on 2019-02-17.
 */
public class Network {
    private static int networkID = 0;
    private static final String version = Integer.toString(1);
    public static final SimpleChannel channel = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(DeepConstants.MODID, "main"))
        .clientAcceptedVersions(version::equals)
        .serverAcceptedVersions(version::equals)
        .networkProtocolVersion(() -> version)
        .simpleChannel();

    public static void register() {
        // Init network messages
        channel.registerMessage(
            networkID++,
            PlayerOpenDeepLearner.class,
            PlayerOpenDeepLearner::encode,
            PlayerOpenDeepLearner::decode,
            PlayerOpenDeepLearner::handle
        );

        channel.registerMessage(
            networkID++,
            ExtractorSetSelectedItemMessage.class,
            ExtractorSetSelectedItemMessage::encode,
            ExtractorSetSelectedItemMessage::decode,
            ExtractorSetSelectedItemMessage::handle
        );

        channel.registerMessage(
            networkID++,
            ExtractionChamberChangePageMessage.class,
            ExtractionChamberChangePageMessage::encode,
            ExtractionChamberChangePageMessage::decode,
            ExtractionChamberChangePageMessage::handle
        );

        channel.registerMessage(
            networkID++,
            LevelUpModelMessage.class,
            LevelUpModelMessage::encode,
            LevelUpModelMessage::decode,
            LevelUpModelMessage::handle
        );

        channel.registerMessage(
            networkID++,
            ConsumeLivingMatterMessage.class,
            ConsumeLivingMatterMessage::encode,
            ConsumeLivingMatterMessage::decode,
            ConsumeLivingMatterMessage::handle
        );

        channel.registerMessage(
            networkID++,
            TrialStartMessage.class,
            TrialStartMessage::encode,
            TrialStartMessage::decode,
            TrialStartMessage::handle
        );

        channel.registerMessage(
            networkID++,
            UpdateKeystoneItemMessage.class,
            UpdateKeystoneItemMessage::encode,
            UpdateKeystoneItemMessage::decode,
            UpdateKeystoneItemMessage::handle
        );

        channel.registerMessage(
            networkID++,
            RequestKeystoneItemMessage.class,
            RequestKeystoneItemMessage::encode,
            RequestKeystoneItemMessage::decode,
            RequestKeystoneItemMessage::handle
        );

        channel.registerMessage(
            networkID++,
            UpdatePlayerTrialCapabilityMessage.class,
            UpdatePlayerTrialCapabilityMessage::encode,
            UpdatePlayerTrialCapabilityMessage::decode,
            UpdatePlayerTrialCapabilityMessage::handle
        );

        channel.registerMessage(
            networkID++,
            UpdateTrialOverlayMessage.class,
            UpdateTrialOverlayMessage::encode,
            UpdateTrialOverlayMessage::decode,
            UpdateTrialOverlayMessage::handle
        );

    }
}
