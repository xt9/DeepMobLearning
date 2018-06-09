package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-05-20.
 */
public class ItemGlitchIngot extends ItemBase {
    public ItemGlitchIngot() {
        super("glitch_infused_ingot", 64);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(Config.isSootedRedstoneCraftingEnabled.getBoolean()) {
            list.add("Made by stabilizing " + new ItemStack(Registry.glitchFragment).getDisplayName());
            list.add("more info found in JEI or the Guidebook");
        }
    }
}
