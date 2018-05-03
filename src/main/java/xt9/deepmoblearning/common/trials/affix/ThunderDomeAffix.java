package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import xt9.deepmoblearning.common.entity.EntityGlitch;

import java.util.List;

/**
 * Created by xt9 on 2018-05-01.
 */
public class ThunderDomeAffix implements ITrialAffix {
    private String[] descriptionLines = {"Makes the trial more epic(by enabling thunder)\nMay occasionally strike the player or entities in the trial"};
    private BlockPos pos;
    private World world;
    private int ticks = 0;

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
        ticks++;
        // Do once every cycle
        if(ticks == 0) {
            WorldInfo info = world.getWorldInfo();
            info.setThunderTime(100);
        }
        // Once every 5 seconds
        if(ticks % 100 == 0) {
            BlockPos start = new BlockPos(pos.getX() - 7, pos.getY(), pos.getZ() - 7);
            BlockPos end = new BlockPos(pos.getX() + 7, pos.getY() + 10, pos.getZ() + 7);

            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(start, end));

            System.out.println("ay");
            ticks = 0;
        }
    }

    @Override
    public String getAffixName() {
        return null;
    }

    @Override
    public String getAffixNameWithFormatting() {
        return null;
    }

    @Override
    public String[] getDescriptionLines() {
        return descriptionLines;
    }
}
