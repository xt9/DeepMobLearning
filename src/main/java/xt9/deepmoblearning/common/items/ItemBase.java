package xt9.deepmoblearning.common.items;


import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ItemBase extends Item {
    public String itemName;

    public ItemBase(String name, int stackSize) {
        super(getDefaultProps(stackSize));
        setRegistryName(new ResourceLocation(DeepConstants.MODID, name));
        this.itemName = name;
    }

    public static Item.Properties getDefaultProps(int stackSize) {
        return new Item.Properties().maxStackSize(stackSize).group(DeepMobLearning.tab);
    }

}
