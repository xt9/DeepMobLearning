package xt9.deepmoblearning.plugins.guideapi.categories;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageText;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import xt9.deepmoblearning.DeepConstants;
import xt9.deepmoblearning.common.Registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xt9 on 2018-06-08.
 */
public class CategoryExtractionChamber {
    public static void init(BookBinder instance) {
        Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<>();

        entries.put(new ResourceLocation(DeepConstants.MODID, "extraction_chamber"), new EntryItemStack(getExtractionChamberPages(), "Loot Fabricator", new ItemStack(Registry.extractionChamberItem)));

        instance.addCategory(new CategoryItemStack(entries, "Loot Fabricator", new ItemStack(Registry.extractionChamberItem)));
    }

    private static List<IPage> getExtractionChamberPages() {
        NonNullList<IPage> pages = NonNullList.create();
        pages.add(new PageText("§3Loot Fabricator§r\nThe loot fabricator can well... Fabricate loot from the various accessible Pristine matter and some power.\n\nIt will remember the selected item for automation purposes."));
        pages.add(new PageText("§3Machine Sides§r\nInput: Top\nOutput: All other sides\n\nThe machine cannot autu-output or auto-input."));
        return pages;
    }
}
