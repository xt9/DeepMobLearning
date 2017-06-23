package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

/**
 * Created by xt9 on 2017-06-12.
 */
public class ItemStackNBTHelper {
    public static boolean hasTag(ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static boolean hasKey(ItemStack stack, String key) {
        return hasTag(stack) && getTag(stack).hasKey(key);
    }

    public static NBTTagCompound getTag(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static void removeTag(ItemStack stack, String key) {
        if(hasKey(stack, key)) {
            getTag(stack).removeTag(key);
            if(getTag(stack).hasNoTags()) {
                stack.setTagCompound(null);
            }
        }
    }

    public static void setString(ItemStack stack, String key, String val) {
        getTag(stack).setString(key, val);
    }

    public static @Nullable String getString(ItemStack stack, String key, String defaultVal) {
        return hasTag(stack) ? getTag(stack).getString(key) : defaultVal;
    }

    public static void setInt(ItemStack stack, String key, int val) {
        getTag(stack).setInteger(key, val);
    }

    public static int getInt(ItemStack stack, String key, int defaultVal) {
        return hasTag(stack) ? getTag(stack).getInteger(key) : defaultVal;
    }
}
