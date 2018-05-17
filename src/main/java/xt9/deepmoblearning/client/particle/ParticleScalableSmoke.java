package xt9.deepmoblearning.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-19.
 */
public class ParticleScalableSmoke extends ParticleSmokeNormal {

    public ParticleScalableSmoke(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, scale);
    }
}
