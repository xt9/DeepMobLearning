package xt9.deepmoblearning.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.blocks.BlockExtractionChamber;
import xt9.deepmoblearning.common.blocks.BlockMachineCasing;
import xt9.deepmoblearning.common.blocks.BlockSimulationChamber;
import xt9.deepmoblearning.common.blocks.BlockTrialKeystone;
import xt9.deepmoblearning.common.items.*;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    public static BlockMachineCasing blockMachineCasing;
    public static BlockSimulationChamber blockSimulationChamber;
    public static BlockExtractionChamber blockExtractionChamber;
    public static BlockTrialKeystone blockTrialKeystone;


    // Lists
    public static NonNullList<ItemBase> items = NonNullList.create();
    public static NonNullList<ItemDataModel> dataModels = NonNullList.create();
    public static NonNullList<ItemLivingMatter> livingMatter = NonNullList.create();
    public static NonNullList<ItemPristineMatter> pristineMatter = NonNullList.create();

    public static Item simulationChamber;
    public static Item extractionChamber;
    public static Item trialKeystone;
    public static Item machineCasing;
    public static ItemPolymerClay polymerClay = new ItemPolymerClay();
    public static ItemCharredRedstone charredRedstone = new ItemCharredRedstone();
    public static ItemCharredPlate charredPlate = new ItemCharredPlate();
    public static ItemDeepLearner deepLearner = new ItemDeepLearner();
    public static ItemDataModelBlank dataModelBlank = new ItemDataModelBlank();
    public static ItemCreativeModelLearner cml = new ItemCreativeModelLearner();
    public static ItemTrialKey trialKey = new ItemTrialKey();

    // Data models
    public static ItemDataModel.Zombie dataModelZombie = new ItemDataModel.Zombie();
    public static ItemDataModel.Skeleton dataModelSkeleton = new ItemDataModel.Skeleton();
    public static ItemDataModel.Creeper dataModelCreeper = new ItemDataModel.Creeper();
    public static ItemDataModel.Spider dataModelSpider = new ItemDataModel.Spider();
    public static ItemDataModel.Slime dataModelSlime = new ItemDataModel.Slime();
    public static ItemDataModel.Witch dataModelWitch = new ItemDataModel.Witch();
    public static ItemDataModel.Blaze dataModelBlaze = new ItemDataModel.Blaze();
    public static ItemDataModel.Ghast dataModelGhast = new ItemDataModel.Ghast();
    public static ItemDataModel.WitherSkeleton dataModelWitherSkeleton = new ItemDataModel.WitherSkeleton();
    public static ItemDataModel.Enderman dataModelEnderman = new ItemDataModel.Enderman();
    public static ItemDataModel.Wither dataModelWither = new ItemDataModel.Wither();
    public static ItemDataModel.Dragon dataModelDragon = new ItemDataModel.Dragon();
    public static ItemDataModel.Shulker dataModelShulker = new ItemDataModel.Shulker();
    public static ItemDataModel.Guardian dataModelGuardian = new ItemDataModel.Guardian();
    public static ItemDataModel.TE dataModelTE = new ItemDataModel.TE();
    public static ItemDataModel.TwilightForest dataModelTwilightForest = new ItemDataModel.TwilightForest();
    public static ItemDataModel.TwilightSwamp dataModelTwilightSwamp = new ItemDataModel.TwilightSwamp();
    public static ItemDataModel.TwilightDarkwood dataModelTwilightDarkwood = new ItemDataModel.TwilightDarkwood();
    public static ItemDataModel.TwilightGlacier dataModelTwilightGlacier = new ItemDataModel.TwilightGlacier();
    public static ItemDataModel.TinkerSlime dataModelTinkerSlime = new ItemDataModel.TinkerSlime();

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

    public static void registerBlocks(IForgeRegistry registry) {
        blockSimulationChamber = new BlockSimulationChamber();
        blockExtractionChamber = new BlockExtractionChamber();
        blockTrialKeystone = new BlockTrialKeystone();
        blockMachineCasing = new BlockMachineCasing();

        // Register tile entities
        GameRegistry.registerTileEntity(blockSimulationChamber.getTileEntityClass(), DeepConstants.MODID + ":simulation_chamber");
        GameRegistry.registerTileEntity(blockExtractionChamber.getTileEntityClass(), DeepConstants.MODID + ":extraction_chamber");
        GameRegistry.registerTileEntity(blockTrialKeystone.getTileEntityClass(), DeepConstants.MODID + ":trial_keystone");

        // Register our sole block
        registry.registerAll(blockMachineCasing, blockSimulationChamber, blockExtractionChamber, blockTrialKeystone);
    }

    public static void registerItems(IForgeRegistry registry) {
        populateDataModelList();
        populateLivingMatterList();
        populatePristineMatterList();

        // Create our Item instances
        machineCasing = new ItemBlock(blockMachineCasing).setRegistryName(blockMachineCasing.getRegistryName());
        simulationChamber = new ItemBlock(blockSimulationChamber).setRegistryName(blockSimulationChamber.getRegistryName());
        extractionChamber = new ItemBlock(blockExtractionChamber).setRegistryName(blockExtractionChamber.getRegistryName());
        trialKeystone = new ItemBlock(blockTrialKeystone).setRegistryName(blockTrialKeystone.getRegistryName());
        registry.registerAll(machineCasing, simulationChamber, extractionChamber, trialKeystone);

        items.add(charredRedstone);
        items.add(charredPlate);
        items.add(polymerClay);
        items.add(deepLearner);
        items.add(dataModelBlank);
        items.add(cml);
        items.add(trialKey);
        items.addAll(dataModels);
        items.addAll(livingMatter);
        items.addAll(pristineMatter);

        for (Item item : items) {
            registry.register(item);
        }
    }

    public static void registerItemModels() {
        blockMachineCasing.registerItemModel(Item.getItemFromBlock(blockMachineCasing));
        blockSimulationChamber.registerItemModel(Item.getItemFromBlock(blockSimulationChamber));
        blockExtractionChamber.registerItemModel(Item.getItemFromBlock(blockExtractionChamber));
        blockTrialKeystone.registerItemModel(Item.getItemFromBlock(blockTrialKeystone));

        for (ItemBase item : items) {
            item.registerItemModel();
        }
    }
}
