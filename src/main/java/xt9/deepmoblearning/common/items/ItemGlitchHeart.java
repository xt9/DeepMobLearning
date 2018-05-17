package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-05-14.
 */
public class ItemGlitchHeart extends ItemBase{
    public ItemGlitchHeart() {
        super("glitch_heart", 64);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        list.add("Drops from System Glitches");
    }
}
