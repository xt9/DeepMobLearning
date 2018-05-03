package xt9.deepmoblearning.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-19.
 */
public class ParticleGlitchSmoke extends ParticleSmokeNormal {

    public ParticleGlitchSmoke(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0F);
        particleRed = 0.0F;
        particleGreen = 0.81F;
        particleBlue = 0.8F;
    }
}
