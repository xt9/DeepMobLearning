package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        if(Config.isSootedRedstoneCraftingEnabled) {
            list.add(new TextComponentString("Made by stabilizing " + new ItemStack(Registry.glitchFragment).getDisplayName().getFormattedText()));
            list.add(new TextComponentString("more info found in JEI or the Guidebook"));
        }
    }
}
