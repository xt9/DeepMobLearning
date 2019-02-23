package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.Particles;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-18.
 */
@SuppressWarnings("FieldCanBeLocal")
public class EntityGlitchOrb extends EntityFireball {
    private float damage = 1.6F;

    @SuppressWarnings("unused")
    public EntityGlitchOrb(World worldIn) {
        super(Registry.entityGlitchOrb, worldIn, 1.0f, 1.0f);
    }



    public EntityGlitchOrb(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ, boolean empowered) {
        // @todo 1.13 check last 2  (width and height)
        super(Registry.entityGlitchOrb, shooter, accelX, accelY, accelZ, worldIn, 1.0f, 1.0f);
        if(empowered) {
            damage = 2.8F;
        }
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            setFlag(6, isGlowing());

            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(1.0D));

            entities.forEach(entity -> {
                if(entity instanceof EntityPlayer) {
                    onImpact((EntityPlayer) entity);
                }
            });
        }

        if(world.isRemote || (shootingEntity == null || shootingEntity.isAlive()) && world.isBlockLoaded(new BlockPos(this))) {
            this.posX += motionX;
            this.posY += motionY;
            this.posZ += motionZ;
            ProjectileHelper.rotateTowardsMovement(this, 0.055F);

            // Motion multiplier
            float f = 1.11F;
            if(isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    //noinspection RedundantArrayCreation
                    world.spawnParticle(Particles.BUBBLE, posX - motionX * 0.25D, posY - motionY * 0.25D, posZ - motionZ * 0.25D, motionX, motionY, motionZ);
                }
                f = 0.8F;
            }

            this.motionX += accelerationX;
            this.motionY += accelerationY;
            this.motionZ += accelerationZ;
            this.motionX *= (double)f;
            this.motionY *= (double)f;
            this.motionZ *= (double)f;

            ThreadLocalRandom rand = ThreadLocalRandom.current();

            DeepMobLearning.proxy.spawnSmokeParticle(world,
                posX + rand.nextDouble(-0.5D, 0.5D),
                posY  + rand.nextDouble(-0.1D, 0.1D),
                posZ + rand.nextDouble(-0.5D, 0.5D),
                rand.nextDouble(-0.08D, 0.08D),
                rand.nextDouble(-0.08D, 0),
                rand.nextDouble(-0.08D, 0.08D),
                "mixed"
            );

            setPosition(posX, posY, posZ);
        }

        if(ticksExisted > 400){
            remove();
        }
        super.tick();
    }

    private void onImpact(EntityPlayer entity) {
        if (!world.isRemote) {
            entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shootingEntity), damage);
            //noinspection ConstantConditions
            entity.addPotionEffect(new PotionEffect(Potion.getPotionById(20), 40, 1));
            remove();
        }
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        //
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }
}
