package xt9.deepmoblearning.common.proxy;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.capabilities.PlayerProperties;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;

import javax.annotation.Nullable;


/**
 * Created by xt9 on 2017-06-08.
 */
public class ServerProxy implements IProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(PlayerTrial.class, new Capability.IStorage<PlayerTrial>() {
            @Nullable
            @Override
            public INBTBase writeNBT(Capability<PlayerTrial> capability, PlayerTrial instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerTrial> capability, PlayerTrial instance, EnumFacing side, INBTBase nbt) {
                throw new UnsupportedOperationException();
            }
        }, () -> null);


        /* Register loot tables */
        LootTableList.register(new ResourceLocation(DeepConstants.MODID, "entityGlitch"));
        LootTableList.register(new ResourceLocation(DeepConstants.MODID, "loot_hoarder"));
    }

    @Override
    public PlayerTrial getTrialCapability(EntityPlayer player) {
        return player.getCapability(PlayerProperties.playerTrialCap).orElse(new PlayerTrial());
    }

    @Override
    public void spawnGlitchParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        throw new IllegalStateException("Accessing a Client only method from the Server");
    }

    @Override
    public void spawnSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz, String type) {
        throw new IllegalStateException("Accessing a Client only method from the Server");
    }

    @Override
    public ItemRenderer getClientItemRenderer() {
        throw new IllegalStateException("Accessing a Client only method from the Server");
    }
}
