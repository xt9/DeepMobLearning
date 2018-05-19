package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(Config.isSootedRedstoneCraftingEnabled.getBoolean()) {
            list.add("Crafted by crushing §cRedstone§7 against");
            list.add("a §rBlock of Coal§7 (Left click)");
        }
    }
}
