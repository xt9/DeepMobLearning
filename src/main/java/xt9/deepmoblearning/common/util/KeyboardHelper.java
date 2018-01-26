package xt9.deepmoblearning.common.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;


/**
 * Created by xt9 on 2017-06-13.
 */
public class KeyboardHelper {

    @SideOnly(Side.CLIENT)
    public static boolean isHoldingShift() {
        return Keyboard.isCreated() && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
    }

    @SideOnly(Side.CLIENT)
    public static boolean isHoldingCTRL() {
        return Keyboard.isCreated() && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
    }
}
