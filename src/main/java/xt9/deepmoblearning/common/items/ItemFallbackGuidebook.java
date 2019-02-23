package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2019-01-06.
 */
public class ItemFallbackGuidebook extends ItemBase {
    public ItemFallbackGuidebook() {
        super("book", 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
        list.add(new TextComponentString("This item does nothing"));
        list.add(new TextComponentString("Install Patchouli for an indepth mod Guide."));
    }
}
