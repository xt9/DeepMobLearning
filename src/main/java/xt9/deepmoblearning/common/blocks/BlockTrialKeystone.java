package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

/**
 * Created by xt9 on 2018-03-25.
 */
public class BlockTrialKeystone extends BlockBase implements ITileEntityProvider {
    private static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0, 0, 0, 1.0D, 0.5D, 1.0D);

    public BlockTrialKeystone() {
        super("trial_keystone", Material.ROCK);
        setHardness(4f);
        setResistance(3000.0f);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!player.isSneaking()) {
            TileEntityTrialKeystone tile = getTileEntity(world, pos);
            CommonProxy.openTileEntityGui(world, player, tile, pos);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityTrialKeystone tile = getTileEntity(world, pos);
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        tile.finishTrial(true, true);

        ItemStack stack = inventory.getStackInSlot(0);
        if(!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            item.setDefaultPickupDelay();
            world.spawnEntity(item);
        }

        super.breakBlock(world, pos, state);
    }

    public Class<TileEntityTrialKeystone> getTileEntityClass() {
        return TileEntityTrialKeystone.class;
    }

    private TileEntityTrialKeystone getTileEntity(World world, BlockPos pos) {
        return (TileEntityTrialKeystone) world.getTileEntity(pos);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTrialKeystone();
    }

}
