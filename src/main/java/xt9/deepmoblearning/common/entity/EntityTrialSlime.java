package xt9.deepmoblearning.common.entity;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-02.
 */
public class EntityTrialSlime extends EntitySlime {
    public EntityTrialSlime(World world) {
        // @todo 1.13 see if this needs it's own type
        super(world);
        setSlimeSize(3, true);
    }


    @Override
    public void remove() {
        this.removed = true;
        this.invalidateCaps();
    }
}
