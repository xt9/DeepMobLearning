package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-25.
 */
public class BlazeInvadersAffix implements ITrialAffix {
    private BlockPos pos;
    private World world;
    private int ticks = 0;

    public BlazeInvadersAffix() {
        this.pos = new BlockPos(0, 0, 0);
        this.world = null;
    }

    public BlazeInvadersAffix(BlockPos pos, World world) {
        this.pos = pos;
        this.world = world;
    }


    @Override
    public void apply(EntityLiving entity) {

    }

    @Override
    public void applyToGlitch(EntityGlitch entity) {

    }

    @Override
    public void run() {
        ticks++;
        // Once every 15 seconds 34% chance
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 15) == 0) {
            if(ThreadLocalRandom.current().nextInt(1, 100) > 66) {
                EntityBlaze blaze = new EntityBlaze(world);

                int randomX = pos.getX() + ThreadLocalRandom.current().nextInt(-5, 5);
                int randomY = pos.getY() + ThreadLocalRandom.current().nextInt(0, 1);
                int randomZ = pos.getZ() + ThreadLocalRandom.current().nextInt(-5, 5);
                blaze.setLocationAndAngles(randomX, randomY, randomZ, 0, 0);

                world.spawnEntity(blaze);
            }
            ticks = 0;
        }
    }

    @Override
    public String getAffixName() {
        return "Blaze Invaders";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§c"+getAffixName()+"§r";
    }
}
