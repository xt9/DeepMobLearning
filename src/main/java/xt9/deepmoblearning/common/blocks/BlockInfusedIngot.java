package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.material.Material;

/**
 * Created by xt9 on 2018-05-20.
 */
public class BlockInfusedIngot extends BlockBase {
    public BlockInfusedIngot() {
        super("infused_ingot_block", Material.IRON);
        setHardness(4f);
        setResistance(10.0f);
    }
}
