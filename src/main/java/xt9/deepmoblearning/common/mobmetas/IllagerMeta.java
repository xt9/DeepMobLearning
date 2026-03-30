package xt9.deepmoblearning.common.mobmetas;

import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class IllagerMeta extends MobMetaData {
    static String[] mobTrivia = {"Dangerous outcasts", "Villagers don't trust them", "And neither should you"};

    IllagerMeta(String key, String name, String pluralName, int numberOfHearts, int interfaceScale, int interfaceOffsetX, int interfaceOffsetY, Item livingMatter, Item pristineMatter) {
        super(key, name, pluralName, numberOfHearts, interfaceScale, interfaceOffsetX, interfaceOffsetY, livingMatter, pristineMatter, mobTrivia);
    }

    @Override
    public EntityEvoker getEntity(World world) {
        return new EntityEvoker(world);
    }
}