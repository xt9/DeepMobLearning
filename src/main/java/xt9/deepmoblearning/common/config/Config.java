package xt9.deepmoblearning.common.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.deepmoblearning.DeepConstants;

import java.io.File;

/**
 * Created by xt9 on 2017-06-08.
 */
@Mod.EventBusSubscriber
public class Config {
    private static Configuration config = new DMLConfiguration(new File("config/" + DeepConstants.MODID + ".cfg"));
    public static ConfigCategory dataModel = new ConfigCategory("data model simulation costs");
    public static ConfigCategory pristineChance = new ConfigCategory("pristine matter chance");
    public static ConfigCategory modelExperience = new ConfigCategory("model experience tweaks");
    public static ConfigCategory pristineOutputs = new ConfigCategory("pristine output items");
    public static ConfigCategory livingMatterEXP = new ConfigCategory("matter experience");

    public static Property rfCostExtractionChamber;
    public static Property guiOverlaySide;

    public static void load() {
        config.load();
    }

    public static void initConfigValues() {
        initLivingMatterEXP();
        initDataModelRFCost();
        initPristineChance();
        initModelExperience();
        initPristineOutputs();

        rfCostExtractionChamber = config.get(Configuration.CATEGORY_GENERAL, "rfCostLootFabricator", 256, "RF/t cost for the Loot Fabricator, roof is 18k RF/t");
        guiOverlaySide = config.get(Configuration.CATEGORY_GENERAL, "guiOverlaySide", "topleft", "Which position on the screen the Deep learner gui will appear on. (bottomleft will clash with the chat) [values: topleft/topright/bottomleft/bottomright]");

        config.save();
    }

    private static void initLivingMatterEXP() {
        livingMatterEXP.setComment("Experience values for the different \"realm\" matters, maxValue: 999");
        config.setCategoryComment(livingMatterEXP.getName(), livingMatterEXP.getComment());

        livingMatterEXP.put("overworldian", config.get(livingMatterEXP.getName(), "overworldian", 10, null, 1, 999));
        livingMatterEXP.put("hellish", config.get(livingMatterEXP.getName(), "hellish", 14, null, 1, 999));
        livingMatterEXP.put("extraterrestrial", config.get(livingMatterEXP.getName(), "extraterrestrial", 20, null, 1, 999));

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            livingMatterEXP.put("twilight", config.get(livingMatterEXP.getName(), "twilight", 30, null, 1, 999));
        }
    }

    private static void initDataModelRFCost() {
        dataModel.setComment("Simulation costs for all the data models (in RF/t)\nCost should not exceed a full machine buffer (Max 6666 RF/t)\nValues over the max will be set to the max");
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
        dataModel.put("witherskeleton", config.get(dataModel.getName(), "witherskeleton", 880,null, 1, 6666));
        dataModel.put("slime", config.get(dataModel.getName(), "slime", 180, null, 1, 6666));
        dataModel.put("dragon", config.get(dataModel.getName(), "dragon", 2560, null, 1, 6666));

        /* Extension models */
        if(DeepConstants.MOD_TE_LOADED) {
            dataModel.put("thermalelemental", config.get(dataModel.getName(), "thermalelemental", 256,null, 1, 6666));
        }
        if(DeepConstants.MOD_TCON_LOADED) {
            dataModel.put("tinkerslime", config.get(dataModel.getName(), "tinkerslime", 256,null, 1, 6666));
        }
        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModel.put("twilightforest", config.get(dataModel.getName(), "twilightforest", 256,null, 1, 6666));
            dataModel.put("twilightswamp", config.get(dataModel.getName(), "twilightswamp", 256,null, 1, 6666));
            dataModel.put("twilightdarkwood", config.get(dataModel.getName(), "twilightdarkwood", 256,null, 1, 6666));
            dataModel.put("twilightglacier", config.get(dataModel.getName(), "twilightglacier", 256,null, 1, 6666));
        }
    }

    private static void initPristineChance() {
        pristineChance.setComment("The chance to output pristine matter from the various tiers\nStarts at basic since faulty models can't be used in the simulation chamber");
        config.setCategoryComment(pristineChance.getName(), pristineChance.getComment());

        pristineChance.put("tier1", config.get(pristineChance.getName(), "tier1", 5, null, 1, 100));
        pristineChance.put("tier2", config.get(pristineChance.getName(), "tier2", 11, null, 1, 100));
        pristineChance.put("tier3", config.get(pristineChance.getName(), "tier3", 24, null, 1, 100));
        pristineChance.put("tier4", config.get(pristineChance.getName(), "tier4", 42, null, 1, 100));
    }

    private static void initModelExperience() {
        modelExperience.setComment("DISCLAIMER: Please tweak these values responsibly if you're building modpacks for the public, This mods intent is not to be grindy or \"timegated\"\nRemember that a high kill multiplier devalues how much you can use the simulation chamber for leveling\nFor example, a killmultiplier of 100 and 2 required kills to next tier is the equivalent of 200 simulations\nData formula: killmultiplier * requiredKills\nMax multiplier: 100\nMax kills to next tier: 500");
        config.setCategoryComment(modelExperience.getName(), modelExperience.getComment());

        modelExperience.put("killMultiplierTier0", config.get(modelExperience.getName(), "killMultiplierTier0", 1, "How much data you get per kill on the Faulty tier (It's Best to leave this at 1, as a baseline)", 1, 100));
        modelExperience.put("killMultiplierTier1", config.get(modelExperience.getName(), "killMultiplierTier1", 4, "How much data you get per kill on the Basic tier", 1, 100));
        modelExperience.put("killMultiplierTier2", config.get(modelExperience.getName(), "killMultiplierTier2", 10, "How much data you get per kill on the Advanced tier", 1, 100));
        modelExperience.put("killMultiplierTier3", config.get(modelExperience.getName(), "killMultiplierTier3", 18, "How much data you get per kill on the Superior tier", 1, 100));

        modelExperience.put("killsToTier1", config.get(modelExperience.getName(), "killsToTier1", 6, "Number of kills to reach the Basic tier.", 1, 500));
        modelExperience.put("killsToTier2", config.get(modelExperience.getName(), "killsToTier2", 12, "Number of kills to reach the Advanced tier.", 1, 500));
        modelExperience.put("killsToTier3", config.get(modelExperience.getName(), "killsToTier3", 30, "Number of kills to reach the Superior tier.", 1, 500));
        modelExperience.put("killsToTier4", config.get(modelExperience.getName(), "killsToTier4", 50, "Number of kills to reach the Self Aware tier.", 1, 500));
    }

    private static void initPristineOutputs() {
        pristineOutputs.setComment("Entries for Loot fabricator outputs from the different pristine items.\nInput format \"minecraft:coal,64,0\"\nWhere minecraft:coal is the registryName, 64 is the amount and 0 is the damagevalue/meta. \",\" is the delimiter.");
        config.setCategoryComment(pristineOutputs.getName(), pristineOutputs.getComment());

        pristineOutputs.put("blaze", new Property("blaze", config.getStringList("blaze", pristineOutputs.getName(), DeepConstants.LOOT.BLAZE, "Blaze"), Property.Type.STRING));
        pristineOutputs.put("creeper", new Property("creeper", config.getStringList("creeper", pristineOutputs.getName(), DeepConstants.LOOT.CREEPER, "Creeper"), Property.Type.STRING));
        pristineOutputs.put("dragon", new Property("dragon", config.getStringList("dragon", pristineOutputs.getName(), DeepConstants.LOOT.DRAGON, "Dragon"), Property.Type.STRING));
        pristineOutputs.put("enderman", new Property("enderman", config.getStringList("enderman", pristineOutputs.getName(), DeepConstants.LOOT.ENDERMAN, "Enderman"), Property.Type.STRING));
        pristineOutputs.put("ghast", new Property("ghast", config.getStringList("ghast", pristineOutputs.getName(), DeepConstants.LOOT.GHAST, "Ghast"), Property.Type.STRING));
        pristineOutputs.put("skeleton", new Property("skeleton", config.getStringList("skeleton", pristineOutputs.getName(), DeepConstants.LOOT.SKELETON, "Skeleton"), Property.Type.STRING));
        pristineOutputs.put("slime", new Property("slime", config.getStringList("slime", pristineOutputs.getName(), DeepConstants.LOOT.SLIME, "Slime"), Property.Type.STRING));
        pristineOutputs.put("spider", new Property("spider", config.getStringList("spider", pristineOutputs.getName(), DeepConstants.LOOT.SPIDER, "Spider"), Property.Type.STRING));
        pristineOutputs.put("witch", new Property("witch", config.getStringList("witch", pristineOutputs.getName(), DeepConstants.LOOT.WITCH, "Witch"), Property.Type.STRING));
        pristineOutputs.put("witherskeleton", new Property("witherskeleton", config.getStringList("witherskeleton", pristineOutputs.getName(), DeepConstants.LOOT.WITHERSKELETON, "Wither Skeleton"), Property.Type.STRING));
        pristineOutputs.put("wither", new Property("wither", config.getStringList("wither", pristineOutputs.getName(), DeepConstants.LOOT.WITHER, "Wither"), Property.Type.STRING));
        pristineOutputs.put("zombie", new Property("zombie", config.getStringList("zombie", pristineOutputs.getName(), DeepConstants.LOOT.ZOMBIE, "Zombie"), Property.Type.STRING));

        if(DeepConstants.MOD_TE_LOADED) {
            pristineOutputs.put("thermalelemental", new Property("thermalelemental", config.getStringList("thermalelemental", pristineOutputs.getName(), DeepConstants.LOOT.THERMALELEMENTAL, "Thermal Elemental"), Property.Type.STRING));
        }

        if(DeepConstants.MOD_TCON_LOADED) {
            pristineOutputs.put("tinkerslime", new Property("tinkerslime", config.getStringList("tinkerslime", pristineOutputs.getName(), DeepConstants.LOOT.TINKERSLIME, "Tinker construct slime"), Property.Type.STRING));
        }

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            pristineOutputs.put("twilightforest", new Property("twilightforest", config.getStringList("twilightforest", pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTFOREST, "Twilight Forest(Biome, not the whole mod)"), Property.Type.STRING));
            pristineOutputs.put("twilightswamp", new Property("twilightswamp", config.getStringList("twilightswamp", pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTSWAMP, "Twilight Swamp creatures"), Property.Type.STRING));
            pristineOutputs.put("twilightdarkwood", new Property("twilightdarkwood", config.getStringList("twilightdarkwood", pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTDARKWOOD, "Twilight Darkwood creatures"), Property.Type.STRING));
            pristineOutputs.put("twilightglacier", new Property("twilightglacier", config.getStringList("twilightglacier", pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTGLACIER, "Twilight Glacier creatures"), Property.Type.STRING));
        }
    }

    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equalsIgnoreCase(DeepConstants.MODID)){
            // Reload the values if something was changed so the config accessors are "up to date"
            initConfigValues();
        }
    }

    public static class LootParser {

        public static NonNullList<ItemStack> getLootEntries(String key) {
            NonNullList<ItemStack> list = NonNullList.create();

            String[] toParseList;
            if(pristineOutputs.containsKey(key)) {
                toParseList = pristineOutputs.get(key).getStringList();
            } else {
                return list;
            }

            for(int i = 0; i < toParseList.length; i++) {
                if(!getStackFromConfigEntry(toParseList[i]).isEmpty()) {
                    list.add(getStackFromConfigEntry(toParseList[i]));
                }
            }

            return list;
        }

        private static ItemStack getStackFromConfigEntry(String entry) {
            String[] vals = entry.split(",");

            if (vals.length < 3) {
                return ItemStack.EMPTY;
            }

            String itemName = vals[0];
            int amount;
            int meta;

            try {
                amount = Integer.parseInt(vals[1]);
                meta = Integer.parseInt(vals[2]);
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number for meta or amount");
                return ItemStack.EMPTY;
            }

            Item item = Item.getByNameOrId(itemName);
            if(item != null) {
                return new ItemStack(item, amount, meta);
            } else {
                return ItemStack.EMPTY;
            }
        }
    }
}