package xt9.deepmoblearning.plugins.guideapi;

import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.DeepMobLearning;
import xt9.deepmoblearning.common.Registry;
import xt9.deepmoblearning.plugins.guideapi.categories.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * Created by xt9 on 2018-06-08.
 */
@GuideBook
@SuppressWarnings("unused")
public class DeepGuide implements IGuideBook {
    private static BookBinder instance = new BookBinder(new ResourceLocation(DeepConstants.MODID, "deepguide"));

    @Nullable
    @Override
    public Book buildBook() {
        instance.setGuideTitle("Deep Mob Learning");
        instance.setItemName("Deep Mob Learning");
        instance.setAuthor("A book written by IterationFunk with GuideAPI (by TehNut/Tombenpotter)");
        instance.setColor(0x00FFFF);
        return instance.build();
    }

    @Override
    public void handlePost(@Nonnull ItemStack bookStack) {
        CategoryIntroduction.init(instance, bookStack);
        CategorySimulationChamber.init(instance);
        CategoryExtractionChamber.init(instance);
        CategoryTrial.init(instance);
        CategoryGlitches.init(instance);

        if(DeepConstants.MOD_BM_ADDON_LOADED) {
            CategoryBloodMagic.init(instance);
        }
    }

    @Nullable
    @Override
    public IRecipe getRecipe(@Nonnull ItemStack bookStack) {
        return new ShapelessOreRecipe(new ResourceLocation(DeepConstants.MODID, "deepguide"), bookStack, new ItemStack(Items.BOOK), new ItemStack(Registry.sootedRedstone)).setRegistryName("deepguide");
    }
}
