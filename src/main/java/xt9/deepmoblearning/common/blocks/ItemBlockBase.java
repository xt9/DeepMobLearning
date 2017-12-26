package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import xt9.deepmoblearning.DeepMobLearning;

/**
 * Created by xt9 on 2017-06-15.
 */
public class ItemBlockBase extends ItemBlock {
    protected String itemName;

    public ItemBlockBase(String name, int stackSize, Block block) {
        super(block);
        this.setUnlocalizedName(DeepMobLearning.MODID + "." + name);
        this.setRegistryName(name);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }
}
