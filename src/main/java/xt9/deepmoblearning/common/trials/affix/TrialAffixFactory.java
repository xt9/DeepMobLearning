package xt9.deepmoblearning.common.trials.affix;

import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-04-21.
 */
public class TrialAffixFactory {
    public static ITrialAffix createAffix(String key, BlockPos pos, World world) {
        ITrialAffix trial = new SpeedAffix();

        if(key.equals(TrialAffixKey.SPEED)) {
            trial = new SpeedAffix();
        }else if(key.equals(TrialAffixKey.REGEN_PARTY)) {
            trial = new RegenPartyAffix(pos, world);
        } else if(key.equals(TrialAffixKey.EMPOWERED_GLITCHES)) {
            trial = new EmpoweredGlitchAffix();
        } else if(key.equals(TrialAffixKey.KNOCKBACK_IMMUNITY)) {
            trial = new KnockbackImmuneAffix();
        } else if(key.equals(TrialAffixKey.BLAZE_INVADERS)) {
            trial = new BlazeInvadersAffix(pos, world);
        } else if(key.equals(TrialAffixKey.LOOT_HOARDERS)) {
            trial = new LootHoarderAffix(pos, world);
        } else if(key.equals(TrialAffixKey.THUNDERDOME)) {
            trial = new ThunderDomeAffix(pos, world);
        }

        return trial;
    }

    public static NonNullList<ITrialAffix> getAllAffixes() {
        NonNullList<ITrialAffix> affixes = NonNullList.create();
        affixes.add(new SpeedAffix());
        affixes.add(new RegenPartyAffix());
        affixes.add(new EmpoweredGlitchAffix());
        affixes.add(new KnockbackImmuneAffix());
        affixes.add(new BlazeInvadersAffix());
        affixes.add(new LootHoarderAffix());
        affixes.add(new ThunderDomeAffix());
        return affixes;
    }
}
