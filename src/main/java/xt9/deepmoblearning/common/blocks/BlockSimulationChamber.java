package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockSimulationChamber extends BlockBase {
    public BlockSimulationChamber() {
        super("simulation_chamber", Material.ROCK);

        setHardness(4f);
        setResistance(10.0f);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
