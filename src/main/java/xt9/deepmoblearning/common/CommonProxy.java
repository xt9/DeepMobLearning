package xt9.deepmoblearning.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.capabilities.IPlayerTrial;
import xt9.deepmoblearning.common.entity.*;
import xt9.deepmoblearning.common.inventory.ContainerDeepLearner;
import xt9.deepmoblearning.common.inventory.ContainerExtractionChamber;
import xt9.deepmoblearning.common.inventory.ContainerSimulationChamber;
import xt9.deepmoblearning.common.inventory.ContainerTrialKeystone;
import xt9.deepmoblearning.common.items.IGuiItem;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.tiles.IGuiTile;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;
import xt9.deepmoblearning.common.util.Color;


/**
 * Created by xt9 on 2017-06-08.
 */
public class CommonProxy implements IGuiHandler {
    public static boolean openItemGui(EntityPlayer player, EntityEquipmentSlot slot) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        IGuiItem item = (IGuiItem)stack.getItem();

        if(stack.isEmpty() || !(stack.getItem() instanceof IGuiItem)) {
            return false;
        }
        int slotID = slot.getName().equals("mainhand") ? 0 : 1;

        player.openGui(DeepMobLearning.instance, 100 * slotID + item.getGuiID(), player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return true;
    }

    public static void openTileEntityGui(World world, EntityPlayer player, IGuiTile te, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        // Notify on open so we trigger the TE's getUpdateTag
        world.notifyBlockUpdate(pos, state, state, 3);
        player.openGui(DeepMobLearning.instance, te.getGuiID(), player.world, pos.getX(), pos.getY(), pos.getZ());
    }

    public void preInit() {
        // Entities
        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":glitch"), EntityGlitch.class, DeepConstants.MODID + ".glitch", 0, DeepMobLearning.instance, 64, 1, true, 0, Color.AQUA);
        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":glitch_orb"), EntityGlitchOrb.class, DeepConstants.MODID + ".glitch_orb", 1, DeepMobLearning.instance, 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":trial_enderman"), EntityTrialEnderman.class, DeepConstants.MODID + ".trial_enderman", 2, DeepMobLearning.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":trial_spider"), EntityTrialSpider.class, DeepConstants.MODID + ".trial_spider", 3, DeepMobLearning.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":trial_cave_spider"), EntityTrialCaveSpider.class, DeepConstants.MODID + ".trial_cave_spider", 4, DeepMobLearning.instance, 64, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":trial_slime"), EntityTrialSlime.class, DeepConstants.MODID + ".trial_slime", 5, DeepMobLearning.instance, 64, 1, true);

        EntityRegistry.registerModEntity(new ResourceLocation(DeepConstants.MODID + ":item_glitch_fragment"), EntityItemGlitchFragment.class, DeepConstants.MODID + ".item_glitch_fragment", 6, DeepMobLearning.instance, 64, 1, true);

        // Loot tables
        LootTableList.register(new ResourceLocation(DeepConstants.MODID, "glitch"));

    }

    public void init() {
        LootTableList.register(new ResourceLocation(DeepConstants.MODID, "loot_hoarder"));
    }

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        if(ID % 100 == DeepConstants.ITEM_DEEP_LEARNER_GUI_ID && item.getItem() instanceof ItemDeepLearner) {
            return new ContainerDeepLearner(player.inventory, world, slot, item);
        } else {

            switch (ID) {
                case DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID:
                    return new ContainerSimulationChamber((TileEntitySimulationChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                case DeepConstants.TILE_EXTRACTION_CHAMBER_GUI_ID:
                    return new ContainerExtractionChamber((TileEntityExtractionChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                case DeepConstants.TILE_TRIAL_KEYSTONE_GUI_ID:
                    return new ContainerTrialKeystone((TileEntityTrialKeystone) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                default:
                    return null;
            }
        }
    }

    // Client only methods
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public IPlayerTrial getClientPlayerTrialCapability() {
        return null;
    }

    public void spawnGlitchParticle(World world, double x, double y, double z, double mx, double my, double mz) {}
    public void spawnSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz, String type) {}
    public void registerItemRenderer(Item item, ResourceLocation location, int meta) {}

    public void registerRenderers() { }
}
