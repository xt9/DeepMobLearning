package xt9.deepmoblearning.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.blocks.BlockSimulationChamber;
import xt9.deepmoblearning.common.items.*;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    public static BlockSimulationChamber blockSimulationChamber;

    public static NonNullList<ItemBase> items = NonNullList.create();
    public static Item simulationChamber;
    public static ItemPolymerClay polymerClay;
    public static ItemCharredRedstone charredRedstone;
    public static ItemCharredPlate charredPlate;
    public static ItemLivingMatter livingMatter;
    public static ItemPristineMatter pristineMatter;
    public static ItemDeepLearner deepLearner;
    public static ItemMobChip mobChip;
    public static ItemCreativeModelLearner cml;

    public static void registerBlocks(IForgeRegistry registry) {
        blockSimulationChamber = new BlockSimulationChamber();

        // Register tile entities
        GameRegistry.registerTileEntity(blockSimulationChamber.getTileEntityClass(), DeepMobLearning.MODID + ":simulation_chamber");

        // Register our sole block
        registry.registerAll(blockSimulationChamber);
    }

    public static void registerItems(IForgeRegistry registry) {
        // Create our Item instances
        simulationChamber = new ItemBlock(blockSimulationChamber).setRegistryName(blockSimulationChamber.getRegistryName());
        registry.register(simulationChamber);

        charredRedstone = new ItemCharredRedstone();
        items.add(charredRedstone);
        charredPlate = new ItemCharredPlate();
        items.add(charredPlate);
        polymerClay = new ItemPolymerClay();
        items.add(polymerClay);
        deepLearner = new ItemDeepLearner();
        items.add(deepLearner);
        mobChip = new ItemMobChip();
        items.add(mobChip);
        livingMatter = new ItemLivingMatter();
        items.add(livingMatter);
        pristineMatter = new ItemPristineMatter();
        items.add(pristineMatter);
        cml = new ItemCreativeModelLearner();
        items.add(cml);

        for (Item item : items) {
            registry.register(item);
        }
    }

    public static void registerItemModels() {
        blockSimulationChamber.registerItemModel(Item.getItemFromBlock(blockSimulationChamber));

        for (ItemBase item : items) {
            item.registerItemModel();
        }
    }
}
