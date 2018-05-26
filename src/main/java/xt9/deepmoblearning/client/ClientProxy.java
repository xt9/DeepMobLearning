package xt9.deepmoblearning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderCaveSpider;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.client.gui.*;
import xt9.deepmoblearning.client.particle.ParticleGlitch;
import xt9.deepmoblearning.client.particle.ParticleScalableSmoke;
import xt9.deepmoblearning.client.renders.*;
import xt9.deepmoblearning.common.CommonProxy;
import xt9.deepmoblearning.common.capabilities.IPlayerTrial;
import xt9.deepmoblearning.common.capabilities.PlayerTrialProvider;
import xt9.deepmoblearning.common.entity.*;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.tiles.TileEntityExtractionChamber;
import xt9.deepmoblearning.common.tiles.TileEntitySimulationChamber;
import xt9.deepmoblearning.common.tiles.TileEntityTrialKeystone;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
        RenderingRegistry.registerEntityRenderingHandler(EntityGlitch.class, RenderEntityGlitch::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlitchOrb.class, RenderEntityGlitchOrb::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityTrialEnderman.class, RenderEnderman::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialSpider.class, RenderSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialCaveSpider.class, RenderCaveSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialSlime.class, RenderSlime::new);
    }

    @Override
    public void registerRenderers() {
        MinecraftForge.EVENT_BUS.register(new DataOverlay(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new TrialOverlay(Minecraft.getMinecraft()));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrialKeystone.class, new TESRTrialKeystone());
    }

    @Override
    public void registerItemRenderer(Item item, ResourceLocation location, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(location, "inventory"));
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // Find which slot triggered this
        EntityEquipmentSlot slot = EntityEquipmentSlot.values()[ID/100];
        ItemStack item = player.getItemStackFromSlot(slot);

        if(ID % 100 == DeepConstants.ITEM_DEEP_LEARNER_GUI_ID && item.getItem() instanceof ItemDeepLearner) {
            return new DeepLearnerGui(player.inventory, world, slot, item);
        } else {
            switch (ID) {
                case DeepConstants.TILE_SIMULATION_CHAMBER_GUI_ID:
                    return new SimulationChamberGui((TileEntitySimulationChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                case DeepConstants.TILE_EXTRACTION_CHAMBER_GUI_ID:
                    return new ExtractionChamberGui((TileEntityExtractionChamber) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                case DeepConstants.TILE_TRIAL_KEYSTONE_GUI_ID:
                    return new TrialKeystoneGui((TileEntityTrialKeystone) world.getTileEntity(new BlockPos(x, y, z)), player.inventory, world);
                default:
                    return null;
            }
        }
    }

    public IPlayerTrial getClientPlayerTrialCapability() {
        return FMLClientHandler.instance().getClientPlayerEntity().getCapability(PlayerTrialProvider.PLAYER_TRIAL_CAP, null);
    }

    public void spawnGlitchParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleGlitch(world, x, y, z, mx, my, mz);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public void spawnSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz, String type) {
        Particle particle;

        switch(type) {
            case "smoke": particle = getSmokeParticle(world, x, y, z, mx, my, mz); break;
            case "mixed": particle = getMixedParticle(world, x, y, z, mx, my, mz); break;
            case "cyan": particle = getCyanParticle(world, x, y, z, mx, my, mz); break;
            default: particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        }

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    private Particle getCyanParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        particle.setRBGColorF(0.0F, 1.0F, 0.75F);
        return particle;
    }

    private Particle getMixedParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        particle.setRBGColorF(0.09F, 0.09F, 0.09F);

        if(ThreadLocalRandom.current().nextInt(0, 3) == 0) {
            particle.setRBGColorF(0.0F, 1.0F, 0.75F);
        }

        return particle;
    }

    private Particle getSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.6F);
        particle.setRBGColorF(0.09F, 0.09F, 0.09F);

        if(ThreadLocalRandom.current().nextInt(0, 3) == 0) {
            particle.setRBGColorF(0.29F, 0.05F, 0.01F);
        }

        if(ThreadLocalRandom.current().nextInt(0, 4) == 0) {
            particle.setRBGColorF(0.02F, 0.02F, 0.02F);
        }

        return particle;
    }

}
