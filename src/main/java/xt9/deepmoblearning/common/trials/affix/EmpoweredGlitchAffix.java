package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.entity.EntityGlitch;

/**
 * Created by xt9 on 2018-04-21.
 */
@SuppressWarnings("ConstantConditions")
public class EmpoweredGlitchAffix implements ITrialAffix {
    @Override
    public void apply(EntityLiving entity) {

    }

    @Override
    public void applyToGlitch(EntityGlitch entity) {
        entity.setEmpowered(true);
        IAttributeInstance health = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        AttributeModifier healthMod = new AttributeModifier(DeepConstants.MODID + ".ATTRIBUTE_MAX_HEALTH", 10, 0);
        health.applyModifier(healthMod);
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public void run() {

    }

    @Override
    public String getAffixName() {
        return "Empowered Glitches";
    }

    @Override
    public String getAffixNameWithFormatting() {
        return "§3"+getAffixName()+"§r";
    }
}
