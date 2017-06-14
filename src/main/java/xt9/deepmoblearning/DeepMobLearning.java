package xt9.deepmoblearning;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.Registry;

@Mod(modid = DeepMobLearning.MODID, version = DeepMobLearning.VERSION)
public class DeepMobLearning {
    public static final String MODID = "deepmoblearning";
    public static final String VERSION = "${version}";

    @Mod.Instance(MODID)
    public static DeepMobLearning instance;

    @SidedProxy(clientSide="xt9.deepmoblearning.client.ClientProxy", serverSide="xt9.deepmoblearning.common.CommonProxy")
    public static CommonProxy proxy;

    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Registry.preInit();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
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
}
