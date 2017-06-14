package xt9.deepmoblearning.common.util;

/**
 * Created by xt9 on 2017-06-13.
 */
public class StringHelper {
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
