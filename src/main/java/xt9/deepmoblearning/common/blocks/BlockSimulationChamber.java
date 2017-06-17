package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.util.ItemHandlerHelper;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockSimulationChamber extends BlockBase implements ITileEntityProvider {
    private static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockSimulationChamber() {
        super("simulation_chamber", Material.ROCK);

        setHardness(4f);
        setResistance(10.0f);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntitySimulationChamber tile = this.getTileEntity(world, pos);
        CommonProxy.openTileEntityGui(world, player, tile, pos);
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntitySimulationChamber tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        NonNullList<ItemStack> containedItems = ItemHandlerHelper.getItemStackHandlerList(itemHandler);

        for (int i = 0; i < containedItems.size(); i++) {
            if (!containedItems.get(i).isEmpty()) {
                EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), containedItems.get(i));
                world.spawnEntity(item);
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state, 2);
    }

    public Class<TileEntitySimulationChamber> getTileEntityClass() {
        return TileEntitySimulationChamber.class;
    }

    private TileEntitySimulationChamber getTileEntity(World world, BlockPos pos) {
        return (TileEntitySimulationChamber) world.getTileEntity(pos);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySimulationChamber();
    }



}
