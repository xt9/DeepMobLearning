package xt9.deepmoblearning.client.particle;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-05.
 */
public class ParticleGlitch extends ParticlePortal {

    public ParticleGlitch(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        particleRed = 0.0F;
        particleGreen = 0.81F;
        particleBlue = 0.8F;
    }
}
