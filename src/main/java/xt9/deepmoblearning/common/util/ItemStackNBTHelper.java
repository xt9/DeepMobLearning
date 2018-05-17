package xt9.deepmoblearning.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
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

    public static void setLong(ItemStack stack, String key, long val) {
        getTag(stack).setLong(key, val);
    }

    public static long getLong(ItemStack stack, String key, long defaultVal) {
        return hasTag(stack) ? getTag(stack).getLong(key) : defaultVal;
    }

    public static void setInt(ItemStack stack, String key, int val) {
        getTag(stack).setInteger(key, val);
    }

    public static int getInt(ItemStack stack, String key, int defaultVal) {
        return hasTag(stack) ? getTag(stack).getInteger(key) : defaultVal;
    }

    public static boolean getBoolean(ItemStack stack, String key, boolean defaultVal) {
        return hasTag(stack) ? getTag(stack).getBoolean(key) : defaultVal;
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        getTag(stack).setBoolean(key, value);
    }

    public static void setStringList(ItemStack stack, NonNullList<String> list, String key) {
        NBTTagList stringList = new NBTTagList();

        int index = 0;
        for (String s : list) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(index+"", s);
            stringList.appendTag(tag);
            index++;
        }

        getTag(stack).setTag(key, stringList);
    }

    public static NonNullList<String> getStringList(ItemStack stack, String key) {
        NonNullList<String> list = NonNullList.create();

        if(stack.hasTagCompound()) {
            NBTTagList tagList = stack.getTagCompound().getTagList(key, Constants.NBT.TAG_COMPOUND);

            for(int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound tag = tagList.getCompoundTagAt(i);
                list.add(i, tag.getString(i+""));
            }
        }

        return list;
    }
}
