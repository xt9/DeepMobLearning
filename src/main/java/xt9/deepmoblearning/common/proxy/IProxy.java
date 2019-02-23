package xt9.deepmoblearning.common.proxy;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;

/**
 * Created by xt9 on 2019-02-17.
 */
public interface IProxy {
    // 1.13
    void setup(FMLCommonSetupEvent event);
    PlayerTrial getTrialCapability(EntityPlayer player);
    void spawnGlitchParticle(World world, double x, double y, double z, double mx, double my, double mz);
    void spawnSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz, String type);
    ItemRenderer getClientItemRenderer();
}
