package xt9.deepmoblearning;

import net.minecraftforge.fml.common.Loader;

/**
 * Created by xt9 on 2017-06-08.
 */
public class DeepConstants {
    // Mod
    public static final String MODID = "deepmoblearning";
    public static final String VERSION = "@VERSION@";

    // GUI ids
    public static final int ITEM_DEEP_LEARNER_GUI_ID = 1;
    public static final int TILE_SIMULATION_CHAMBER_GUI_ID = 2;
    public static final int TILE_EXTRACTION_CHAMBER_GUI_ID = 3;

    // Internal inventory sizes
    public static final int DEEP_LEARNER_INTERNAL_SLOTS_SIZE = 4;

    // Slot references
    public static final int SIMULATION_CHAMBER_DATA_MODEL_SLOT = 0;
    public static final int SIMULATION_CHAMBER_INPUT_SLOT = 1;
    public static final int SIMULATION_CHAMBER_OUTPUT_SLOT = 2;
    public static final int SIMULATION_CHAMBER_PRISTINE_SLOT = 3;

    public static final int EXTRACTION_CHAMBER_INPUT_SLOT = 0;

    // Mob chip max tier
    public static final int MOB_CHIP_MAXIMUM_TIER = 4;

    // Config restraints
    public static final int MAX_DATA_MODEL_COST = 6666;

    // Loaded mod booleans
    public static final boolean MOD_TE_LOADED = Loader.isModLoaded("thermalfoundation");
    public static final boolean MOD_TWILIGHT_LOADED = Loader.isModLoaded("twilightforest");

    public static final class LOOT {
        public static final String[] CREEPER = {
            "minecraft:gunpowder,64,0",
            "minecraft:skull,6,4"
        };

        public static final String[] BLAZE = {
            "minecraft:blaze_rod,22,0",
            "thermalfoundation:material,32,771",
        };

        public static final String[] DRAGON = {
            "minecraft:dragon_breath,32,0",
            "minecraft:dragon_egg,1,0",
            "draconicevolution:dragon_heart,1,0",
            "draconicevolution:draconium_dust,64,0"
        };

        public static final String[] ENDERMAN = {
            "minecraft:ender_pearl,6,0",
            "minecraft:end_crystal,1,0"
        };

        public static final String[] GHAST = {
            "minecraft:ghast_tear,8,0"
        };

        public static final String[] SKELETON = {
            "minecraft:bone,64,0",
            "minecraft:arrow,64,0",
            "minecraft:skull,6,0",
        };

        public static final String[] SLIME = {
            "minecraft:slime_ball,32,0",
        };

        public static final String[] SPIDER = {
            "minecraft:spider_eye,16,0",
            "minecraft:string,64,0",
            "minecraft:web,8,0",
        };

        public static final String[] THERMALELEMENTAL = {
            "thermalfoundation:material,16,772",
            "thermalfoundation:material,16,770",
            "minecraft:snowball,16,0",
            "thermalfoundation:material,8,2050",
            "thermalfoundation:material,8,2052",
            "thermalfoundation:material,8,2048"
        };

        public static final String[] TWILIGHTFOREST = {
            "twilightforest:naga_scale,16,0",
            "twilightforest:charm_of_life_1,2,0",
            "twilightforest:charm_of_keeping_1,2,0",
            "minecraft:paper,64,0",
            "minecraft:book,32,0",
        };

        public static final String[] TWILIGHTSWAMP = {
            "twilightforest:steeleaf_ingot,16,0",
            "twilightforest:ironwood_raw,8,0",
            "twilightforest:fiery_ingot,5,0",
            "twilightforest:hydra_chop,16,0",
            "minecraft:gold_ingot,22,0",
            "minecraft:red_mushroom,32,0",
            "minecraft:slime_ball,16,0"
        };

        public static final String[] TWILIGHTDARKWOOD = {
            "twilightforest:armor_shard_cluster,5,0",
            "twilightforest:carminite,16,0",
            "minecraft:diamond_ore,3,0",
            "minecraft:emerald_ore,2,0",
            "minecraft:fish,32,0"
        };

        public static final String[] TWILIGHTGLACIER = {
            "twilightforest:arctic_fur,16,0",
            "twilightforest:alpha_fur,8,0",
            "minecraft:packed_ice,16,0",
            "minecraft:feather,32,0",
            "twilightforest:charm_of_life_2,1,0",
            "twilightforest:charm_of_keeping_2,1,0",
        };

        public static final String[] WITCH = {
            "minecraft:redstone,32,0",
            "minecraft:glowstone_dust,32,0",
            "minecraft:sugar,64,0",
        };

        public static final String[] WITHERSKELETON = {
            "minecraft:skull,18,1",
            "minecraft:coal,64,0"
        };

        public static final String[] WITHER = {
            "minecraft:nether_star,3,0",
        };

        public static final String[] ZOMBIE = {
            "minecraft:rotten_flesh,64,0",
            "minecraft:iron_ingot,16,0",
            "minecraft:carrot,32,0",
            "minecraft:potato,32,0",
        };
    }
}
