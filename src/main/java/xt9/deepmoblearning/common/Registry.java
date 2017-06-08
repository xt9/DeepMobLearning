package xt9.deepmoblearning.common;

import xt9.deepmoblearning.common.items.ItemBase;
import xt9.deepmoblearning.common.items.ItemDeepLearner;

/**
 * Created by xt9 on 2017-06-08.
 */
public class Registry {
    public static ItemBase itemDeepLearner;

    public static void preInit() {
        itemDeepLearner = new ItemDeepLearner();
    }
}
