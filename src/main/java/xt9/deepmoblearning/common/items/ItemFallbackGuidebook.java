package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2019-01-06.
 */
public class ItemFallbackGuidebook extends ItemBase {
    public ItemFallbackGuidebook() {
        super("book", 1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        list.add("This item does nothing");
        list.add("Install Patchouli for an indepth mod Guide.");
    }
}
