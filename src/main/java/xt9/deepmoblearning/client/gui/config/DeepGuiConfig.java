package xt9.deepmoblearning.client.gui.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt9 on 2017-12-29.
 */
public class DeepGuiConfig extends GuiConfig {

    public DeepGuiConfig(GuiScreen parent) {
        super(parent, getConfigElements(), DeepConstants.MODID, false, false, "Found at config/deepmoblearning.cfg");
    }

    public static List<IConfigElement> getConfigElements() {
        List <IConfigElement> elements = new ArrayList<>();
        elements.add(new ConfigElement(Config.dataModel));
        elements.add(new ConfigElement(Config.pristineChance));
        elements.add(new ConfigElement(Config.modelExperience));
        elements.add(new ConfigElement(Config.guiOverlaySide));
        return elements;
    }
}
