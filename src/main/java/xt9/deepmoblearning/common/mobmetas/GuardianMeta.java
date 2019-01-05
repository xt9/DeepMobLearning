package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2018-05-05.
 */
public class GuardianMeta extends MobMetaData {
    static String[] mobTrivia = {"Lurking in the oceans.", "Uses some sort of sonar beam as", "a means of attack"};

    GuardianMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public EntityGuardian getEntity(World world) {
        return new EntityGuardian(world);
    }
}

