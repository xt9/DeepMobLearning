package xt9.deepmoblearning.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.blocks.*;
import xt9.deepmoblearning.common.items.*;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    public static BlockMachineCasing blockMachineCasing;
    public static BlockInfusedIngot blockInfusedIngot;
    public static BlockSimulationChamber blockSimulationChamber;
    public static BlockExtractionChamber blockExtractionChamber;
    public static BlockTrialKeystone blockTrialKeystone;

    // Lists
    public static NonNullList<Item> items = NonNullList.create();
    public static NonNullList<Block> blocks = NonNullList.create();
    public static NonNullList<ItemDataModel> dataModels = NonNullList.create();
    public static NonNullList<ItemLivingMatter> livingMatter = NonNullList.create();
    public static NonNullList<ItemPristineMatter> pristineMatter = NonNullList.create();

    public static Item simulationChamberItem;
    public static Item extractionChamberItem;
    public static Item trialKeystoneItem;
    public static Item machineCasingItem;
    public static Item infusedIngotBlockItem;
    public static ItemPolymerClay polymerClay = new ItemPolymerClay();
    public static ItemFallbackGuidebook fallbackGuidebook = new ItemFallbackGuidebook();
    public static ItemSootedRedstone sootedRedstone = new ItemSootedRedstone();
    public static ItemSootedPlate sootedPlate = new ItemSootedPlate();
    public static ItemDeepLearner deepLearner = new ItemDeepLearner();
    public static ItemDataModelBlank dataModelBlank = new ItemDataModelBlank();
    public static ItemCreativeModelLearner cml = new ItemCreativeModelLearner();
    public static ItemTrialKey trialKey = new ItemTrialKey();
    public static ItemGlitchHeart glitchHeart = new ItemGlitchHeart();
    public static ItemGlitchFragment glitchFragment = new ItemGlitchFragment();
    public static ItemGlitchIngot glitchInfusedIngot = new ItemGlitchIngot();

    // Armor & Sword
    public static ItemGlitchArmor.ItemGlitchHelmet glitchInfusedHelmet = new ItemGlitchArmor.ItemGlitchHelmet();
    public static ItemGlitchArmor.ItemGlitchChestplate glitchInfusedChestplate = new ItemGlitchArmor.ItemGlitchChestplate();
    public static ItemGlitchArmor.ItemGlitchLeggings glitchInfusedLeggings = new ItemGlitchArmor.ItemGlitchLeggings();
    public static ItemGlitchArmor.ItemGlitchBoots glitchInfusedBoots = new ItemGlitchArmor.ItemGlitchBoots();
    public static ItemGlitchSword glitchInfusedSword = new ItemGlitchSword();


    // Data models
    private static ItemDataModel.Zombie dataModelZombie = new ItemDataModel.Zombie();
    private static ItemDataModel.Skeleton dataModelSkeleton = new ItemDataModel.Skeleton();
    private static ItemDataModel.Creeper dataModelCreeper = new ItemDataModel.Creeper();
    private static ItemDataModel.Spider dataModelSpider = new ItemDataModel.Spider();
    private static ItemDataModel.Slime dataModelSlime = new ItemDataModel.Slime();
    private static ItemDataModel.Witch dataModelWitch = new ItemDataModel.Witch();
    private static ItemDataModel.Blaze dataModelBlaze = new ItemDataModel.Blaze();
    private static ItemDataModel.Ghast dataModelGhast = new ItemDataModel.Ghast();
    private static ItemDataModel.WitherSkeleton dataModelWitherSkeleton = new ItemDataModel.WitherSkeleton();
    private static ItemDataModel.Enderman dataModelEnderman = new ItemDataModel.Enderman();
    private static ItemDataModel.Wither dataModelWither = new ItemDataModel.Wither();
    private static ItemDataModel.Dragon dataModelDragon = new ItemDataModel.Dragon();
    private static ItemDataModel.Shulker dataModelShulker = new ItemDataModel.Shulker();
    private static ItemDataModel.Guardian dataModelGuardian = new ItemDataModel.Guardian();
    private static ItemDataModel.TE dataModelTE = new ItemDataModel.TE();
    private static ItemDataModel.TwilightForest dataModelTwilightForest = new ItemDataModel.TwilightForest();
    private static ItemDataModel.TwilightSwamp dataModelTwilightSwamp = new ItemDataModel.TwilightSwamp();
    private static ItemDataModel.TwilightDarkwood dataModelTwilightDarkwood = new ItemDataModel.TwilightDarkwood();
    private static ItemDataModel.TwilightGlacier dataModelTwilightGlacier = new ItemDataModel.TwilightGlacier();
    private static ItemDataModel.TinkerSlime dataModelTinkerSlime = new ItemDataModel.TinkerSlime();

    // Living matter
    public static ItemLivingMatter.Overworldian livingMatterOverworldian = new ItemLivingMatter.Overworldian();
    public static ItemLivingMatter.Hellish livingMatterHellish = new ItemLivingMatter.Hellish();
    public static ItemLivingMatter.Extraterrestrial livingMatterExtraterrestrial = new ItemLivingMatter.Extraterrestrial();
    public static ItemLivingMatter.Twilight livingMatterTwilight = new ItemLivingMatter.Twilight();

    // Pristine matter
    public static ItemPristineMatter.Zombie pristineMatterZombie = new ItemPristineMatter.Zombie();
    public static ItemPristineMatter.Skeleton pristineMatterSkeleton = new ItemPristineMatter.Skeleton();
    public static ItemPristineMatter.Creeper pristineMatterCreeper = new ItemPristineMatter.Creeper();
    public static ItemPristineMatter.Spider pristineMatterSpider = new ItemPristineMatter.Spider();
    public static ItemPristineMatter.Slime pristineMatterSlime = new ItemPristineMatter.Slime();
    public static ItemPristineMatter.Witch pristineMatterWitch = new ItemPristineMatter.Witch();
    public static ItemPristineMatter.Blaze pristineMatterBlaze = new ItemPristineMatter.Blaze();
    public static ItemPristineMatter.Ghast pristineMatterGhast = new ItemPristineMatter.Ghast();
    public static ItemPristineMatter.WitherSkeleton pristineMatterWitherSkeleton = new ItemPristineMatter.WitherSkeleton();
    public static ItemPristineMatter.Enderman pristineMatterEnderman = new ItemPristineMatter.Enderman();
    public static ItemPristineMatter.Wither pristineMatterWither = new ItemPristineMatter.Wither();
    public static ItemPristineMatter.Dragon pristineMatterDragon = new ItemPristineMatter.Dragon();
    public static ItemPristineMatter.Shulker pristineMatterShulker = new ItemPristineMatter.Shulker();
    public static ItemPristineMatter.Guardian pristineMatterGuardian = new ItemPristineMatter.Guardian();
    public static ItemPristineMatter.TE pristineMatterTE = new ItemPristineMatter.TE();
    public static ItemPristineMatter.TwilightForest pristineMatterTwilightForest = new ItemPristineMatter.TwilightForest();
    public static ItemPristineMatter.TwilightSwamp pristineMatterTwilightSwamp = new ItemPristineMatter.TwilightSwamp();
    public static ItemPristineMatter.TwilightDarkwood pristineMatterTwilightDarkwood = new ItemPristineMatter.TwilightDarkwood();
    public static ItemPristineMatter.TwilightGlacier pristineMatterTwilightGlacier = new ItemPristineMatter.TwilightGlacier();
    public static ItemPristineMatter.TinkerSlime pristineMatterTinkerSlime = new ItemPristineMatter.TinkerSlime();

    /* Init the list of data models */
    private static void populateDataModelList() {
        dataModels.add(dataModelZombie);
        dataModels.add(dataModelSkeleton);
        dataModels.add(dataModelCreeper);
        dataModels.add(dataModelSpider);
        dataModels.add(dataModelSlime);
        dataModels.add(dataModelWitch);
        dataModels.add(dataModelBlaze);
        dataModels.add(dataModelGhast);
        dataModels.add(dataModelWitherSkeleton);
        dataModels.add(dataModelEnderman);
        dataModels.add(dataModelWither);
        dataModels.add(dataModelDragon);
        dataModels.add(dataModelShulker);
        dataModels.add(dataModelGuardian);

        if(DeepConstants.MOD_TE_LOADED) {
            dataModels.add(dataModelTE);
        }

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            dataModels.add(dataModelTwilightForest);
            dataModels.add(dataModelTwilightSwamp);
            dataModels.add(dataModelTwilightDarkwood);
            dataModels.add(dataModelTwilightGlacier);

        }

        if(DeepConstants.MOD_TCON_LOADED) {
            dataModels.add(dataModelTinkerSlime);
        }
    }

    private static void populateLivingMatterList() {
        livingMatter.add(livingMatterOverworldian);
        livingMatter.add(livingMatterHellish);
        livingMatter.add(livingMatterExtraterrestrial);

        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            livingMatter.add(livingMatterTwilight);
        }
    }

    private static void populatePristineMatterList() {
        pristineMatter.add(pristineMatterZombie);
        pristineMatter.add(pristineMatterSkeleton);
        pristineMatter.add(pristineMatterCreeper);
        pristineMatter.add(pristineMatterSpider);
        pristineMatter.add(pristineMatterSlime);
        pristineMatter.add(pristineMatterWitch);
        pristineMatter.add(pristineMatterBlaze);
        pristineMatter.add(pristineMatterGhast);
        pristineMatter.add(pristineMatterWitherSkeleton);
        pristineMatter.add(pristineMatterEnderman);
        pristineMatter.add(pristineMatterWither);
        pristineMatter.add(pristineMatterDragon);
        pristineMatter.add(pristineMatterShulker);
        pristineMatter.add(pristineMatterGuardian);

        if(DeepConstants.MOD_TE_LOADED) {
            pristineMatter.add(pristineMatterTE);
        }
        if(DeepConstants.MOD_TCON_LOADED) {
            pristineMatter.add(pristineMatterTinkerSlime);
        }
        if(DeepConstants.MOD_TWILIGHT_LOADED) {
            pristineMatter.add(pristineMatterTwilightForest);
            pristineMatter.add(pristineMatterTwilightSwamp);
            pristineMatter.add(pristineMatterTwilightDarkwood);
            pristineMatter.add(pristineMatterTwilightGlacier);
        }
    }

    @SuppressWarnings("unchecked")
    public static void registerBlocks(IForgeRegistry registry) {
        blockSimulationChamber = new BlockSimulationChamber();
        blockExtractionChamber = new BlockExtractionChamber();
        blockTrialKeystone = new BlockTrialKeystone();
        blockMachineCasing = new BlockMachineCasing();
        blockInfusedIngot = new BlockInfusedIngot();

        // Register tile entities
        GameRegistry.registerTileEntity(blockSimulationChamber.getTileEntityClass(), new ResourceLocation(DeepConstants.MODID, "simulation_chamber"));
        GameRegistry.registerTileEntity(blockExtractionChamber.getTileEntityClass(), new ResourceLocation(DeepConstants.MODID, "extraction_chamber"));
        GameRegistry.registerTileEntity(blockTrialKeystone.getTileEntityClass(), new ResourceLocation(DeepConstants.MODID, "trial_keystone"));

        blocks.add(blockMachineCasing);
        blocks.add(blockInfusedIngot);
        blocks.add(blockSimulationChamber);
        blocks.add(blockExtractionChamber);
        blocks.add(blockTrialKeystone);
        blocks.forEach(registry::register);
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static void registerItems(IForgeRegistry registry) {
        populateDataModelList();
        populateLivingMatterList();
        populatePristineMatterList();

        // Create our Item instances
        machineCasingItem = new ItemBlock(blockMachineCasing).setRegistryName(blockMachineCasing.getRegistryName());
        infusedIngotBlockItem = new ItemBlock(blockInfusedIngot).setRegistryName(blockInfusedIngot.getRegistryName());
        simulationChamberItem = new ItemBlock(blockSimulationChamber).setRegistryName(blockSimulationChamber.getRegistryName());
        extractionChamberItem = new ItemBlock(blockExtractionChamber).setRegistryName(blockExtractionChamber.getRegistryName());
        trialKeystoneItem = new ItemBlock(blockTrialKeystone).setRegistryName(blockTrialKeystone.getRegistryName());
        registry.registerAll(machineCasingItem, infusedIngotBlockItem, simulationChamberItem, extractionChamberItem, trialKeystoneItem);

        items.add(sootedRedstone);
        items.add(sootedPlate);
        items.add(polymerClay);
        items.add(deepLearner);
        items.add(dataModelBlank);
        items.add(cml);
        items.add(trialKey);
        items.add(glitchHeart);
        items.add(glitchFragment);
        items.add(glitchInfusedIngot);
        items.add(glitchInfusedHelmet);
        items.add(glitchInfusedChestplate);
        items.add(glitchInfusedLeggings);
        items.add(glitchInfusedBoots);
        items.add(glitchInfusedSword);

        items.addAll(dataModels);
        items.addAll(livingMatter);
        items.addAll(pristineMatter);

        if(!DeepConstants.MOD_PATCHOULI_LOADED) {
            items.add(fallbackGuidebook);
        }

        for (Item item : items) {
            registry.register(item);
        }
    }

    public static void registerItemModels() {
        for (Block block : blocks) {
            DeepMobLearning.proxy.registerItemRenderer(Item.getItemFromBlock(block), block.getRegistryName(), 0);
        }

        for (Item item : items) {
            DeepMobLearning.proxy.registerItemRenderer(item, item.getRegistryName(), 0);
        }
    }
}
