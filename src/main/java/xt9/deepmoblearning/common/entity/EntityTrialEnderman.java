package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-05.
 */
public class EntityTrialEnderman extends EntityEnderman {
    public EntityTrialEnderman(World world) {
        super(world);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1, new AIFindPlayer(this));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    @Override
    protected void updateAITasks() {
        // Disables enderman taking damage from water.
    }

    private boolean shouldAttackPlayer() {
        return true;
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
