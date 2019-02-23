package xt9.deepmoblearning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderCaveSpider;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xt9.deepmoblearning.client.particle.ParticleGlitch;
import xt9.deepmoblearning.client.particle.ParticleScalableSmoke;
import xt9.deepmoblearning.client.renders.*;
import xt9.deepmoblearning.common.capabilities.PlayerProperties;
import xt9.deepmoblearning.common.capabilities.PlayerTrial;
import xt9.deepmoblearning.common.proxy.IProxy;
import xt9.deepmoblearning.common.entity.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ClientProxy implements IProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlitch.class, RenderEntityGlitch::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGlitchOrb.class, RenderEntityGlitchOrb::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityTrialEnderman.class, RenderEnderman::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialSpider.class, RenderSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialCaveSpider.class, RenderCaveSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrialSlime.class, RenderSlime::new);
    }

    @Override
    public PlayerTrial getTrialCapability(EntityPlayer player) {
        return player.getCapability(PlayerProperties.playerTrialCap).orElseGet(PlayerTrial::new);
    }

    @Override
    public void spawnGlitchParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleGlitch(world, x, y, z, mx, my, mz);
        Minecraft.getInstance().particles.addEffect(particle);
    }

    @Override
    public void spawnSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz, String type) {
        Particle particle;

        switch(type) {
            case "smoke": particle = getSmokeParticle(world, x, y, z, mx, my, mz); break;
            case "mixed": particle = getMixedParticle(world, x, y, z, mx, my, mz); break;
            case "cyan": particle = getCyanParticle(world, x, y, z, mx, my, mz); break;
            default: particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        }

        Minecraft.getInstance().particles.addEffect(particle);
    }

    @Override
    public ItemRenderer getClientItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    private Particle getCyanParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        particle.setColor(0.0F, 1.0F, 0.75F);
        return particle;
    }

    private Particle getMixedParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.4F);
        particle.setColor(0.09F, 0.09F, 0.09F);

        if(ThreadLocalRandom.current().nextInt(0, 3) == 0) {
            particle.setColor(0.0F, 1.0F, 0.75F);
        }

        return particle;
    }

    private Particle getSmokeParticle(World world, double x, double y, double z, double mx, double my, double mz) {
        Particle particle = new ParticleScalableSmoke(world, x, y, z, mx, my, mz, 1.6F);
        particle.setColor(0.09F, 0.09F, 0.09F);

        if(ThreadLocalRandom.current().nextInt(0, 3) == 0) {
            particle.setColor(0.29F, 0.05F, 0.01F);
        }

        if(ThreadLocalRandom.current().nextInt(0, 4) == 0) {
            particle.setColor(0.02F, 0.02F, 0.02F);
        }

        return particle;
    }

}
