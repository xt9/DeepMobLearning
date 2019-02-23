package xt9.deepmoblearning.common.config;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.mobmetas.MobKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xt9 on 2017-06-08.
 */
@Mod.EventBusSubscriber
public class Config {
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = specPair.getLeft();
        CLIENT_SPEC = specPair.getRight();
    }

    /* Client Accesser Fields */
    public static String guiOverlaySide = "topleft";
    public static int guiOverlayVerticalSpacing = 0;
    public static int guiOverlayHorizontalSpacing = 0;

    /* Server Accesser Fields */
    public static int rfCostExtractionChamber = 256;
    public static boolean isSootedRedstoneCraftingEnabled = true;
    public static HashMap<String, Integer> livingMatterEXP = new HashMap<>();
    public static HashMap<String, Integer> pristineChance = new HashMap<>();
    public static HashMap<String, Integer> dataModelRFCost = new HashMap<>();
    public static HashMap<String, Integer> dataModelExperience = new HashMap<>();
    public static HashMap<String, List<? extends String>> dataModelMobs = new HashMap<>();
    public static HashMap<String, List<? extends String>> pristineOutputs = new HashMap<>();
    public static HashMap<String, List<? extends String>> trialRewards = new HashMap<>();

    private static class ClientConfig {
        public ForgeConfigSpec.ConfigValue<String> guiOverlaySide;
        public ForgeConfigSpec.IntValue guiOverlayHorizontalSpacing;
        public ForgeConfigSpec.IntValue guiOverlayVerticalSpacing;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            guiOverlaySide = builder
                .comment(" Which position on the screen the Deep learner gui will appear on. (bottomleft will clash with the chat) [values: topleft/topright/bottomleft/bottomright]")
                .define("guiOverlaySide", "topleft");

            guiOverlayVerticalSpacing = builder
                .comment(" Vertical spacing from the selected corner, values can be both positive and negative")
                .defineInRange("guiOverlayVerticalSpacing", 0, -2000, 2000);

            guiOverlayHorizontalSpacing = builder
                .comment(" Horizontal spacing from the selected corner, values can be both positive and negative")
                .defineInRange("guiOverlayHorizontalSpacing", 0, -2000, 2000);
            builder.pop();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static class CommonConfig {
        private ForgeConfigSpec.IntValue rfCostExtractionChamber;
        private ForgeConfigSpec.BooleanValue isSootedRedstoneCraftingEnabled;
        private HashMap<String, ForgeConfigSpec.IntValue> livingMatterEXP = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.IntValue> pristineChance = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.IntValue> dataModelExperience = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.IntValue> dataModelRFCost = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.ConfigValue<List<? extends String>>> pristineOutputs = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.ConfigValue<List<? extends String>>> trialRewards = new HashMap<>();
        private HashMap<String, ForgeConfigSpec.ConfigValue<List<? extends String>>> dataModelMobs = new HashMap<>();

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            rfCostExtractionChamber = builder
                .comment(" RF/t cost for the Loot Fabricator, roof is 18k RF/t")
                .defineInRange("rfCostExtractionChamber", 256, 0, 18000);

            isSootedRedstoneCraftingEnabled = builder
                .comment(" Should the Crafting of sooted redstone on Blocks of Coal be enabled?")
                .define("isSootedRedstoneCraftingEnabled", true);
            builder.pop();
            initLivingMatterEXP("matter.livingmatterexp", builder);
            initPristineChance("matter.pristinechance", builder);
            initPristineOutputs("matter.pristineloot", builder);
            initDataModelRFCost("datamodels.cost", builder);
            initDataModelMobs("datamodels.mobs", builder);
            initModelExperience("datamodels.experience", builder);
            initTrialRewards("trials.rewards", builder);
        }

        private void initLivingMatterEXP(String category, ForgeConfigSpec.Builder builder) {
            builder.comment("Experience values for the different \"transmutational\" matters");
            builder.push(category);
            livingMatterEXP.put(Registry.livingMatterOverworldian.getType(), builder.defineInRange(Registry.livingMatterOverworldian.getType(), 10, 0, 999));
            livingMatterEXP.put(Registry.livingMatterHellish.getType(), builder.defineInRange(Registry.livingMatterHellish.getType(), 14, 0, 999));
            livingMatterEXP.put(Registry.livingMatterExtraterrestrial.getType(), builder.defineInRange(Registry.livingMatterExtraterrestrial.getType(), 20, 0, 999));
            builder.pop(2);
/*            if(DeepConstants.MOD_TWILIGHT_LOADED) {
                livingMatterEXP.put("twilight", config.get(livingMatterEXP.getName(), "twilight", 30, null, 1, 999));
            }*/
        }

        private void initPristineChance(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " The chance to output pristine matter from the various Tiers.",
                " Starts at basic since faulty models can't be used in the simulation chamber"
            );
            builder.push(category);
            pristineChance.put("tier1", builder.defineInRange("tier1", 5, 1, 100));
            pristineChance.put("tier2", builder.defineInRange("tier2", 11, 1, 100));
            pristineChance.put("tier3", builder.defineInRange("tier3", 24, 1, 100));
            pristineChance.put("tier4", builder.defineInRange("tier4", 42, 1, 100));
            builder.pop(2);
        }

        private void initPristineOutputs(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " Entries for Loot fabricator outputs from the different pristine items.",
                " Input format \"minecraft:coal,64\"",
                " Where minecraft:coal is the registryName and 64 is the amount. \",\" is the delimiter."
            );
            builder.push(category);
            pristineOutputs.put(MobKey.BLAZE, builder.defineList(MobKey.BLAZE, Arrays.asList(DeepConstants.LOOT.BLAZE), o -> o instanceof String));
            pristineOutputs.put(MobKey.CREEPER, builder.defineList(MobKey.CREEPER, Arrays.asList(DeepConstants.LOOT.CREEPER), o -> o instanceof String));
            pristineOutputs.put(MobKey.DRAGON, builder.defineList(MobKey.DRAGON, Arrays.asList(DeepConstants.LOOT.DRAGON), o -> o instanceof String));
            pristineOutputs.put(MobKey.ENDERMAN, builder.defineList(MobKey.ENDERMAN, Arrays.asList(DeepConstants.LOOT.ENDERMAN), o -> o instanceof String));
            pristineOutputs.put(MobKey.GHAST, builder.defineList(MobKey.GHAST, Arrays.asList(DeepConstants.LOOT.GHAST), o -> o instanceof String));
            pristineOutputs.put(MobKey.SKELETON, builder.defineList(MobKey.SKELETON, Arrays.asList(DeepConstants.LOOT.SKELETON), o -> o instanceof String));
            pristineOutputs.put(MobKey.SLIME, builder.defineList(MobKey.SLIME, Arrays.asList(DeepConstants.LOOT.SLIME), o -> o instanceof String));
            pristineOutputs.put(MobKey.SPIDER, builder.defineList(MobKey.SPIDER, Arrays.asList(DeepConstants.LOOT.SPIDER), o -> o instanceof String));
            pristineOutputs.put(MobKey.WITCH, builder.defineList(MobKey.WITCH, Arrays.asList(DeepConstants.LOOT.WITCH), o -> o instanceof String));
            pristineOutputs.put(MobKey.WITHER, builder.defineList(MobKey.WITHER, Arrays.asList(DeepConstants.LOOT.WITHER), o -> o instanceof String));
            pristineOutputs.put(MobKey.ZOMBIE, builder.defineList(MobKey.ZOMBIE, Arrays.asList(DeepConstants.LOOT.ZOMBIE), o -> o instanceof String));
            pristineOutputs.put(MobKey.SHULKER, builder.defineList(MobKey.SHULKER, Arrays.asList(DeepConstants.LOOT.SHULKER), o -> o instanceof String));
            pristineOutputs.put(MobKey.GUARDIAN, builder.defineList(MobKey.GUARDIAN, Arrays.asList(DeepConstants.LOOT.GUARDIAN), o -> o instanceof String));
            pristineOutputs.put(MobKey.WITHERSKELETON, builder.defineList(MobKey.WITHERSKELETON, Arrays.asList(DeepConstants.LOOT.WITHERSKELETON), o -> o instanceof String));
            builder.pop(2);
    /*        if(DeepConstants.MOD_TE_LOADED) {
                pristineOutputs.put(MobKey.TE, new Property(MobKey.TE, config.getStringList(MobKey.TE, pristineOutputs.getName(), DeepConstants.LOOT.THERMALELEMENTAL, "Thermal Elemental"), Property.Type.STRING));
            }
            if(DeepConstants.MOD_TCON_LOADED) {
                pristineOutputs.put(MobKey.TINKERSLIME, new Property(MobKey.TINKERSLIME, config.getStringList(MobKey.TINKERSLIME, pristineOutputs.getName(), DeepConstants.LOOT.TINKERSLIME, "Tinker construct slime"), Property.Type.STRING));
            }
            if(DeepConstants.MOD_TWILIGHT_LOADED) {
                pristineOutputs.put(MobKey.TWILIGHTFOREST, new Property(MobKey.TWILIGHTFOREST, config.getStringList(MobKey.TWILIGHTFOREST, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTFOREST, "Twilight Forest(Biome, not the whole mod)"), Property.Type.STRING));
                pristineOutputs.put(MobKey.TWILIGHTSWAMP, new Property(MobKey.TWILIGHTSWAMP, config.getStringList(MobKey.TWILIGHTSWAMP, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTSWAMP, "Twilight Swamp creatures"), Property.Type.STRING));
                pristineOutputs.put(MobKey.TWILIGHTDARKWOOD, new Property(MobKey.TWILIGHTDARKWOOD, config.getStringList(MobKey.TWILIGHTDARKWOOD, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTDARKWOOD, "Twilight Darkwood creatures"), Property.Type.STRING));
                pristineOutputs.put(MobKey.TWILIGHTGLACIER, new Property(MobKey.TWILIGHTGLACIER, config.getStringList(MobKey.TWILIGHTGLACIER, pristineOutputs.getName(), DeepConstants.LOOT.TWILIGHTGLACIER, "Twilight Glacier creatures"), Property.Type.STRING));
            }*/
        }

        private void initDataModelRFCost(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " Simulation costs for all the data models (in RF/t)",
                " Cost should not exceed a full machine buffer (Max 6666 RF/t)",
                " Values over the max will be set to the max"
            );
            builder.push(category);
            dataModelRFCost.put(MobKey.BLAZE, builder.defineInRange(MobKey.BLAZE, 256, 0, 6666));
            dataModelRFCost.put(MobKey.CREEPER, builder.defineInRange(MobKey.CREEPER, 80, 0, 6666));
            dataModelRFCost.put(MobKey.DRAGON, builder.defineInRange(MobKey.DRAGON, 2560, 0, 6666));
            dataModelRFCost.put(MobKey.ENDERMAN, builder.defineInRange(MobKey.ENDERMAN, 512, 0, 6666));
            dataModelRFCost.put(MobKey.GHAST, builder.defineInRange(MobKey.GHAST, 372, 0, 6666));
            dataModelRFCost.put(MobKey.SKELETON, builder.defineInRange(MobKey.SKELETON, 80, 0, 6666));
            dataModelRFCost.put(MobKey.SLIME, builder.defineInRange(MobKey.SLIME, 150, 0, 6666));
            dataModelRFCost.put(MobKey.SPIDER, builder.defineInRange(MobKey.SPIDER, 80, 0, 6666));
            dataModelRFCost.put(MobKey.WITCH, builder.defineInRange(MobKey.WITCH, 120, 0, 6666));
            dataModelRFCost.put(MobKey.WITHER, builder.defineInRange(MobKey.WITHER, 880, 0, 6666));
            dataModelRFCost.put(MobKey.ZOMBIE, builder.defineInRange(MobKey.ZOMBIE, 2048, 0, 6666));
            dataModelRFCost.put(MobKey.SHULKER, builder.defineInRange(MobKey.SHULKER, 80, 0, 6666));
            dataModelRFCost.put(MobKey.GUARDIAN, builder.defineInRange(MobKey.GUARDIAN, 256, 0, 6666));
            dataModelRFCost.put(MobKey.WITHERSKELETON, builder.defineInRange(MobKey.WITHERSKELETON, 340, 0, 6666));
            builder.pop(2);

            /* Extension models */
/*        if(DeepConstants.MOD_TE_LOADED) {
            dataModel.put(MobKey.TE, config.get(dataModel.getName(), MobKey.TE, 256,null, 1, 6666));
        } if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModel.put(MobKey.TWILIGHTFOREST, config.get(dataModel.getName(), MobKey.TWILIGHTFOREST, 256,null, 1, 6666));
            dataModel.put(MobKey.TWILIGHTSWAMP, config.get(dataModel.getName(), MobKey.TWILIGHTSWAMP, 256,null, 1, 6666));
            dataModel.put(MobKey.TWILIGHTDARKWOOD, config.get(dataModel.getName(), MobKey.TWILIGHTDARKWOOD, 256,null, 1, 6666));
            dataModel.put(MobKey.TWILIGHTGLACIER, config.get(dataModel.getName(), MobKey.TWILIGHTGLACIER, 256,null, 1, 6666));
        } if(DeepConstants.MOD_TCON_LOADED) {
            dataModel.put(MobKey.TINKERSLIME, config.get(dataModel.getName(), MobKey.TINKERSLIME, 256,null, 1, 6666));
        }*/
        }

        private void initDataModelMobs(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " Register entities that count towards leveling up the model",
                " Format is \"modname:entity_name\", each value is separated by a comma."
            );
            builder.push(category);
            dataModelMobs.put(MobKey.BLAZE, builder.defineList(MobKey.BLAZE, Arrays.asList(DeepConstants.MOBS.BLAZE), o -> o instanceof String));
            dataModelMobs.put(MobKey.CREEPER, builder.defineList(MobKey.CREEPER, Arrays.asList(DeepConstants.MOBS.CREEPER), o -> o instanceof String));
            dataModelMobs.put(MobKey.DRAGON, builder.defineList(MobKey.DRAGON, Arrays.asList(DeepConstants.MOBS.DRAGON), o -> o instanceof String));
            dataModelMobs.put(MobKey.ENDERMAN, builder.defineList(MobKey.ENDERMAN, Arrays.asList(DeepConstants.MOBS.ENDERMAN), o -> o instanceof String));
            dataModelMobs.put(MobKey.GHAST, builder.defineList(MobKey.GHAST, Arrays.asList(DeepConstants.MOBS.GHAST), o -> o instanceof String));
            dataModelMobs.put(MobKey.SKELETON, builder.defineList(MobKey.SKELETON, Arrays.asList(DeepConstants.MOBS.SKELETON), o -> o instanceof String));
            dataModelMobs.put(MobKey.SLIME, builder.defineList(MobKey.SLIME, Arrays.asList(DeepConstants.MOBS.SLIME), o -> o instanceof String));
            dataModelMobs.put(MobKey.SPIDER, builder.defineList(MobKey.SPIDER, Arrays.asList(DeepConstants.MOBS.SPIDER), o -> o instanceof String));
            dataModelMobs.put(MobKey.WITCH, builder.defineList(MobKey.WITCH, Arrays.asList(DeepConstants.MOBS.WITCH), o -> o instanceof String));
            dataModelMobs.put(MobKey.WITHER, builder.defineList(MobKey.WITHER, Arrays.asList(DeepConstants.MOBS.WITHER), o -> o instanceof String));
            dataModelMobs.put(MobKey.ZOMBIE, builder.defineList(MobKey.ZOMBIE, Arrays.asList(DeepConstants.MOBS.ZOMBIE), o -> o instanceof String));
            dataModelMobs.put(MobKey.SHULKER, builder.defineList(MobKey.SHULKER, Arrays.asList(DeepConstants.MOBS.SHULKER), o -> o instanceof String));
            dataModelMobs.put(MobKey.GUARDIAN, builder.defineList(MobKey.GUARDIAN, Arrays.asList(DeepConstants.MOBS.GUARDIAN), o -> o instanceof String));
            dataModelMobs.put(MobKey.WITHERSKELETON, builder.defineList(MobKey.WITHERSKELETON, Arrays.asList(DeepConstants.MOBS.WITHERSKELETON), o -> o instanceof String));
            builder.pop(2);

            /* Extension models */
/*        if(DeepConstants.MOD_TE_LOADED) {
            dataModelMobNames.put(MobKey.TE, new Property(MobKey.TE, config.getStringList(MobKey.TE, dataModelMobNames.getName(), DeepConstants.MOBS.THERMALELEMENTAL, "Thermal Elemental"), Property.Type.STRING));
        } if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModelMobNames.put(MobKey.TWILIGHTFOREST, new Property(MobKey.TWILIGHTFOREST, config.getStringList(MobKey.TWILIGHTFOREST, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTFOREST, "Twilight Forest(Biome, not the whole mod)"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTSWAMP, new Property(MobKey.TWILIGHTSWAMP, config.getStringList(MobKey.TWILIGHTSWAMP, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTSWAMP, "Twilight Swamp creatures"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTDARKWOOD, new Property(MobKey.TWILIGHTDARKWOOD, config.getStringList(MobKey.TWILIGHTDARKWOOD, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTDARKWOOD, "Twilight Darkwood creatures"), Property.Type.STRING));
            dataModelMobNames.put(MobKey.TWILIGHTGLACIER, new Property(MobKey.TWILIGHTGLACIER, config.getStringList(MobKey.TWILIGHTGLACIER, dataModelMobNames.getName(), DeepConstants.MOBS.TWILIGHTGLACIER, "Twilight Glacier creatures"), Property.Type.STRING));
        } if(DeepConstants.MOD_TCON_LOADED) {
            dataModelMobNames.put(MobKey.TINKERSLIME, new Property(MobKey.TINKERSLIME, config.getStringList(MobKey.TINKERSLIME, dataModelMobNames.getName(), DeepConstants.MOBS.TINKERSLIME, "Tinker construct slime"), Property.Type.STRING));
        }*/
        }

        private void initModelExperience(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " DISCLAIMER: Please tweak these values responsibly if you're building modpacks for the public, This mods intent is not to be grindy or \"timegated\"",
                " Remember that a high kill multiplier devalues how much you can use the simulation chamber for leveling.",
                " For example, a killmultiplier of 100 and 2 required kills to next tier is the equivalent of 200 simulations",
                " Data formula: killmultiplier * requiredKills",
                " Max multiplier: 100",
                " Max kills to next tier: 500"
            );
            builder.push(category);
            dataModelExperience.put("killMultiplierTier0", builder.defineInRange("killMultiplierTier0", 1, 1, 100));
            dataModelExperience.put("killMultiplierTier1", builder.defineInRange("killMultiplierTier1", 4, 1, 100));
            dataModelExperience.put("killMultiplierTier2", builder.defineInRange("killMultiplierTier2", 10, 1, 100));
            dataModelExperience.put("killMultiplierTier3", builder.defineInRange("killMultiplierTier3", 18, 1, 100));
            dataModelExperience.put("killsToTier1", builder.defineInRange("killsToTier1", 6, 1, 100));
            dataModelExperience.put("killsToTier2", builder.defineInRange("killsToTier2", 12, 1, 100));
            dataModelExperience.put("killsToTier3", builder.defineInRange("killsToTier3", 30, 1, 100));
            dataModelExperience.put("killsToTier4", builder.defineInRange("killsToTier4", 50, 1, 100));
            builder.pop(2);
        }

        private void initTrialRewards(String category, ForgeConfigSpec.Builder builder) {
            builder.comment(
                " Rewards for the Max tier of trials.",
                " CAUTION: Max 3 items per list, anything after that will be trimmed.",
                " Input format \"minecraft:coal,64\"",
                " Where minecraft:coal is the registryName and 64 is the amount. \",\" is the delimiter."
            );
            builder.push(category);
            trialRewards = new HashMap<>();
            trialRewards.put(MobKey.ENDERMAN, builder.defineList(MobKey.ENDERMAN, Arrays.asList(DeepConstants.TRIAL_REWARD.ENDERMAN), o -> o instanceof String));
            trialRewards.put(MobKey.SKELETON, builder.defineList(MobKey.SKELETON, Arrays.asList(DeepConstants.TRIAL_REWARD.SKELETON), o -> o instanceof String));
            trialRewards.put(MobKey.SLIME, builder.defineList(MobKey.SLIME, Arrays.asList(DeepConstants.TRIAL_REWARD.SLIME), o -> o instanceof String));
            trialRewards.put(MobKey.SPIDER, builder.defineList(MobKey.SPIDER, Arrays.asList(DeepConstants.TRIAL_REWARD.SPIDER), o -> o instanceof String));
            trialRewards.put(MobKey.WITCH, builder.defineList(MobKey.WITCH, Arrays.asList(DeepConstants.TRIAL_REWARD.WITCH), o -> o instanceof String));
            trialRewards.put(MobKey.ZOMBIE, builder.defineList(MobKey.ZOMBIE, Arrays.asList(DeepConstants.TRIAL_REWARD.ZOMBIE), o -> o instanceof String));
            trialRewards.put(MobKey.WITHERSKELETON, builder.defineList(MobKey.WITHERSKELETON, Arrays.asList(DeepConstants.TRIAL_REWARD.WITHERSKELETON), o -> o instanceof String));
            builder.pop(2);
        }
    }

    public static void refreshClient() {
        guiOverlaySide = CLIENT.guiOverlaySide.get();
        guiOverlayHorizontalSpacing = CLIENT.guiOverlayHorizontalSpacing.get();
        guiOverlayVerticalSpacing = CLIENT.guiOverlayVerticalSpacing.get();
    }

    public static void refreshCommon() {
        rfCostExtractionChamber = COMMON.rfCostExtractionChamber.get();
        isSootedRedstoneCraftingEnabled = COMMON.isSootedRedstoneCraftingEnabled.get();
        COMMON.livingMatterEXP.forEach((k, v) -> livingMatterEXP.put(k, v.get()));
        COMMON.pristineChance.forEach((k, v) -> pristineChance.put(k, v.get()));
        COMMON.pristineOutputs.forEach((k, v) -> pristineOutputs.put(k, v.get()));
        COMMON.trialRewards.forEach((k, v) -> trialRewards.put(k, v.get()));
        COMMON.dataModelRFCost.forEach((k, v) -> dataModelRFCost.put(k, v.get()));
        COMMON.dataModelMobs.forEach((k, v) -> dataModelMobs.put(k, v.get()));
        COMMON.dataModelExperience.forEach((k, v) -> dataModelExperience.put(k, v.get()));
    }


    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(DeepConstants.MODID)) {
            // Reload the values if something was changed so the config accessors are "up to date"
            refreshClient();
            refreshCommon();
        }
    }

    public static class LootParser {

        public static NonNullList<ItemStack> getTrialRewards(String key) {
            NonNullList<ItemStack> list = NonNullList.create();

            List<? extends String> toParseList;
            if(trialRewards.containsKey(key)) {
                toParseList = trialRewards.get(key);
            } else {
                return list;
            }

            for (String line : toParseList) {
                if (!getStackFromConfigLine(line).isEmpty()) {
                    list.add(getStackFromConfigLine(line));
                }
            }

            return list;
        }

        public static NonNullList<ItemStack> getPristineLootEntries(String key) {
            NonNullList<ItemStack> list = NonNullList.create();

            List<? extends String> toParseList;
            if(pristineOutputs.containsKey(key)) {
                toParseList = pristineOutputs.get(key);
            } else {
                return list;
            }

            for (String line : toParseList) {
                if (!getStackFromConfigLine(line).isEmpty()) {
                    list.add(getStackFromConfigLine(line));
                }
            }

            return list;
        }

        private static ItemStack getStackFromConfigLine(String line) {
            String[] vals = line.split(",");

            if (vals.length < 2) {
                return ItemStack.EMPTY;
            }


            ResourceLocation itemLocation = new ResourceLocation(vals[0]);
            int amount;

            try {
                amount = Integer.parseInt(vals[1]);
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number for amount");
                return ItemStack.EMPTY;
            }


            Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
            if(item != null) {
                return new ItemStack(item, amount);
            } else {
                return ItemStack.EMPTY;
            }
        }
    }
}