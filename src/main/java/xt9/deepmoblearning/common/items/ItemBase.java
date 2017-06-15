package xt9.deepmoblearning.common.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.DeepMobLearning;

import java.util.List;

/**
 * Created by xt9 on 2017-06-08.
 */
public class ItemBase extends Item {
    public String itemName;
    private String[] subNames;

    public ItemBase(String name, int stackSize, String... subNames) {
        boolean hasSubItems = subNames != null && subNames.length > 0;

        this.setUnlocalizedName(DeepMobLearning.MODID + "." + name);
        this.setRegistryName(name);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
        this.subNames = hasSubItems ? subNames : null;
        this.setHasSubtypes(hasSubItems);
    }

    public void registerItemModel() {
        if(this.hasSubtypes) {
            for (int i = 0; i < this.subNames.length; i++) {
                // Register a item renderer for each subtype
                DeepMobLearning.proxy.registerItemRenderer(this, i, this.itemName);
            }
        } else {
            DeepMobLearning.proxy.registerItemRenderer(this, 0, this.itemName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
        if(this.hasSubtypes) {
            for(int i = 0; i < this.subNames.length; i++) {
                list.add(new ItemStack(this,1, i));
            }
        }
        else {
            list.add(new ItemStack(this));
        }

    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = this.getUnlocalizedName();
        if(this.hasSubtypes) {
            name = this.getUnlocalizedName() + "." + this.subNames[stack.getItemDamage()];
        }
        return name;
    }

    public boolean hasSubTypes() {
        return this.hasSubtypes;
    }

    public String[] getSubNames() {
        return this.subNames;
    }

}
