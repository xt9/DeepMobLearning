package xt9.deepmoblearning.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.handlers.BaseItemHandler;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2018-03-25.
 */
public class BlockTrialKeystone extends Block {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    protected static final VoxelShape BOX = Block.makeCuboidShape(0, 0, 0, 16.0D, 8.0D, 16.0D);

    public BlockTrialKeystone() {
        super(Properties.create(Material.ROCK).lightValue(1).hardnessAndResistance(4f, 3000.0f));

        setRegistryName(new ResourceLocation(DeepConstants.MODID, "trial_keystone"));
        setDefaultState(getStateContainer().getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Nullable
    @Override
    @SuppressWarnings("ConstantConditions")
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        EnumFacing enumfacing = context.getPlacementHorizontalFacing().getOpposite();
        return this.getDefaultState().with(FACING, enumfacing);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return BOX;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return false;
        }
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof IInteractionObject && !player.isSneaking()) {
            NetworkHooks.openGui((EntityPlayerMP) player, (IInteractionObject) tile, (buf) -> buf.writeBlockPos(tile.getPos()));
        }
        return true;
    }

    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
        TileEntity tile = world.getTileEntity(pos);

        if(tile instanceof TileEntityTrialKeystone) {
            IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new BaseItemHandler());
            ((TileEntityTrialKeystone) tile).finishTrial(true, true);

            drops.add(new ItemStack(Registry.trialKeystoneItem));
            ItemStack stack = inventory.getStackInSlot(0);
            if(!stack.isEmpty()) {
                drops.add(stack);
            }
        } else {
            super.getDrops(state, drops, world, pos, fortune);
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityTrialKeystone();
    }
}
