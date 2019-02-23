package xt9.deepmoblearning.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemBlock;

/**
 * Created by xt9 on 2019-02-20.
 */
@SuppressWarnings("ConstantConditions")
public class ItemBlockPlacer extends ItemBlock {
    private boolean canPlace;

    public ItemBlockPlacer(Block block, Properties props, boolean canPlace) {
        super(block, props);
        this.canPlace = canPlace;
        setRegistryName(block.getRegistryName());
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, IBlockState state) {
        return canPlace && super.canPlace(context, state);
    }
}
