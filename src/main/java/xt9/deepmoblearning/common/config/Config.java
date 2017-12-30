package xt9.deepmoblearning.common.config;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;

import java.io.File;
import java.util.*;

/**
 * Created by xt9 on 2017-06-08.
 */
@Mod.EventBusSubscriber
public class Config {
    private static Configuration config = new Configuration(new File("config/" + DeepConstants.MODID + ".cfg"));
    public static ConfigCategory dataModel = new ConfigCategory("data model simulation costs");
    public static ConfigCategory pristineChance = new ConfigCategory("pristine matter chance");
    public static ConfigCategory modelExperience = new ConfigCategory("model experience tweaks");

    public static Property guiOverlaySide;

    public static void load() {
        config.load();
    }

    public static void initConfigValues() {
        dataModel.setComment("Simulation cost for all the data models (in RF/t)\nCost should not exceed a full machine buffer (Max 6666 RF/t)\nValues over the max will be set to the max");
        config.setCategoryComment(dataModel.getName(), dataModel.getComment());

        dataModel.put("zombie", config.get(dataModel.getName(), "zombie", 80, null, 1, 6666));
        dataModel.put("skeleton", config.get(dataModel.getName(), "skeleton", 80, null, 1, 6666));
        dataModel.put("blaze", config.get(dataModel.getName(), "blaze", 256, null, 1, 6666));
        dataModel.put("enderman", config.get(dataModel.getName(), "enderman", 512, null, 1, 6666));
        dataModel.put("wither", config.get(dataModel.getName(), "wither", 2048, null, 1, 6666));
        dataModel.put("witch", config.get(dataModel.getName(), "witch", 120, null, 1, 6666));
        dataModel.put("spider", config.get(dataModel.getName(), "spider", 80, null, 1, 6666));
        dataModel.put("creeper", config.get(dataModel.getName(), "creeper", 80, null, 1, 6666));
        dataModel.put("ghast", config.get(dataModel.getName(), "ghast", 372, null, 1, 6666));
        dataModel.put("witherskeleton", config.get(dataModel.getName(), "witherskeleton", 880));
        dataModel.put("slime", config.get(dataModel.getName(), "slime", 180));
        dataModel.put("dragon", config.get(dataModel.getName(), "dragon", 2560));

        pristineChance.setComment("The chance to output pristine matter from the various tiers\nStarts at basic since faulty models can't be used in the simulation chamber");
        config.setCategoryComment(pristineChance.getName(), pristineChance.getComment());

        pristineChance.put("tier1", config.get(pristineChance.getName(), "tier1", 5));
        pristineChance.put("tier2", config.get(pristineChance.getName(), "tier2", 11));
        pristineChance.put("tier3", config.get(pristineChance.getName(), "tier3", 24));
        pristineChance.put("tier4", config.get(pristineChance.getName(), "tier4", 42));

        modelExperience.setComment("DISCLAIMER: Please tweak these values responsibly if you're building modpacks for the public, This mods intent is not to be grindy or \"timegated\"\nRemember that a high kill multiplier devalues how much you can use the simulation chamber for leveling\nFor example, a killmultiplier of 300 and 2 required kills to next tier is the equivalent of 600 simulations\nData formula: killmultiplier * requiredKills");
        config.setCategoryComment(modelExperience.getName(), modelExperience.getComment());

        modelExperience.put("killMultiplierTier0", config.get(modelExperience.getName(), "killMultiplierTier0", 1, "How much data you get per kill on the Faulty tier (It's Best to leave this at 1, as a baseline)"));
        modelExperience.put("killMultiplierTier1", config.get(modelExperience.getName(), "killMultiplierTier1", 4, "How much data you get per kill on the Basic tier"));
        modelExperience.put("killMultiplierTier2", config.get(modelExperience.getName(), "killMultiplierTier2", 10, "How much data you get per kill on the Advanced tier"));
        modelExperience.put("killMultiplierTier3", config.get(modelExperience.getName(), "killMultiplierTier3", 18, "How much data you get per kill on the Superior tier"));

        modelExperience.put("killsToTier1", config.get(modelExperience.getName(), "killsToTier1", 6, "Number of kills to reach the Basic tier."));
        modelExperience.put("killsToTier2", config.get(modelExperience.getName(), "killsToTier2", 12, "Number of kills to reach the Advanced tier."));
        modelExperience.put("killsToTier3", config.get(modelExperience.getName(), "killsToTier3", 30, "Number of kills to reach the Superior tier."));
        modelExperience.put("killsToTier4", config.get(modelExperience.getName(), "killsToTier4", 50, "Number of kills to reach the Self Aware tier."));

        guiOverlaySide = config.get(Configuration.CATEGORY_GENERAL, "guiOverlaySide", "left", "Which side the Deep learner gui will appear on. [values: left/right]");

        config.save();
    }


    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.getModID().equalsIgnoreCase(DeepConstants.MODID)){
            // Reload the values if something was changed so the config accessors are "up to date"
            initConfigValues();
        }
    }
}