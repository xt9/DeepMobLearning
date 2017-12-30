package xt9.deepmoblearning.common.mobs;

/**
 * Created by xt9 on 2017-12-29.
 */
public class MobMetaFactory {
    public static MobMetaData createMobMetaData(String mobKey) {
        MobMetaData meta;

        switch(mobKey) {
            case "zombie":
                meta = new ZombieMeta(
                    "zombie",
                    "Zombie",
                    "Zombies",
                    10,
                    35,
                    -2,
                    6,
                    0
                ); break;
            case "skeleton": 
                meta = new SkeletonMeta(
                    "skeleton", 
                    "Skeleton", 
                    "Skeletons",
                    10,
                    38,
                    6,
                    10,
                    0
                ); break;
            case "blaze": 
                meta = new BlazeMeta(
                    "blaze",
                    "Blaze",
                    "Blazes",
                    10,
                    48,
                    10,
                    20,
                    1
                ); break;
            case "enderman": 
                meta = new EndermanMeta(
                    "enderman",
                    "Enderman",
                    "Endermen",
                    20,
                    30,
                    5,
                    11,
                    2
                ); break;
            case "wither": 
                meta = new WitherMeta(
                    "wither",
                    "Wither",
                    "Withers",
                    150,
                    22,
                    3,
                    18,
                    2
                ); break;
            case "witch": 
                meta = new WitchMeta(
                    "witch",
                    "Witch",
                    "Witches",
                    13,
                    34,
                    4,
                    11,
                    0
                ); break;
            case "spider":
                meta = new SpiderMeta(
                    "spider",
                    "Spider",
                    "Spiders",
                    8,
                    30,
                    5,
                    0,
                    0
                ); break;
            case "creeper":
                meta = new CreeperMeta(
                    "creeper",
                    "Creeper",
                    "Creepers",
                    10,
                    42,
                    5,
                    5,
                    0
                ); break;
            case "ghast":
                meta = new GhastMeta(
                    "ghast",
                    "Ghast",
                    "Ghasts",
                    5,
                    10,
                    0,
                    -20,
                    1
                ); break;
            case "witherskeleton":
                meta = new WitherSkeletonMeta(
                    "witherskeleton",
                    "Wither Skeleton",
                    "Wither Skeletons",
                    10,
                    33,
                    5,
                    10,
                    1
                ); break;
            case "slime":
                meta = new SlimeMeta(
                    "slime",
                    "Slime",
                    "Slimes",
                    8,
                    60,
                    10,
                    -16,
                    0
                ); break;
            case "dragon":
                meta = new DragonMeta(
                    "dragon",
                    "Ender Dragon",
                    "Ender Dragons",
                    100,
                    7,
                    0,
                    -20,
                    2
                ); break;
            default:
                meta = new ZombieMeta("zombie", "Zombie", "Zombies", 10, 35, -2, 6, 0); break;
        }

        return meta;
    }
}
