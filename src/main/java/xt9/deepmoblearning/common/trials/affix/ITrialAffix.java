package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import xt9.deepmoblearning.common.entity.EntityGlitch;

/**
 * Created by xt9 on 2018-04-21.
 */
public interface ITrialAffix {
    void apply(EntityLiving entity);
    void applyToGlitch(EntityGlitch entity);
    // Run will run every update tick from the Trial Keystone, it's up to the implementing class to stagger this to avoid performance issues.
    void run();
    String getAffixName();
    String getAffixNameWithFormatting();
}
