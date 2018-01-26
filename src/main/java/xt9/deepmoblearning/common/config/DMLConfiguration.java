package xt9.deepmoblearning.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

/**
 * Created by xt9 on 2018-01-21.
 */
public class DMLConfiguration extends Configuration {
    public DMLConfiguration(File file) {
        super(file);
    }

    /* Added to get rid of long annoying list of default values */
    @Override
    public String[] getStringList(String name, String category, String[] defaultValue, String comment) {
        comment = comment == null ? "" : comment;
        Property prop = get(category, name, defaultValue);
        prop.setLanguageKey(name);
        prop.setComment(comment);
        return prop.getStringList();
    }
}
