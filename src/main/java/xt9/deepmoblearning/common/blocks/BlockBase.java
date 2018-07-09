package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockBase extends Block {
    protected String blockName;

    public BlockBase(String name, Material material) {
        super(material);
        this.blockName = name;
        setUnlocalizedName(DeepConstants.MODID + "." + name);
        setCreativeTab(DeepMobLearning.creativeTab);
        setRegistryName(name);
        setLightLevel(1F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this, 1));
    }
}
