package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-04.Nre
 */
public class EntityGlitch extends EntityMob {
    private boolean empowered = false;

    public EntityGlitch(World world) {
        super(world);
        setSize(0.6F, 1.95F);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(DeepConstants.MODID, "glitch");
    }

    public void setEmpowered(boolean empowered) {
        this.empowered = empowered;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(26.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.29D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(4, new EntityGlitch.AIRangedAttack(this));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(7, new EntityAIWander(this, 1.0D));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }


    public void onLivingUpdate() {
        if (world.isRemote) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            for(int i = 0; i < 16; ++i) {
                DeepMobLearning.proxy.spawnGlitchParticle(
                    world,
                    posX + (rand.nextDouble(-2.0D, 2.0D)) * (double) width,
                    posY + (rand.current().nextDouble(-0.5D, 0.5D)) * (double) height,
                    posZ + (rand.current().nextDouble(-2.0D, 2.0D)) * (double) width,
                    rand.nextDouble(-0.08D, 0.08D) - 0.01D,
                    rand.nextDouble(-0.08D, 0.08D) - rand.nextDouble(),
                    rand.nextDouble(-0.08D, 0.08D) - 0.01D
                );
            }
        }


        super.onLivingUpdate();
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 30;
    }

    static class AIRangedAttack extends EntityAIBase {
        private final EntityGlitch glitch;
        public int attackTimer;

        public AIRangedAttack(EntityGlitch glitch) {
            this.glitch = glitch;
            setMutexBits(3);
        }

        public boolean shouldExecute() {
            if(glitch.getAttackTarget() instanceof EntityPlayer &&
                (((EntityPlayer) glitch.getAttackTarget()).isCreative() || ((EntityPlayer) glitch.getAttackTarget()).isSpectator())) {
                return false;
            }
            return glitch.getAttackTarget() != null;
        }

        public void startExecuting() {
            resetTask();
        }

        public void resetTask() {
            if(glitch.empowered) {
                attackTimer = 70;
            } else {
                attackTimer = 60;
            }
        }

        public void updateTask() {
            EntityLivingBase target = glitch.getAttackTarget();
            if(target == null) {
                resetTask();
                return;
            }

            glitch.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
            double distanceToTarget = target.getDistanceSq(glitch);

            // Can shoot 19 blocks
            if (distanceToTarget < 304 && glitch.canEntityBeSeen(target)) {
                // Clear previous pathing when in range of target
                glitch.getNavigator().clearPath();
                World world = glitch.world;
                --this.attackTimer;
                ThreadLocalRandom rand = ThreadLocalRandom.current();

                // Barrage target, reset task when timer hit's 0
                if(attackTimer == 48) {
                    spawnGlitchOrb(world, target, rand.nextDouble(-1.2, 1.2), 0.1,rand.nextDouble(-1.2, 1.2));
                } else if (attackTimer == 36) {
                    spawnGlitchOrb(world, target, 0, 0.8D,0);
                } else if(attackTimer == 24 && glitch.empowered) {
                    spawnGlitchOrb(world, target, rand.nextDouble(-1.2, 1.2), 0.3D,rand.nextDouble(-1.2, 1.2));
                } else if(attackTimer == 0) {
                    resetTask();
                }
            } else {
                // Path towards target if not in range for an attack
                glitch.getNavigator().tryMoveToEntityLiving(target, 1.0F);
            }

            super.updateTask();
        }

        private void spawnGlitchOrb(World world, Entity target, double xPad, double yPad, double zPad) {
            Vec3d vec3d = glitch.getLook(1.0F);
            double d2 = target.posX - (glitch.posX + vec3d.x * 0.5D);
            double d3 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (0.5D + glitch.posY + (double) (glitch.height / 2.0F));
            double d4 = target.posZ - (glitch.posZ + vec3d.z * 0.5D);


            EntityGlitchOrb rangedObject = new EntityGlitchOrb(world, glitch, d2, d3, d4, glitch.empowered);
            rangedObject.posX = glitch.posX + vec3d.x * 0.5D + xPad;
            rangedObject.posY = glitch.posY + (glitch.height / 2.0F) + yPad + 0.5D;
            rangedObject.posZ = glitch.posZ + vec3d.z * 0.5D + zPad;
            world.spawnEntity(rangedObject);

            world.playSound(null, target.posX, target.posY, target.posZ, SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.HOSTILE, 1.0F, 1.0F);
            glitch.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 1.0F, 1.0F);
        }

    }
}
