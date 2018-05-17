package xt9.deepmoblearning.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.common.config.Config;
import xt9.deepmoblearning.common.entity.EntityItemGlitchFragment;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by xt9 on 2018-05-16.
 */
public class ItemGlitchFragment extends ItemBase {
    public ItemGlitchFragment() {
        super("glitch_fragment", 64);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
        if(Config.isSootedRedstoneCraftingEnabled.getBoolean()) {
            list.add("Crafted by crushing a " + new ItemStack(Registry.glitchHeart, 1).getDisplayName());
            list.add("against some §5Obsidian§7 (Left click)");
            list.add("§rYields 3 fragments per crushed heart");
        }
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack stack) {
        Entity result = new EntityItemGlitchFragment(world, location.posX, location.posY, location.posZ, new ItemStack(this, stack.getCount()));
        result.motionX = location.motionX;
        result.motionY = location.motionY;
        result.motionZ = location.motionZ;
        return result;
    }
}
