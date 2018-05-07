package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-02.
 */
public class EntityTrialCaveSpider extends EntityCaveSpider {
    public EntityTrialCaveSpider(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        tasks.addTask(4, new AISpiderAttack(this));
        tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(6, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAIFindEntityNearestPlayer(this));
    }

    static class AISpiderAttack extends EntityAIAttackMelee {
        public AISpiderAttack(EntitySpider spider) {
            super(spider, 1.2D, true);
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return (double)(4.0F + attackTarget.width);
        }
    }
}
