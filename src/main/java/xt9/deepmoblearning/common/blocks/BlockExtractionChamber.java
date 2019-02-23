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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2017-06-15.
 */
public class BlockExtractionChamber extends Block {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public BlockExtractionChamber() {
        super(Properties.create(Material.ROCK).lightValue(1).hardnessAndResistance(4f, 10.0f));

        setRegistryName(new ResourceLocation(DeepConstants.MODID, "extraction_chamber"));
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

        if(tile instanceof TileEntityExtractionChamber) {
            IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new BaseItemHandler());
            drops.add(new ItemStack(Registry.extractionChamberItem));
            for (int i = 0; i < inventory.getSlots(); i++) {
                drops.add(inventory.getStackInSlot(i));
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
        return new TileEntityExtractionChamber();
    }
}
