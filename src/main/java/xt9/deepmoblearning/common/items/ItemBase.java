package xt9.deepmoblearning.common.items;


import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xt9.deepmoblearning.DeepMobLearning;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ItemBase extends Item {
    public String itemName;

    public ItemBase(String name, int stackSize) {
        this.setUnlocalizedName(DeepMobLearning.MODID + "." + name);
        this.setRegistryName(name);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
        GameRegistry.register(this);
    }
}
