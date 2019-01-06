package xt9.deepmoblearning;

import net.minecraftforge.fml.common.Loader;

/**
 * Created by xt9 on 2017-06-08.
 */
public class DeepConstants {
    // Mod
    public static final String MODID = "deepmoblearning";
    public static final String VERSION = "@VERSION@";

    // Minecraft logic
    public static final int TICKS_TO_SECOND = 20;

    // GUI ids
    public static final int ITEM_DEEP_LEARNER_GUI_ID = 1;
    public static final int TILE_SIMULATION_CHAMBER_GUI_ID = 2;
    public static final int TILE_EXTRACTION_CHAMBER_GUI_ID = 3;
    public static final int TILE_TRIAL_KEYSTONE_GUI_ID = 4;

    // Internal inventory sizes
    public static final int DEEP_LEARNER_INTERNAL_SLOTS_SIZE = 4;

    // Data model max tier
    public static final int DATA_MODEL_MAXIMUM_TIER = 4;

    // Config restraints
    public static final int MAX_DATA_MODEL_COST = 6666;

    // Loaded mod booleans
    public static final boolean MOD_TE_LOADED = Loader.isModLoaded("thermalfoundation");
    public static final boolean MOD_TWILIGHT_LOADED = Loader.isModLoaded("twilightforest");
    public static final boolean MOD_TCON_LOADED = Loader.isModLoaded("tconstruct");
    public static final boolean MOD_PATCHOULI_LOADED = Loader.isModLoaded("patchouli");

    // NBT references
    public static final String NBT_STRING_AFFIX_CONNECTION = DeepConstants.MODID + ":mob_type";

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
            "minecraft:end_crystal,1,0",
            "enderio:block_enderman_skull,2,0"
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

        public static final String[] TINKERSLIME = {
            "tconstruct:edible,18,1",
            "tconstruct:edible,18,2",
            "tconstruct:edible,18,4",
            "tconstruct:slime_sapling,4,0",
            "tconstruct:slime_sapling,4,1",
            "tconstruct:slime_sapling,4,2",
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
            "minecraft:potato,32,0"
        };

        public static final String[] SHULKER = {
            "minecraft:shulker_shell,18,0",
            "minecraft:diamond,2,0"
        };

        public static final String[] GUARDIAN = {
            "minecraft:prismarine_shard,32,0",
            "minecraft:prismarine_crystals,32,0",
            "minecraft:fish,64,0"
        };
    }

    public static final class TRIAL_REWARD {
        public static final String[] ZOMBIE = {
            "deepmoblearning:glitch_heart,3,0"
        };

        public static final String[] ENDERMAN = {
            "deepmoblearning:glitch_heart,5,0"
        };

        public static final String[] SKELETON = {
            "deepmoblearning:glitch_heart,3,0"
        };

        public static final String[] WITCH = {
            "deepmoblearning:glitch_heart,6,0"
        };

        public static final String[] SPIDER = {
            "deepmoblearning:glitch_heart,4,0"
        };

        public static final String[] SLIME = {
            "deepmoblearning:glitch_heart,3,0"
        };

        public static final String[] WITHERSKELETON = {
            "deepmoblearning:glitch_heart,5,0"
        };
    }

    public static final class MOBS {
        public static final String[] CREEPER = {
                "minecraft:creeper"
        };

        public static final String[] BLAZE = {
                "minecraft:blaze"
        };

        public static final String[] DRAGON = {
                "minecraft:ender_dragon"
        };

        public static final String[] ENDERMAN = {
                "minecraft:enderman",
                "deepmoblearning:trial_enderman"
        };

        public static final String[] GHAST = {
                "minecraft:ghast"
        };

        public static final String[] SKELETON = {
                "minecraft:stray",
                "minecraft:skeleton",
                "twilightforest:skeleton_druid"
        };

        public static final String[] SLIME = {
                "minecraft:slime",
                "minecraft:magma_cube",
                "deepmoblearning:trial_slime",
        };

        public static final String[] SPIDER = {
                "minecraft:spider",
                "minecraft:cave_spider",
                "twilightforest:hedge_spider",
                "twilightforest:king_spider",
                "deepmoblearning:trial_spider",
                "deepmoblearning:trial_cave_spider",
        };

        public static final String[] THERMALELEMENTAL = {
                "thermalfoundation:blizz",
                "thermalfoundation:blitz",
                "thermalfoundation:basalz",
        };

        public static final String[] TINKERSLIME = {
                "tconstruct:blueslime"
        };

        public static final String[] TWILIGHTFOREST = {
                "twilightforest:naga",
                "twilightforest:lich_minion",
                "twilightforest:lich",
                "twilightforest:death_tome",
                "twilightforest:swarm_spider",
        };

        public static final String[] TWILIGHTSWAMP = {
                "twilightforest:minotaur",
                "twilightforest:minoshroom",
                "twilightforest:maze_slime",
                "twilightforest:fire_beetle",
                "twilightforest:pinch_beetle",
                "twilightforest:slime_beetle",
                "twilightforest:hydra",
        };

        public static final String[] TWILIGHTDARKWOOD = {
                "twilightforest:redcap",
                "twilightforest:blockchain_goblin",
                "twilightforest:kobold",
                "twilightforest:goblin_knight_lower",
                "twilightforest:goblin_knight_upper",
                "twilightforest:helmet_crab",
                "twilightforest:knight_phantom",
                "twilightforest:tower_ghast",
                "twilightforest:tower_broodling",
                "twilightforest:tower_golem",
                "twilightforest:tower_termite",
                "twilightforest:mini_ghast",
                "twilightforest:ur_ghast",
        };

        public static final String[] TWILIGHTGLACIER = {
                "twilightforest:yeti_alpha",
                "twilightforest:yeti",
                "twilightforest:winter_wolf",
                "twilightforest:penguin",
                "twilightforest:snow_guardian",
                "twilightforest:stable_ice_core",
                "twilightforest:unstable_ice_core",
                "twilightforest:snow_queen",
        };

        public static final String[] WITCH = {
                "minecraft:witch"
        };

        public static final String[] WITHERSKELETON = {
                "minecraft:wither_skeleton"
        };

        public static final String[] WITHER = {
                "minecraft:wither"
        };

        public static final String[] ZOMBIE = {
                "minecraft:husk",
                "minecraft:zombie",
                "minecraft:zombie_villager",
                "minecraft:zombie_pigman",
        };

        public static final String[] SHULKER = {
                "minecraft:shulker"
        };

        public static final String[] GUARDIAN = {
                "minecraft:elder_guardian",
                "minecraft:guardian",
        };
    }
}
