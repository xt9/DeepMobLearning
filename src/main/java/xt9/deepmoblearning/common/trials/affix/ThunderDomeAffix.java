package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;
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

        // Once every 15 seconds
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 15) == 0) {
            // 22% chance
            if(ThreadLocalRandom.current().nextInt(1, 100) < 22) {
                int randomX = pos.getX() + ThreadLocalRandom.current().nextInt(-5, 5);
                int randomY = pos.getY() + ThreadLocalRandom.current().nextInt(0, 1);
                int randomZ = pos.getZ() + ThreadLocalRandom.current().nextInt(-5, 5);

                if(ThreadLocalRandom.current().nextInt(1, 100) < 33) {
                    EntityCreeper creeper = new EntityCreeper(world);
                    creeper.setLocationAndAngles(randomX, randomY, randomZ, 0, 0);

                    NBTTagCompound tag = new NBTTagCompound();
                    tag = creeper.writeToNBT(tag);
                    tag.setBoolean("powered", true);
                    creeper.readEntityFromNBT(tag);

                    world.spawnEntity(creeper);
                } else {
                    EntityWitch witch = new EntityWitch(world);
                    witch.setLocationAndAngles(randomX, randomY, randomZ, 0, 0);
                    world.spawnEntity(witch);
                }
            }

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
}
