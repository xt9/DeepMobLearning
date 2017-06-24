package xt9.deepmoblearning.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.blocks.BlockBase;
import xt9.deepmoblearning.common.blocks.BlockPolymerPrinter;
import xt9.deepmoblearning.common.blocks.BlockSimulationChamber;
import xt9.deepmoblearning.common.blocks.ItemBlockBase;
import xt9.deepmoblearning.common.items.*;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    public static NonNullList<BlockBase> blocks = NonNullList.create();
    public static BlockSimulationChamber simulationChamber;
    public static BlockPolymerPrinter polymerPrinter;

    public static NonNullList<ItemBase> items = NonNullList.create();
    public static ItemBlankSimulationSummary blankSimulationSummary;
    public static ItemSimulationSummary simulationSummary;
    public static ItemPolymerClay polymerClay;
    public static ItemDeepLearner deepLearner;
    public static ItemMobChip mobChip;


    public static void preInit() {
        // Create our block instances
        simulationChamber = new BlockSimulationChamber();
        blocks.add(simulationChamber);
        polymerPrinter = new BlockPolymerPrinter();
        blocks.add(polymerPrinter);

        // Create our Item instances
        blankSimulationSummary = new ItemBlankSimulationSummary();
        items.add(blankSimulationSummary);
        simulationSummary = new ItemSimulationSummary();
        items.add(simulationSummary);
        polymerClay = new ItemPolymerClay();
        items.add(polymerClay);
        deepLearner = new ItemDeepLearner();
        items.add(deepLearner);
        mobChip = new ItemMobChip();
        items.add(mobChip);


        // TileEntities
        GameRegistry.registerTileEntity(simulationChamber.getTileEntityClass(), DeepMobLearning.MODID + ":simulation_chamber");

        registerBlocks();
        registerItems();
    }

    private static void registerItems() {
        for (ItemBase item : items) {
            GameRegistry.register(item);
            item.registerItemModel();
        }
    }

    private static void registerBlocks() {
        for(BlockBase block : blocks) {
            ItemBlock itemBlock = block.getItemBlock(block);
            GameRegistry.register(block);
            GameRegistry.register(itemBlock);
            block.registerItemModel(itemBlock);

        }
    }
}
