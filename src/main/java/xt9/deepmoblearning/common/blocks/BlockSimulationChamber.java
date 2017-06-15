package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockSimulationChamber extends BlockBase implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockSimulationChamber() {
        super("simulation_chamber", Material.ROCK);

        setHardness(4f);
        setResistance(10.0f);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntitySimulationChamber tile = getTileEntity(world, pos);

            if (hitY < .5f) {
                tile.decrementCount();
            } else {
                tile.incrementCount();
            }
            player.sendMessage(new TextComponentString("Current count: " + tile.getCount()));
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
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
