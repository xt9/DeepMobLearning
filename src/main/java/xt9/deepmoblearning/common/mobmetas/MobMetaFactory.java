package xt9.deepmoblearning.common.mobmetas;

import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;

/**
 * Created by xt9 on 2017-12-29.
 */
public class MobMetaFactory {
    public static MobMetaData createMobMetaData(String key) {
        /* Fallback if key does not match any entry */
        MobMetaData meta = new ZombieMeta("zombie", "Zombie", "Zombies", 0, 0, 0, 0, Registry.livingMatterOverworldian, Registry.pristineMatterZombie);

        if(key.equals(MobKey.ZOMBIE)) {
            meta = new ZombieMeta(
                MobKey.ZOMBIE,
                "Zombie",
                "Zombies",
                10,
                35,
                -2,
                6,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterZombie
            );
        } else if(key.equals(MobKey.SKELETON)) {
            meta = new SkeletonMeta(
                MobKey.SKELETON,
                "Skeleton",
                "Skeletons",
                10,
                38,
                6,
                10,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSkeleton
            );
        } else if(key.equals(MobKey.BLAZE)) {
            meta = new BlazeMeta(
                MobKey.BLAZE,
                "Blaze",
                "Blazes",
                10,
                48,
                10,
                20,
                Registry.livingMatterHellish,
                Registry.pristineMatterBlaze
            );
        } else if(key.equals(MobKey.ENDERMAN)) {
            meta = new EndermanMeta(
                MobKey.ENDERMAN,
                "Enderman",
                "Endermen",
                20,
                30,
                5,
                11,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterEnderman
            );
        } else if(key.equals(MobKey.WITHER)) {
            meta = new WitherMeta(
                MobKey.WITHER,
                "Wither",
                "Withers",
                150,
                22,
                3,
                18,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterWither
            );
        } else if(key.equals(MobKey.WITCH)) {
            meta = new WitchMeta(
                MobKey.WITCH,
                "Witch",
                "Witches",
                13,
                34,
                4,
                11,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterWitch
            );
        } else if(key.equals(MobKey.SPIDER)) {
            meta = new SpiderMeta(
                MobKey.SPIDER,
                "Spider",
                "Spiders",
                8,
                30,
                5,
                0,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSpider
            );
        } else if(key.equals(MobKey.CREEPER)) {
            meta = new CreeperMeta(
                MobKey.CREEPER,
                "Creeper",
                "Creepers",
                10,
                42,
                5,
                5,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterCreeper
            );
        } else if(key.equals(MobKey.GHAST)) {
            meta = new GhastMeta(
                MobKey.GHAST,
                "Ghast",
                "Ghasts",
                5,
                10,
                0,
                -20,
                Registry.livingMatterHellish,
                Registry.pristineMatterGhast
            );
        } else if(key.equals(MobKey.WITHERSKELETON)) {
            meta = new WitherSkeletonMeta(
                MobKey.WITHERSKELETON,
                "Wither Skeleton",
                "Wither Skeletons",
                10,
                33,
                5,
                10,
                Registry.livingMatterHellish,
                Registry.pristineMatterWitherSkeleton
            );
        } else if(key.equals(MobKey.SLIME)) {
            meta = new SlimeMeta(
                MobKey.SLIME,
                "Slime",
                "Slimes",
                8,
                60,
                10,
                -16,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSlime
            );
        } else if(key.equals(MobKey.DRAGON)) {
            meta = new DragonMeta(
                MobKey.DRAGON,
                "Ender Dragon",
                "Ender Dragons",
                100,
                7,
                0,
                -20,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterDragon
            );
        } else if(key.equals(MobKey.SHULKER)) {
            meta = new ShulkerMeta(
                MobKey.SHULKER,
                "Shulker",
                "Shulkers",
                15,
                36,
                5,
                -5,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterShulker
            );
        }
        else if(key.equals(MobKey.GUARDIAN)) {
            meta = new GuardianMeta(
                MobKey.GUARDIAN,
                "Guardian",
                "Guardians",
                15,
                36,
                5,
                -5,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterGuardian
            );
        }


        // External mods
        if(DeepConstants.MOD_TE_LOADED && key.equals(MobKey.TE)) {
            meta = new ThermalElementalMeta(MobKey.TE, "Thermal Elementals", "Elementals", 10, 48, 10, 20, Registry.livingMatterOverworldian, Registry.pristineMatterTE);
        }

        if(DeepConstants.MOD_TCON_LOADED && key.equals(MobKey.TINKERSLIME)) {
            meta = new TinkerSlimeMeta(MobKey.TINKERSLIME, "Blue slime", "Blue slimes", 8, 60, 10, -16, Registry.livingMatterOverworldian, Registry.pristineMatterTinkerSlime);
        }

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            if(key.equals(MobKey.TWILIGHTFOREST)) {
                meta = new TwilightForestMeta(
                    MobKey.TWILIGHTFOREST,
                    "Forest creatures",
                    "Forest creatures",
                    0,
                    35,
                    6,
                    12,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightForest);
            } else if(key.equals(MobKey.TWILIGHTSWAMP)) {
                meta = new TwilightSwampMeta(
                    MobKey.TWILIGHTSWAMP,
                    "Swamp creatures",
                    "Swamp creatures",
                    0,
                    33,
                    6,
                    14,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightSwamp
                );
            } else if(key.equals(MobKey.TWILIGHTDARKWOOD)) {
                meta = new TwilightDarkwoodMeta(
                    MobKey.TWILIGHTDARKWOOD,
                    "Darkwood creatures",
                    "Darkwood creatures",
                    0,
                    3,
                    -3,
                    -3,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightDarkwood
                );
            } else if(key.equals(MobKey.TWILIGHTGLACIER)) {
                meta = new TwilightGlacierMeta(
                    MobKey.TWILIGHTGLACIER,
                    "Glacier inhabitants",
                    "Glacier inhabitants",
                    0,
                    33,
                    5,
                    13,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightGlacier
                );
            }
        }

        return meta;
    }
}
