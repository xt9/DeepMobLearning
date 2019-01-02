package xt9.deepmoblearning.common.util;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xt9 on 2019-01-02.
 */
public class SoundHelper {
    public static void playSound(World world, BlockPos pos, String key) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

        switch (key) {
            case "waveStart":
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.MASTER, 1.6F, 0.8F);
                break;
            case "glitchAlert":
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_NOTE_BELL, SoundCategory.MASTER, 1.6F, 0.4F);
                break;
            case "waveCountdown":
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_NOTE_PLING, SoundCategory.MASTER, 1.5F, 1.1F);
                for (int seconds = 1; seconds < 5; seconds++) {
                    float pitch = (seconds * 0.1F) + 1.1F;
                    exec.schedule(() -> world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_NOTE_PLING, SoundCategory.MASTER, 1.5F, pitch), seconds, TimeUnit.SECONDS);
                }
                break;
            case "trialWon":
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1.5F, 1.0F);
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1.3F, 1.0F);

                for (int fireworks = 1; fireworks < 5; fireworks++) {
                    float pitch = (fireworks * 0.05F) + 1F;
                    exec.schedule(() -> world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.MASTER, 1.3F, pitch), fireworks * 450, TimeUnit.MILLISECONDS);
                }
                break;
            default:
                break;
        }
    }
}
