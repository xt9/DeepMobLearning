package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-05.
 */
public class EntityTrialEnderman extends EntityEnderman {
    public EntityTrialEnderman(World world) {
        super(world);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
    }

    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1, new AIFindPlayer(this));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));

    }

    private boolean shouldAttackPlayer() {
        return true;
    }

    @Override
    protected boolean teleportRandomly() {
        double x = posX + ThreadLocalRandom.current().nextInt(-5, 5);
        double y = posY;
        double z = posX + ThreadLocalRandom.current().nextInt(-5, 5);
        return teleportTo(x, y, z);
    }

    @Override
    protected boolean teleportToEntity(Entity entity) {
        return teleportRandomly();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        super.attackEntityFrom(source, amount);
        return true;
    }

    private boolean teleportTo(double x, double y, double z) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0.0F);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        } else {
            boolean flag = attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
            if (flag) {
                world.playSound(null, prevPosX, prevPosY, prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
                playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            }

            return flag;
        }
    }

    static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
        private final EntityTrialEnderman enderman;
        private EntityPlayer player;
        private int aggroTime;
        private int teleportTime;

        public AIFindPlayer(EntityTrialEnderman entity) {
            super(entity, EntityPlayer.class, false);
            this.enderman = entity;
        }

        public boolean shouldExecute() {
            double d0 = getTargetDistance();
            player = enderman.world.getNearestAttackablePlayer(enderman.posX, enderman.posY, enderman.posZ, d0, d0, null, player -> player != null && enderman.shouldAttackPlayer());
            return player != null;
        }

        public void startExecuting() {
            aggroTime = 5;
            teleportTime = 0;
        }

        public void resetTask() {
            player = null;
            super.resetTask();
        }

        public boolean shouldContinueExecuting() {
            if (player != null) {
                if (!enderman.shouldAttackPlayer()) {
                    return false;
                } else {
                    enderman.faceEntity(player, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return targetEntity != null && targetEntity.isEntityAlive() || super.shouldContinueExecuting();
            }
        }

        public void updateTask() {
            if (player != null) {
                if (--aggroTime <= 0) {
                    targetEntity = player;
                    player = null;
                    super.startExecuting();
                }
            } else {
                if (targetEntity != null) {
                    if (enderman.shouldAttackPlayer()) {
                        if (targetEntity.getDistanceSq(enderman) < 16.0D) {
                            enderman.teleportRandomly();
                        }

                        teleportTime = 0;
                    } else if (targetEntity.getDistanceSq(enderman) > 256.0D && teleportTime++ >= 30 && enderman.teleportToEntity(targetEntity)) {
                        this.teleportTime = 0;
                    }
                }

                super.updateTask();
            }

        }
    }
}
