package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2018-05-11.
 */
public class BlockMachineCasing extends Block {
    public BlockMachineCasing() {
        super(Block.Properties.create(Material.ROCK));
        setRegistryName(new ResourceLocation(DeepConstants.MODID, "machine_casing"));
    }
}
