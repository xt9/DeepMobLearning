package xt9.deepmoblearning;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = DeepMobLearning.MODID, version = DeepMobLearning.VERSION, updateJSON = "https://github.com/xt9/DeepMobLearner/blob/master/update.json")
public class DeepMobLearning
{
    public static final String MODID = "deepmoblearning";
    public static final String VERSION = "${version}";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        return;
    }
}
