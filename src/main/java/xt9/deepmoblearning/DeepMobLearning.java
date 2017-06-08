package xt9.deepmoblearning;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.Registry;

@Mod(modid = DeepMobLearning.MODID, version = DeepMobLearning.VERSION, updateJSON = "https://github.com/xt9/DeepMobLearner/blob/master/update.json")
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
}
