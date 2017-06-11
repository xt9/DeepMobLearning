package xt9.deepmoblearning.common;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xt9.deepmoblearning.common.items.ItemBase;
import xt9.deepmoblearning.common.items.ItemDeepLearner;
import xt9.deepmoblearning.common.items.ItemMobChip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    protected static List<ItemBase> items = new ArrayList<>();

    public static void preInit() {
        // Create our Item instances
        items.add(new ItemDeepLearner());
        items.add(new ItemMobChip());
        registerItems();
    }

    public static void registerItems() {
        for (ItemBase item: items) {
            GameRegistry.register(item);
            item.registerItemModel();
        }
    }
}
