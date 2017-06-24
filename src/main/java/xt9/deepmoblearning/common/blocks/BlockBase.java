package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import xt9.deepmoblearning.DeepMobLearning;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockBase extends Block {
    protected String blockName;

    public BlockBase(String name, Material material) {
        super(material);

        this.blockName = name;
        this.setUnlocalizedName(DeepMobLearning.MODID + "." + name);
        this.setRegistryName(name);
    }

    public void registerItemModel(ItemBlock itemBlock) {
        DeepMobLearning.proxy.registerItemRenderer(itemBlock, 0, blockName);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    public ItemBlock getItemBlock(Block block) {
        return new ItemBlockBase(this.blockName, 64, block);
    }

}
