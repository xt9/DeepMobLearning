package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xt9.deepmoblearning.common.entity.EntityGlitch;

/**
 * Created by xt9 on 2018-04-21.
 */
@SuppressWarnings("ConstantConditions")
public class SpeedAffix implements ITrialAffix {
    private PotionEffect effect = new PotionEffect(Potion.REGISTRY.getObjectById(1), Integer.MAX_VALUE, 0);

    @Override
    public void apply(EntityLiving entity) {
        entity.addPotionEffect(effect);
    }

    @Override
    public void applyToGlitch(EntityGlitch entity) {}

    @Override
    public void run() {}

    @Override
    public String getAffixName() {
        return "Speed";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§b"+getAffixName()+"§r";
    }

}
