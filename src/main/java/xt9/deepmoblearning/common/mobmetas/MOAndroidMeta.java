package xt9.deepmoblearning.common.mobmetas;

import matteroverdrive.entity.monster.EntityMeleeRougeAndroidMob;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2019-03-01.
 */
public class MOAndroidMeta extends MobMetaData {
    static String[] mobTrivia = {"It's not simply an android.", "It's a life form, entirely unique.", "Meep morp."};

    public MOAndroidMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public EntityMeleeRougeAndroidMob getEntity(World world) {
        return new EntityMeleeRougeAndroidMob(world);
    }
}
