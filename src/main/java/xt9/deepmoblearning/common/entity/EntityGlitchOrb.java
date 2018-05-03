package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-18.
 */
public class EntityGlitchAttack extends EntityFireball {
    private float damage = 7.0F;

    @SuppressWarnings("unused")
    public EntityGlitchAttack(World worldIn) {
        super(worldIn);
        setSize(0.66F, 0.66F);
    }

    public EntityGlitchAttack(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (ticksExisted > 400)
            setDead();
    }

    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            if (result.entityHit != null) {
                result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shootingEntity), damage);
            }

            setDead();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }
}
