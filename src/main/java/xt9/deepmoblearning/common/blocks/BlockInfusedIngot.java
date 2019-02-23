package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2018-05-20.
 */
public class BlockInfusedIngot extends Block {

    public BlockInfusedIngot() {
        super(Properties.create(Material.IRON).hardnessAndResistance(4f, 10.0f));

        setRegistryName(new ResourceLocation(DeepConstants.MODID, "infused_ingot_block"));
    }
}
