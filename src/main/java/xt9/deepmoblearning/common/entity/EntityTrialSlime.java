package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-02.
 */
public class EntityTrialSlime extends EntitySlime {
    public EntityTrialSlime(World world) {
        super(world);
        setSlimeSize(3, true);
    }

    @Override
    public void setDead() {
        isDead = true;
    }
}
