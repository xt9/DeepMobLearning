package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-05-01.
 */
public class ThunderDomeAffix implements ITrialAffix {
    private BlockPos pos;
    private World world;
    private int ticks = 0;

    public ThunderDomeAffix() {
        this.pos = new BlockPos(0, 0, 0);
        this.world = null;
    }

    public ThunderDomeAffix(BlockPos pos, World world) {
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
        // Do once every cycle, enable weather effects
        if(ticks == 0) {
            WorldInfo info = world.getWorldInfo();
            info.setCleanWeatherTime(0);
            info.setRaining(true);
            info.setThundering(true);
            info.setThunderTime(DeepConstants.TICKS_TO_SECOND * 20);
            info.setRainTime(DeepConstants.TICKS_TO_SECOND * 20);
        }

        ticks++;

        // Do once every 2.5 seconds, strike a random location
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 2.5) == 0) {
            int randomX = pos.getX() + ThreadLocalRandom.current().nextInt(-18, 18);
            int randomZ = pos.getZ() + ThreadLocalRandom.current().nextInt(-18, 18);

            world.addWeatherEffect(new EntityLightningBolt(world, (double) randomX, (double) pos.getY(), (double) randomZ, false));
        }

        // Once every 15 seconds, stike a random entity in the trial
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 15) == 0) {
            BlockPos start = new BlockPos(pos.getX() - 7, pos.getY(), pos.getZ() - 7);
            BlockPos end = new BlockPos(pos.getX() + 7, pos.getY() + 10, pos.getZ() + 7);

            List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(start, end));
            Entity unlucky = entities.get(new Random().nextInt(entities.size()));

            world.addWeatherEffect(new EntityLightningBolt(world, unlucky.posX, unlucky.posY - 1.5D, unlucky.posZ, false));
            ticks = 0;
        }
    }

    @Override
    public String getAffixName() {
        return "THUNDERDOME";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§9THUNDERDOME§r";
    }

    @Override
    public String getAffixDescription() {
        return "Makes the trial more electric by causing random thunder strikes. May occasionally directly strike the Player, Opponents or System glitches in the trial";
    }
}
