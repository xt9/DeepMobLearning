package xt9.deepmoblearning;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;
import xt9.deepmoblearning.client.ClientProxy;
import xt9.deepmoblearning.client.GuiHandler;
import xt9.deepmoblearning.client.gui.DataOverlay;
import xt9.deepmoblearning.client.gui.TrialOverlay;
import xt9.deepmoblearning.client.renders.TESRTrialKeystone;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.network.Network;
import xt9.deepmoblearning.common.proxy.IProxy;
import xt9.deepmoblearning.common.proxy.ServerProxy;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Mod(DeepConstants.MODID)
@Mod.EventBusSubscriber(modid = DeepConstants.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class DeepMobLearning {

    @SuppressWarnings("Convert2MethodRef")
    public static IProxy proxy = DistExecutor.runForDist(
        () -> () -> new ClientProxy(),
        () -> () -> new ServerProxy()
    );

    public DeepMobLearning() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::setupConfig);
        modEventBus.addListener(this::clientSetup);

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
    }

    /* Mod setup, run after registry events */
    private void setup(FMLCommonSetupEvent event) {
        Network.register();
        proxy.setup(event);

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getClientGuiElement);
    }

    private void setupConfig(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getSpec() == Config.CLIENT_SPEC) {
            Config.refreshClient();
        } else if(config.getSpec() == Config.COMMON_SPEC) {
            Config.refreshCommon();
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new DataOverlay());
        MinecraftForge.EVENT_BUS.register(new TrialOverlay());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrialKeystone.class, new TESRTrialKeystone());
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityType<?>> event) {
        Registry.registerEntities(event.getRegistry());
    }

    @SubscribeEvent
    public static void onTileEntityTypeRegister(RegistryEvent.Register<TileEntityType<?>> event) {
        Registry.registerTileEntities(event.getRegistry());
    }

    @SubscribeEvent
    public static void onBlocksRegistry(RegistryEvent.Register<Block> event) {
        Registry.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onItemsRegistry(RegistryEvent.Register<Item> event) {
        Registry.registerItems(event.getRegistry());
    }

    public static ItemGroup tab = new ItemGroup(DeepConstants.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registry.deepLearner);
        }
    };
}
