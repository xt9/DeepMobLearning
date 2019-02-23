package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xt9.deepmoblearning.common.config.Config;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2017-06-19.
 */
public class ItemSootedRedstone extends ItemBase {
    public ItemSootedRedstone() {
        super("soot_covered_redstone", 64);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        if(Config.isSootedRedstoneCraftingEnabled) {
            list.add(new TextComponentString("Crafted by crushing §cRedstone§7 against"));
            list.add(new TextComponentString("a §rBlock of Coal§7 (Left click)"));
        }
    }
}
