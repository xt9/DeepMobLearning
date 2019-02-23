package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockBase extends Block {
    protected String blockName;

    public BlockBase(String name, Material material) {
        super(Properties.create(material).lightValue(1));
        this.blockName = name;

        setRegistryName(new ResourceLocation(DeepConstants.MODID, name));
    }
}