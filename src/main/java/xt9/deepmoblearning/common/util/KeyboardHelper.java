package xt9.deepmoblearning.common.util;

import org.lwjgl.input.Keyboard;

/**
 * Created by xt9 on 2017-06-13.
 */
public class KeyboardHelper {
    public static boolean isHoldingShift() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
