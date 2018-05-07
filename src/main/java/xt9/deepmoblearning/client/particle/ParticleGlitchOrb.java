package xt9.deepmoblearning.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-19.
 */
public class ParticleGlitchOrb extends ParticleSmokeNormal {

    public ParticleGlitchOrb(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.4F);
        particleRed = 0.09F;
        particleGreen = 0.09F;
        particleBlue = 0.09F;
    }
}
