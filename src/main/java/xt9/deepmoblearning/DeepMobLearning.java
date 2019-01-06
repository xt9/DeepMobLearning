package xt9.deepmoblearning;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.patchouli.client.book.ClientBookRegistry;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.network.*;

@Mod(modid = DeepConstants.MODID, version = DeepConstants.VERSION, useMetadata = true, guiFactory = "xt9.deepmoblearning.client.gui.config.GuiFactory",
    dependencies = "after:jei;after:thermalfoundation;after:twilightforest;after:tconstruct;after:patchouli")
@Mod.EventBusSubscriber
public class DeepMobLearning {
    private int networkID = 0;

    @Mod.Instance(DeepConstants.MODID)
    public static DeepMobLearning instance;

    @SidedProxy(
        clientSide="xt9.deepmoblearning.client.ClientProxy",
        serverSide="xt9.deepmoblearning.common.CommonProxy"
    )
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load();
        Config.initConfigValues();

        // Init network messages
        network = NetworkRegistry.INSTANCE.newSimpleChannel(DeepConstants.MODID);
        network.registerMessage(ExtractorSetSelectedItemMessage.Handler.class, ExtractorSetSelectedItemMessage.class, networkID++, Side.SERVER);
        network.registerMessage(ExtractionChamberChangePageMessage.Handler.class, ExtractionChamberChangePageMessage.class, networkID++, Side.SERVER);
        network.registerMessage(LevelUpModelMessage.Handler.class, LevelUpModelMessage.class, networkID++, Side.SERVER);
        network.registerMessage(ConsumeLivingMatterMessage.Handler.class, ConsumeLivingMatterMessage.class, networkID++, Side.SERVER);

        network.registerMessage(TrialStartMessage.Handler.class, TrialStartMessage.class, networkID++, Side.SERVER);

        network.registerMessage(UpdateKeystoneItemMessage.Handler.class, UpdateKeystoneItemMessage.class, networkID++, Side.CLIENT);
        network.registerMessage(RequestKeystoneItemMessage.Handler.class, RequestKeystoneItemMessage.class, networkID++, Side.SERVER);

        network.registerMessage(UpdatePlayerTrialCapabilityMessage.Handler.class, UpdatePlayerTrialCapabilityMessage.class, networkID++, Side.CLIENT);
        network.registerMessage(UpdateTrialOverlayMessage.Handler.class, UpdateTrialOverlayMessage.class, networkID++, Side.CLIENT);

        // Init capabilities
        PlayerTrial.init();

        proxy.preInit();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Registry.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Registry.registerItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Registry.registerItemModels();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        proxy.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        // Register block, item, and particle renders after they have been initialized and
        // registered in pre-init; however, Minecraft's RenderItem and ModelMesher instances
        // must also be ready, so we have to register renders during init, not earlier
        proxy.registerRenderers();
    }


    public static CreativeTabs creativeTab = new CreativeTabs(DeepConstants.MODID) {

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getTabIconItem() {
            return ItemStack.EMPTY;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Registry.deepLearner);
        }
    };
}
