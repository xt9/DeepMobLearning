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
import net.minecraftforge.registries.IForgeRegistry;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.network.ConsumeLivingMatterMessage;
import xt9.deepmoblearning.common.network.ExtractionChamberChangePageMessage;
import xt9.deepmoblearning.common.network.ExtractorSetSelectedItemMessage;
import xt9.deepmoblearning.common.network.LevelUpModelMessage;

@Mod(modid = DeepConstants.MODID, version = DeepConstants.VERSION, useMetadata = true, guiFactory = "xt9.deepmoblearning.client.gui.config.GuiFactory",
    dependencies = "after:jei;after:thermalfoundation;after:twilightforest;after:tconstruct", acceptedMinecraftVersions = "[1.12,1.12.2]")
@Mod.EventBusSubscriber
public class DeepMobLearning {

    @Mod.Instance(DeepConstants.MODID)
    public static DeepMobLearning instance;

    @SidedProxy(clientSide="xt9.deepmoblearning.client.ClientProxy", serverSide="xt9.deepmoblearning.common.CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load();
        Config.initConfigValues();

        network = NetworkRegistry.INSTANCE.newSimpleChannel(DeepConstants.MODID);
        network.registerMessage(ExtractorSetSelectedItemMessage.Handler.class, ExtractorSetSelectedItemMessage.class, 0, Side.SERVER);
        network.registerMessage(ExtractionChamberChangePageMessage.Handler.class, ExtractionChamberChangePageMessage.class, 1, Side.SERVER);
        network.registerMessage(LevelUpModelMessage.Handler.class, LevelUpModelMessage.class, 2, Side.SERVER);
        network.registerMessage(ConsumeLivingMatterMessage.Handler.class, ConsumeLivingMatterMessage.class, 3, Side.SERVER);
        proxy.preInit();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry registry = event.getRegistry();
        Registry.registerBlocks(registry);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry registry = event.getRegistry();
        Registry.registerItems(registry);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Registry.registerItemModels();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

        proxy.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        // Register block, item, and entity renderers after they have been initialized and
        // registered in pre-init; however, Minecraft's RenderItem and ModelMesher instances
        // must also be ready, so we have to register renderers during init, not earlier
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
            return new ItemStack(Registry.deepLearner, 1);
        }
    };
}
