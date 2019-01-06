package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2018-04-24.
 */
public class RegenPartyAffix implements ITrialAffix {
    private BlockPos pos;
    private World world;
    private int ticks = 0;

    public RegenPartyAffix() {
        this.pos = new BlockPos(0,0,0);
        this.world = null;
    }

    public RegenPartyAffix(BlockPos pos, World world) {
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
    @SuppressWarnings("ConstantConditions")
    public void run() {
        ticks++;
        // Run once every 16 seconds
        if(ticks % (DeepConstants.TICKS_TO_SECOND * 16) == 0) {
            EntityPotion regenPot = new EntityPotion(world);
            ItemStack lingeringPotion = new ItemStack(Items.LINGERING_POTION);
            PotionUtils.addPotionToItemStack(lingeringPotion, PotionType.getPotionTypeForName("strong_regeneration"));
            regenPot.setItem(lingeringPotion);

            int randomX = pos.getX() + ThreadLocalRandom.current().nextInt(-5, 5);
            int randomY = pos.getY() + ThreadLocalRandom.current().nextInt(2, 9);
            int randomZ = pos.getZ() + ThreadLocalRandom.current().nextInt(-5, 5);
            regenPot.setLocationAndAngles(randomX, randomY, randomZ, 0 ,0);

            world.spawnEntity(regenPot);
            ticks = 0;
        }
    }

    @Override
    public String getAffixName() {
        return "Regen Party";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§d"+getAffixName()+"§r";
    }
}
