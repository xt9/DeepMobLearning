package xt9.deepmoblearning.common.util;

/**
 * Created by xt9 on 2017-06-13.
 */
public class MathHelper {
    public static int ensureRange(int value, int min, int max) {
        return java.lang.Math.min(java.lang.Math.max(value, min), max);
    }

    public static boolean inRange(int value, int min, int max) {
        return (value>= min) && (value<= max);
    }
}
