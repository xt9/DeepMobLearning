package xt9.deepmoblearning.common.util;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;


/**
 * Created by xt9 on 2017-06-13.
 */
public class KeyboardHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputMappings.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCTRL() {
        return InputMappings.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL) || InputMappings.isKeyDown(GLFW.GLFW_KEY_RIGHT_CONTROL);
    }
}
