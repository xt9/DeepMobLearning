package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;

/**
 * Created by xt9 on 2018-04-25.
 */
public class KnockbackImmuneAffix implements ITrialAffix {
    @Override
    public void apply(EntityLiving entity) {
        IAttributeInstance knockbackResist = entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        AttributeModifier knockbackMod = new AttributeModifier(DeepConstants.MODID + ".ATTRIBUTE_KNOCKBACKRESIST", 1, 0);
        knockbackResist.applyModifier(knockbackMod);
    }

    @Override
    public void applyToGlitch(EntityGlitch entity) {

    }

    @Override
    public void run() {

    }

    @Override
    public String getAffixName() {
        return "Knockback Immunity";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§8"+getAffixName()+"§r";
    }
}
