package xt9.deepmoblearning.common.mobs;

import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.items.ItemDataModel;

/**
 * Created by xt9 on 2017-12-29.
 */
public class MobMetaFactory {
    public static MobMetaData createMobMetaData(ItemStack stack) {
        MobMetaData meta = new ZombieMeta("zombie", "Zombie", "Zombies", 0, 0, 0, 0, Registry.livingMatterOverworldian, Registry.pristineMatterZombie);

        if(stack.getItem() instanceof ItemDataModel.Zombie) {
            meta = new ZombieMeta(
                "zombie",
                "Zombie",
                "Zombies",
                10,
                35,
                -2,
                6,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterZombie
            );
        } else if(stack.getItem() instanceof ItemDataModel.Skeleton) {
            meta = new SkeletonMeta(
                "skeleton",
                "Skeleton",
                "Skeletons",
                10,
                38,
                6,
                10,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSkeleton
            );
        } else if(stack.getItem() instanceof ItemDataModel.Blaze) {
            meta = new BlazeMeta(
                "blaze",
                "Blaze",
                "Blazes",
                10,
                48,
                10,
                20,
                Registry.livingMatterHellish,
                Registry.pristineMatterBlaze
            );
        } else if(stack.getItem() instanceof ItemDataModel.Enderman) {
            meta = new EndermanMeta(
                "enderman",
                "Enderman",
                "Endermen",
                20,
                30,
                5,
                11,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterEnderman
            );
        } else if(stack.getItem() instanceof ItemDataModel.Wither) {
            meta = new WitherMeta(
                "wither",
                "Wither",
                "Withers",
                150,
                22,
                3,
                18,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterWither
            );
        } else if(stack.getItem() instanceof ItemDataModel.Witch) {
            meta = new WitchMeta(
                "witch",
                "Witch",
                "Witches",
                13,
                34,
                4,
                11,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterWitch
            );
        } else if(stack.getItem() instanceof ItemDataModel.Spider) {
            meta = new SpiderMeta(
                "spider",
                "Spider",
                "Spiders",
                8,
                30,
                5,
                0,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSpider
            );
        } else if(stack.getItem() instanceof ItemDataModel.Creeper) {
            meta = new CreeperMeta(
                "creeper",
                "Creeper",
                "Creepers",
                10,
                42,
                5,
                5,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterCreeper
            );
        } else if(stack.getItem() instanceof ItemDataModel.Ghast) {
            meta = new GhastMeta(
                "ghast",
                "Ghast",
                "Ghasts",
                5,
                10,
                0,
                -20,
                Registry.livingMatterHellish,
                Registry.pristineMatterGhast
            );
        } else if(stack.getItem() instanceof ItemDataModel.WitherSkeleton) {
            meta = new WitherSkeletonMeta(
                "witherskeleton",
                "Wither Skeleton",
                "Wither Skeletons",
                10,
                33,
                5,
                10,
                Registry.livingMatterHellish,
                Registry.pristineMatterWitherSkeleton
            );
        } else if(stack.getItem() instanceof ItemDataModel.Slime) {
            meta = new SlimeMeta(
                "slime",
                "Slime",
                "Slimes",
                8,
                60,
                10,
                -16,
                Registry.livingMatterOverworldian,
                Registry.pristineMatterSlime
            );
        } else if(stack.getItem() instanceof ItemDataModel.Dragon) {
            meta = new DragonMeta(
                "dragon",
                "Ender Dragon",
                "Ender Dragons",
                100,
                7,
                0,
                -20,
                Registry.livingMatterExtraterrestrial,
                Registry.pristineMatterDragon
            );
        }


        // External mods
        if(DeepConstants.MOD_TE_LOADED && stack.getItem() instanceof ItemDataModel.TE) {
            meta = new ThermalElementalMeta("thermalelemental", "Thermal Elementals", "Elementals", 10, 48, 10, 20, Registry.livingMatterOverworldian, Registry.pristineMatterTE);
        }

        if(DeepConstants.MOD_TCON_LOADED && stack.getItem() instanceof ItemDataModel.TinkerSlime) {
            meta = new TinkerSlimeMeta("tinkerslime", "Blue slime", "Blue slimes", 8, 60, 10, -16, Registry.livingMatterOverworldian, Registry.pristineMatterTinkerSlime);
        }

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            if(stack.getItem() instanceof ItemDataModel.TwilightForest) {
                meta = new TwilightForestMeta(
                    "twilightforest",
                    "Forest creatures",
                    "Forest creatures",
                    0,
                    35,
                    6,
                    12,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterWitch);
            } else if(stack.getItem() instanceof ItemDataModel.TwilightSwamp) {
                meta = new TwilightSwampMeta(
                    "twilightswamp",
                    "Swamp creatures",
                    "Swamp creatures",
                    0,
                    33,
                    6,
                    14,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightSwamp
                );
            } else if(stack.getItem() instanceof ItemDataModel.TwilightDarkwood) {
                meta = new TwilightDarkwoodMeta(
                    "twilightdarkwood",
                    "Darkwood creatures",
                    "Darkwood creatures",
                    0,
                    3,
                    -3,
                    -3,
                    Registry.livingMatterTwilight,
                    Registry.pristineMatterTwilightDarkwood
                );
            } else if(stack.getItem() instanceof ItemDataModel.TwilightGlacier) {
                meta = new TwilightGlacierMeta(
                    "twilightglacier",
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
